package sn.brasilburger.Repository;


import sn.brasilburger.Entity.MenuComplement;

import java.util.List;
import java.util.Optional;

public interface MenuComplementRepository {
    int numberOfRows();
    int insert(MenuComplement menuComplement);
    Optional<MenuComplement> selectById(int id);

    List<MenuComplement> selectAll();


}
