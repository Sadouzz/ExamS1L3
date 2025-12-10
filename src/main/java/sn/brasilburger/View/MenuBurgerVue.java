package sn.brasilburger.View;

import sn.brasilburger.Entity.MenuBurger;
import sn.brasilburger.Service.MenuBurgerService;

import java.util.List;
import java.util.Scanner;

public class MenuBurgerVue extends Vue {
    private MenuBurgerService service;

    public MenuBurgerVue(MenuBurgerService service) {
        this.service = service;
    }

    public void afficheMenuBurgers() {
        List<MenuBurger> liste = service.selectAll();
        if (liste.isEmpty()) {
            System.out.println("Aucun menu-burger.");
        } else {
            liste.forEach(System.out::println);
        }
    }
}
