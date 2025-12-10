package sn.brasilburger.Repository;


import sn.brasilburger.Entity.Menu;

public interface MenuRepository {
    int numberOfRows();
    int insert(Menu menu);

    int update(Menu menu);

}
