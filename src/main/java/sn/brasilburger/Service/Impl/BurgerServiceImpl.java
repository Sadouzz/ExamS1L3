package sn.brasilburger.Service.Impl;

import sn.brasilburger.Entity.Burger;
import sn.brasilburger.Repository.BurgerRepository;
import sn.brasilburger.Service.BurgerService;

import java.util.List;
import java.util.Optional;

public class BurgerServiceImpl implements BurgerService {

    private BurgerRepository burgerRepository;

    public BurgerServiceImpl(BurgerRepository burgerRepository) {
        this.burgerRepository = burgerRepository;
    }

    @Override
    public void createBurger(Burger burger) {
        burgerRepository.insert(burger);
    }

    @Override
    public int numberOfRows()
    {
        return burgerRepository.numberOfRows();
    }

    @Override
    public Optional<Burger> selectById(int id) {
        return burgerRepository.selectById(id);
    }

    @Override
    public List<Burger> selectAll() {
        return burgerRepository.selectAll();
    }

}
