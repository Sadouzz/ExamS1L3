package sn.brasilburger.config.factory.repository;


import sn.brasilburger.Repository.*;
import sn.brasilburger.Repository.Impl.*;
import sn.brasilburger.Service.Impl.*;
import sn.brasilburger.config.factory.database.DatabaseFactory;

public final class RepositoryFactory {
    private static final PersistanceName persistanceName = PersistanceName.Database;

    public static Object getInstance(EntityName entityName) {
        switch (persistanceName) {
            case List:
                return null;
            case Database:
                return getRepositoryDatabase(entityName);
            default:
                return null;
        }
    }

    public static Object getRepositoryDatabase(EntityName entityName) {
        switch (entityName) {
            case Burger:
                return new BurgerRepositoryImpl(DatabaseFactory.getInstance());
            case BurgerCategorie:
                return new BurgerCategorieRepositoryImpl(DatabaseFactory.getInstance());
            case Complement:
                return new ComplementRepositoryImpl(DatabaseFactory.getInstance());
            case Menu:
                return new MenuRepositoryImpl(DatabaseFactory.getInstance());
            case MenuBurger:
                return new MenuBurgerRepositoryImpl(DatabaseFactory.getInstance());
            case MenuComplement:
                return new MenuComplementRepositoryImpl(DatabaseFactory.getInstance());
            default:
                return null;
        }
    }
}
