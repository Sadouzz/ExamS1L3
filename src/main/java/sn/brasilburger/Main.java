package sn.brasilburger;

import sn.brasilburger.Entity.BurgerCategorie;
import sn.brasilburger.Repository.*;
import sn.brasilburger.Repository.Impl.*;
import sn.brasilburger.Service.*;
import sn.brasilburger.Service.Impl.*;
import sn.brasilburger.View.*;
import sn.brasilburger.config.database.Database;
import sn.brasilburger.config.database.DatabaseImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database = DatabaseImpl.getInstance(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/brasilburger",
                "postgres",
                "passer"
        );
        Scanner scanner = new Scanner(System.in);

        BurgerCategorieRepository burgerCategorieRepository = new BurgerCategorieRepositoryImpl(database);
        BurgerRepository burgerRepository = new BurgerRepositoryImpl(database);
        MenuRepository menuRepository = new MenuRepositoryImpl(database);
        MenuComplementRepository menuComplementRepository = new MenuComplementRepositoryImpl(database);
        MenuBurgerRepository menuBurgerRepository = new MenuBurgerRepositoryImpl(database);
        ComplementRepository complementRepository = new ComplementRepositoryImpl(database);

        BurgerCategorieService burgerCategorieService = new BurgerCategorieServiceImpl(burgerCategorieRepository);
        BurgerService burgerService = new BurgerServiceImpl(burgerRepository);
        MenuService menuService = new MenuServiceImpl(menuRepository);
        MenuBurgerService menuBurgerService = new MenuBurgerServiceImpl(menuBurgerRepository);
        MenuComplementService menuComplementService = new MenuComplementServiceImpl(menuComplementRepository);
        ComplementService complementService = new ComplementServiceImpl(complementRepository);

        BurgerCategorieVue burgerCategorieVue = new BurgerCategorieVue(burgerCategorieService);
        BurgerVue burgerVue = new BurgerVue(burgerService, burgerCategorieService, burgerCategorieVue);
        ComplementVue complementVue = new ComplementVue(complementService);
        MenuVue menuVue = new MenuVue(menuService, menuBurgerService, menuComplementService, burgerVue, complementVue);

        MenuPrincipal menuPrincipal = new MenuPrincipal(burgerVue, menuVue, complementVue, burgerService, complementService, menuService);

        menuPrincipal.afficher(scanner);
    }
}