package sn.brasilburger.View;

import sn.brasilburger.Entity.Burger;
import sn.brasilburger.Entity.BurgerCategorie;
import sn.brasilburger.Entity.Complement;
import sn.brasilburger.Entity.Menu;
import sn.brasilburger.Service.BurgerCategorieService;
import sn.brasilburger.Service.BurgerService;
import sn.brasilburger.Service.ComplementService;
import sn.brasilburger.Service.Impl.ComplementServiceImpl;
import sn.brasilburger.Service.MenuService;

import java.util.Scanner;

public class MenuPrincipal {

    private final BurgerVue burgerVue;
    private final BurgerCategorieVue burgerCategorieVue;
    private final MenuVue menuVue;
    private final ComplementVue complementVue;


    private final BurgerCategorieService burgerCategorieService;
    private final BurgerService burgerService;
    private final ComplementService complementService;
    private final MenuService menuService;

    public MenuPrincipal(BurgerVue burgerVue, BurgerCategorieVue burgerCategorieVue, MenuVue menuVue, ComplementVue complementVue, BurgerCategorieService burgerCategorieService,
                         BurgerService burgerService, ComplementService complementService, MenuService menuService) {
        this.burgerVue = burgerVue;
        this.burgerCategorieVue = burgerCategorieVue;
        this.menuVue = menuVue;
        this.complementVue = complementVue;
        this.burgerCategorieService = burgerCategorieService;
        this.burgerService = burgerService;
        this.complementService = complementService;
        this.menuService = menuService;
    }

    public void afficher(Scanner scanner) {
        int choix = -1;

        do {
            AffichageMenus.afficherMenuPrincipal();
            choix = lireEntier(scanner);

            switch (choix) {
                case 1 : afficherMenuBurger(scanner);
                case 2 : afficherMenuMenu(scanner);
                case 3 : afficherMenuComplement(scanner);
                case 4 : System.out.println("Au revoir !");
                default : System.out.println("Choix invalide, réessayez.");
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
                case 2 -> {
                    BurgerCategorie burgerCategorie = burgerCategorieVue.saisieBurgerCategorie(scanner);
                    burgerCategorieService.createBurgerCategorie(burgerCategorie);
                    System.out.println("BurgerCategorie ajouté !");
                }
                case 3 -> {  }
                case 4 -> {  }
                case 5 -> burgerVue.afficheBurgers();
                case 6 -> {  }
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }

    private void afficherMenuMenu(Scanner scanner) {
        int choix = -1;
        do {
            AffichageMenus.afficherMenuMenu();
            choix = lireEntier(scanner);

            switch (choix) {
                case 1 -> {
                    Menu menu = menuVue.saisieMenu(scanner);
                    menuService.createMenu(menu);
                    System.out.println("Menu ajouté !");
                }
                case 2 -> {}
                case 3 -> {  }
                case 4 -> menuVue.afficheMenus();
                case 5 -> {  }
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }

    private void afficherMenuComplement(Scanner scanner) {
        int choix = -1;
        do {
            AffichageMenus.afficherMenuComplement();
            choix = lireEntier(scanner);

            switch (choix) {
                case 1 -> {
                    Complement complement = complementVue.saisieComplement(scanner);
                    complementService.createComplement(complement);
                    System.out.println("Complément ajouté !");
                }
                case 2 -> {

                }
                case 3 -> {  }
                case 4 -> complementVue.afficheComplements();
                case 5 -> {  }
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
