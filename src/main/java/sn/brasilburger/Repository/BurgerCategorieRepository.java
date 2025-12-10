package sn.brasilburger.Repository;


import sn.brasilburger.Entity.BurgerCategorie;

import java.util.List;
import java.util.Optional;

public interface BurgerCategorieRepository {
    int numberOfRows();
    int insert(BurgerCategorie burgerCategorie);
    Optional<BurgerCategorie> selectById(int id);

    List<BurgerCategorie> selectAll();


}
