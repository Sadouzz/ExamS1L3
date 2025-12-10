package sn.brasilburger.Service.Impl;

import sn.brasilburger.Entity.MenuBurger;
import sn.brasilburger.Repository.MenuBurgerRepository;
import sn.brasilburger.Service.MenuBurgerService;

import java.util.List;
import java.util.Optional;

public class MenuBurgerServiceImpl implements MenuBurgerService {

    private MenuBurgerRepository menuBurgerRepository;

    public MenuBurgerServiceImpl(MenuBurgerRepository menuBurgerRepository) {
        this.menuBurgerRepository = menuBurgerRepository;
    }

    @Override
    public void createMenuBurger(MenuBurger menuBurger) {
        menuBurgerRepository.insert(menuBurger);
    }

    @Override
    public Optional<MenuBurger> selectById(int id) {
        return menuBurgerRepository.selectById(id);
    }

    @Override
    public List<MenuBurger> selectAll() {
        return menuBurgerRepository.selectAll();
    }

    @Override
    public int numberOfRows()
    {
        return menuBurgerRepository.numberOfRows();
    }

}
