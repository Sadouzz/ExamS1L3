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
        //?user=neondb_owner&password=npg_3XJcjmSFD4Vr&sslmode=require&channelBinding=require
        Database database = DatabaseImpl.getInstance(
                "org.postgresql.Driver",
                "jdbc:postgresql://ep-billowing-unit-adx3mpgt-pooler.c-2.us-east-1.aws.neon.tech/brasilburger",
                "neondb_owner",
                "npg_3XJcjmSFD4Vr"
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
        MenuVue menuVue = new MenuVue(menuService, burgerService, complementService, menuBurgerService, menuComplementService, burgerVue, complementVue);

        MenuPrincipal menuPrincipal = new MenuPrincipal(burgerVue, burgerCategorieVue, menuVue, complementVue, burgerCategorieService, burgerService, complementService, menuService, menuBurgerService, menuComplementService);

        menuPrincipal.afficher(scanner);
    }
}