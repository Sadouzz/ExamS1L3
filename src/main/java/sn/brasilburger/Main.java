package sn.brasilburger;

import sn.brasilburger.Entity.BurgerCategorie;
import sn.brasilburger.Repository.*;
import sn.brasilburger.Repository.Impl.*;
import sn.brasilburger.Service.*;
import sn.brasilburger.Service.Impl.*;
import sn.brasilburger.View.*;
import sn.brasilburger.config.database.Database;
import sn.brasilburger.config.database.DatabaseImpl;
import sn.brasilburger.config.factory.repository.EntityName;
import sn.brasilburger.config.factory.repository.RepositoryFactory;
import sn.brasilburger.config.factory.service.ServiceFactory;

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

        BurgerCategorieService burgerCategorieService = (BurgerCategorieService) ServiceFactory.getInstance((EntityName.BurgerCategorie));
        BurgerService burgerService = (BurgerService) ServiceFactory.getInstance((EntityName.Burger));
        MenuService menuService = (MenuService) ServiceFactory.getInstance((EntityName.Menu));
        MenuComplementService menuComplementService = (MenuComplementService) ServiceFactory.getInstance((EntityName.MenuComplement));
        MenuBurgerService menuBurgerService = (MenuBurgerService) ServiceFactory.getInstance((EntityName.MenuBurger));
        ComplementService complementService = (ComplementService) ServiceFactory.getInstance((EntityName.Complement));

        BurgerCategorieVue burgerCategorieVue = new BurgerCategorieVue(burgerCategorieService);
        BurgerVue burgerVue = new BurgerVue(burgerService, burgerCategorieService, burgerCategorieVue);
        ComplementVue complementVue = new ComplementVue(complementService);
        MenuVue menuVue = new MenuVue(menuService, menuBurgerService, menuComplementService, burgerVue, complementVue);

        MenuPrincipal menuPrincipal = new MenuPrincipal(burgerVue, burgerCategorieVue, menuVue, complementVue, burgerCategorieService, burgerService, complementService, menuService);

        menuPrincipal.afficher(scanner);
    }
}