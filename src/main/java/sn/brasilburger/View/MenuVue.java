    package sn.brasilburger.View;

    import sn.brasilburger.Entity.Enum.TypeComplement;
    import sn.brasilburger.Entity.Menu;
    import sn.brasilburger.Entity.MenuBurger;
    import sn.brasilburger.Entity.MenuComplement;
    import sn.brasilburger.Service.*;

    import java.util.List;
    import java.util.Scanner;

    public class MenuVue extends Vue {
        private final MenuService menuService;
        private final BurgerService burgerService;
        private final ComplementService complementService;
        private final MenuBurgerService menuBurgerService;
        private final MenuComplementService menuComplementService;
        private final CloudinaryService cloudinaryService;

        private final BurgerVue burgerVue;
        private final ComplementVue complementVue;

        public MenuVue(MenuService menuService, BurgerService burgerService, ComplementService complementService, MenuBurgerService menuBurgerService, MenuComplementService menuComplementService, BurgerVue burgerVue, ComplementVue complementVue, CloudinaryService cloudinaryService) {
            this.menuService = menuService;
            this.burgerService = burgerService;
            this.complementService = complementService;
            this.menuBurgerService = menuBurgerService;
            this.menuComplementService = menuComplementService;
            this.burgerVue = burgerVue;
            this.complementVue = complementVue;
            this.cloudinaryService = cloudinaryService;
        }

        public Menu saisieMenu(Scanner scanner) {
            Menu menu = new Menu();
            menu.setId(menuService.numberOfRows() + 1);

            menu.setLibelle(saisieChaine(scanner, "Saisir le libellé : "));

            String imageUrl = cloudinaryService.uploadImage();

            if (imageUrl != null) {
                System.out.println("Image disponible à : " + imageUrl);
            }
            menu.setImageUrl(imageUrl);

            menu.setArchived(false);
            menu.setPrix(0.0);

            return menu;
        }

        public Menu saisieMenuComplet(Scanner scanner,
                                      BurgerService burgerService,
                                      ComplementService complementService,
                                      MenuBurgerService menuBurgerService,
                                      MenuComplementService menuComplementService) {

            Menu menu = new Menu();
            menu = saisieMenu(scanner);
            menuService.createMenu(menu);

            double total = 0;

            total += ajoutBurger(total, menu);
            total += ajoutComplement(total, menu, TypeComplement.BOISSON);
            total += ajoutComplement(total, menu, TypeComplement.FRITE);


            menu.setPrix(total);
            menuService.update(menu);

            System.out.println("\n=== MENU CRÉÉ ===");
            System.out.println("Libellé : " + menu.getLibelle());
            System.out.println("Prix final : " + total + " FCFA");

            return menu;
        }

        public double ajoutBurger(double total, Menu menu) {

            System.out.println("\n=== AJOUT DES BURGERS AU MENU ===");

            boolean auMoinsUn = false;

            while (true) {
                if (auMoinsUn)
                {
                    String rep = saisieChaine(scanner, "Ajouter un autre burger ? (o/n) : ");
                    if (rep.equalsIgnoreCase("n")) break;
                }

                burgerVue.afficheBurgers();

                int burgerId;
                do {
                    burgerId = Integer.parseInt(saisieChaine(scanner, "ID du burger : "));
                } while (burgerService.selectById(burgerId).isEmpty());

                int quantite = Vue.saisieIntPositive(scanner, "Quantité: ");

                MenuBurger mb = new MenuBurger(
                        menuBurgerService.numberOfRows() + 1,
                        menu.getId(),
                        burgerId,
                        quantite
                );
                menuBurgerService.createMenuBurger(mb);

                double prixBurger = burgerService.selectById(burgerId).get().getPrix();
                total += prixBurger * quantite;

                auMoinsUn = true;
            }

            return total;
        }

        public double ajoutComplement(double total, Menu menu, TypeComplement typeComplement) {

            System.out.println("\n=== AJOUT DES " + typeComplement.name() + " AU MENU ===");

            boolean auMoinsUn = false;

            while (true) {

                if (auMoinsUn)
                {
                    String rep = saisieChaine(scanner, "Ajouter un(e) " + typeComplement.name().toLowerCase() + " ? (o/n) : ");
                    if (rep.equalsIgnoreCase("n")) break;
                }

                complementVue.afficheComplementsParType(typeComplement);

                int complementId;
                do {
                    complementId = Integer.parseInt(saisieChaine(scanner, "ID : "));
                } while (complementService.selectById(complementId).isEmpty() || complementService.selectById(complementId).get().getTypeComplement() != typeComplement
                );

                int quantite = Vue.saisieIntPositive(scanner, "Quantité: ");

                MenuComplement mc = new MenuComplement(
                        menuComplementService.numberOfRows() + 1,
                        menu.getId(),
                        complementId,
                        quantite
                );
                menuComplementService.createMenuComplement(mc);

                double prixComplement = complementService
                        .selectById(complementId)
                        .get()
                        .getPrix();

                total += prixComplement * quantite;

                auMoinsUn = true;
            }

            return total;
        }

        public void modifierMenu(Scanner scanner) {
            int menuId = Integer.parseInt(saisieChaine(scanner, "Saisir l'ID du menu à modifier : "));
            Menu menu = menuService.selectById(menuId).orElse(null);
            afficheMenus();

            if (menu == null) {
                System.out.println("Menu introuvable !");
                return;
            }

            System.out.println("Modification du menu : " + menu.getLibelle());

            String nouveauLibelle = saisieChaine(scanner, "Nouveau libellé (" + menu.getLibelle() + ") : ");
            if (!nouveauLibelle.isEmpty()) menu.setLibelle(nouveauLibelle);

            String nouvelleImage = cloudinaryService.uploadImage();
            if (nouvelleImage != null) menu.setImageUrl(nouvelleImage);

            List<MenuBurger> anciensBurgers = menuBurgerService.findByMenuId(menu.getId());
            for (MenuBurger mb : anciensBurgers) menuBurgerService.delete(mb.getId());

            List<MenuComplement> anciensComplements = menuComplementService.findByMenuId(menu.getId());
            for (MenuComplement mc : anciensComplements) menuComplementService.delete(mc.getId());

            double total = 0;
            total += ajoutBurger(total, menu);
            total += ajoutComplement(total, menu, TypeComplement.BOISSON);
            total += ajoutComplement(total, menu, TypeComplement.FRITE);

            menu.setPrix(total);
            menuService.update(menu);

            System.out.println("Menu modifié avec succès !");
        }


        public void afficheMenus() {
            List<Menu> menus = menuService.selectAll();

            if (menus.isEmpty()) {
                System.out.println("Aucun menu disponible.");
                return;
            }

            for (Menu menu : menus) {
                System.out.println(menu);

                System.out.println("Burgers :");
                List<MenuBurger> menuBurgers = menuBurgerService.findByMenuId(menu.getId());

                if (menuBurgers.isEmpty()) {
                    System.out.println("   Aucun burger.");
                } else {
                    for (MenuBurger mb : menuBurgers) {
                        System.out.println(burgerService.selectById(mb.getBurgerId()).get());
                    }
                }

                System.out.println("Compléments :");
                List<MenuComplement> menuComplements = menuComplementService.findByMenuId(menu.getId());

                if (menuComplements.isEmpty()) {
                    System.out.println("   Aucun complément.");
                } else {
                    for (MenuComplement mc : menuComplements) {
                        System.out.println(complementService.selectById(mc.getComplementId()).get());
                    }
                }

                System.out.println("==============================\n");
            }
        }

    }
