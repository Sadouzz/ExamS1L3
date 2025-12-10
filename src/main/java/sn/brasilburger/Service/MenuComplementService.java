package sn.brasilburger.Service;

import sn.brasilburger.Entity.MenuComplement;

import java.util.List;
import java.util.Optional;

public interface MenuComplementService {
    void createMenuComplement(MenuComplement menuComplement);

    Optional<MenuComplement> selectById(int id);

    List<MenuComplement> selectAll();

    int numberOfRows();
}


