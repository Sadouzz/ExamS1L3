package sn.brasilburger.View;

import sn.brasilburger.Entity.Menu;
import sn.brasilburger.Entity.MenuBurger;
import sn.brasilburger.Entity.MenuComplement;
import sn.brasilburger.Service.*;

import java.util.List;
import java.util.Scanner;

public class MenuVue extends Vue {
    private final MenuService menuService;
    private final MenuBurgerService menuBurgerService;
    private final MenuComplementService menuComplementService;

    private final BurgerVue burgerVue;
    private final ComplementVue complementVue;

    public MenuVue(MenuService menuService, MenuBurgerService menuBurgerService, MenuComplementService menuComplementService, BurgerVue burgerVue, ComplementVue complementVue) {
        this.menuService = menuService;
        this.menuBurgerService = menuBurgerService;
        this.menuComplementService = menuComplementService;
        this.burgerVue = burgerVue;
        this.complementVue = complementVue;
    }

    public Menu saisieMenu(Scanner scanner) {
        Menu menu = new Menu();
        menu.setId(menuService.numberOfRows() + 1);

        menu.setLibelle(saisieChaine(scanner, "Saisir le libellé : "));
        menu.setImageUrl(saisieChaine(scanner, "Saisir l'URL de l'image : "));
        menu.setArchived(false);

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

        System.out.println("\n=== AJOUT DES BURGERS AU MENU ===");
        while (true) {
            String rep = saisieChaine(scanner, "Ajouter un burger ? (o/n) : ");
            if (rep.equalsIgnoreCase("n")) break;

            burgerVue.afficheBurgers();

            int burgerId;
            do {

                burgerId = Integer.parseInt(saisieChaine(scanner, "ID du burger : "));
            }while (burgerService.selectById(burgerId).isEmpty());
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
        }

        System.out.println("\n=== AJOUT DES COMPLÉMENTS AU MENU ===");
        while (true) {
            String rep = saisieChaine(scanner, "Ajouter un complément ? (o/n) : ");
            if (rep.equalsIgnoreCase("n")) break;

            complementVue.afficheComplements();

            int complementId;
            do {

                complementId = Integer.parseInt(saisieChaine(scanner, "ID du complément : "));
            }while (complementService.selectById(complementId).isEmpty());

            int quantite = Vue.saisieIntPositive(scanner, "Quantité: ");

            MenuComplement mc = new MenuComplement(
                    menuComplementService.numberOfRows() + 1,
                    menu.getId(),
                    complementId,
                    quantite
            );
            menuComplementService.createMenuComplement(mc);

            double prixComplement = complementService.selectById(complementId).get().getPrix();
            total += prixComplement * quantite;
        }

        menu.setPrix(total);
        menuService.update(menu);

        System.out.println("\n=== MENU CRÉÉ ===");
        System.out.println("Libellé : " + menu.getLibelle());
        System.out.println("Prix final : " + total + " FCFA");

        return menu;
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
            List<MenuBurger> burgers = menuBurgerService.findByMenuId(menu.getId());

            if (burgers.isEmpty()) {
                System.out.println("   Aucun burger.");
            } else {
                for (MenuBurger mb : burgers) {
                    System.out.println(mb);
                }
            }

            System.out.println("Compléments :");
            List<MenuComplement> complements = menuComplementService.findByMenuId(menu.getId());

            if (complements.isEmpty()) {
                System.out.println("   Aucun complément.");
            } else {
                for (MenuComplement mc : complements) {
                    System.out.println(mc);
                }
            }

            System.out.println("==============================\n");
        }
    }

}
