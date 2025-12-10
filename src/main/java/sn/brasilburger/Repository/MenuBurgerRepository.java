package sn.brasilburger.Repository;


import sn.brasilburger.Entity.MenuBurger;

import java.util.List;
import java.util.Optional;

public interface MenuBurgerRepository {
    int numberOfRows();
    int insert(MenuBurger menuBurger);
    Optional<MenuBurger> selectById(int id);

    List<MenuBurger> selectAll();


}
