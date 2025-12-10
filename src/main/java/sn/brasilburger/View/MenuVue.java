package sn.brasilburger.View;

import sn.brasilburger.Entity.Menu;
import sn.brasilburger.Entity.MenuBurger;
import sn.brasilburger.Entity.MenuComplement;
import sn.brasilburger.Service.*;

import java.util.List;
import java.util.Scanner;

public class MenuVue extends Vue {
    private MenuService menuService;
    private final MenuBurgerVue menuBurgerVue;
    private final MenuComplementVue menuComplementVue;
    private final BurgerVue burgerVue;
    private final ComplementVue complementVue;

    public MenuVue(MenuService menuService, MenuBurgerVue menuBurgerVue, MenuComplementVue menuComplementVue, BurgerVue burgerVue, ComplementVue complementVue) {
        this.menuService = menuService;
        this.menuBurgerVue = menuBurgerVue;
        this.menuComplementVue = menuComplementVue;
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

}
