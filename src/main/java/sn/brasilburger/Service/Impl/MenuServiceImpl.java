package sn.brasilburger.Service.Impl;

import sn.brasilburger.Entity.Menu;
import sn.brasilburger.Repository.MenuRepository;
import sn.brasilburger.Service.MenuService;

import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void createMenu(Menu menu) {
        menuRepository.insert(menu);
    }


    @Override
    public int numberOfRows()
    {
        return menuRepository.numberOfRows();
    }

    @Override
    public void update(Menu menu) {
        menuRepository.update(menu);
    }

}
