package sn.brasilburger.config.factory.service;

import sn.brasilburger.Repository.*;
import sn.brasilburger.Service.Impl.*;
import sn.brasilburger.config.factory.repository.EntityName;
import sn.brasilburger.config.factory.repository.RepositoryFactory;

public final class ServiceFactory {

    public static Object getInstance(EntityName entityName) {
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
