package sn.brasilburger.View;

import sn.brasilburger.Entity.Burger;
import sn.brasilburger.Entity.Complement;
import sn.brasilburger.Entity.Menu;
import sn.brasilburger.Service.BurgerService;

import java.util.Scanner;

public class MenuPrincipal {

    private final BurgerVue burgerVue;

    private final BurgerService burgerService;

    public MenuPrincipal(BurgerVue burgerVue,
                         BurgerService burgerService) {
        this.burgerVue = burgerVue;
        this.burgerService = burgerService;
    }

    public void afficher(Scanner scanner) {
        int choix = -1;

        do {
            AffichageMenus.afficherMenuPrincipal();
            choix = lireEntier(scanner);

            switch (choix) {
                case 1 -> afficherMenuBurger(scanner);
                case 2 : //afficherMenuMenu(scanner);
                case 3 : //afficherMenuComplement(scanner);
                case 4 : System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide, réessayez.");
            }
        } while (choix != 4);
    }

    private void afficherMenuBurger(Scanner scanner) {
        int choix = -1;
        do {
            AffichageMenus.afficherMenuBurger();
            choix = lireEntier(scanner);

            switch (choix) {
                case 1 -> {
                    Burger burger = burgerVue.saisieBurger(scanner);
                    burgerService.createBurger(burger);
                    System.out.println("Burger ajouté !");
                }
                case 2 -> burgerVue.afficheBurgers();
                case 3 -> {  }
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }

    private int lireEntier(Scanner scanner) {
        String line = scanner.nextLine();
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
