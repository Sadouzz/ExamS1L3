package sn.brasilburger.config.factory.repository;


import sn.brasilburger.Repository.Impl.*;
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
                return null;
            case BurgerCategorie:
                return null;
            case Complement:
                return null;
            case Menu:
                return null;
            case MenuBurger:
                return null;
            case MenuComplement:
                return null;
            default:
                return null;
        }
    }
}
