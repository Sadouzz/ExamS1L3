package sn.brasilburger.View;

import sn.brasilburger.Entity.Burger;
import sn.brasilburger.Service.BurgerCategorieService;
import sn.brasilburger.Service.BurgerService;

import java.util.List;
import java.util.Scanner;

public class BurgerVue extends Vue {
    private BurgerService burgerService;
    private BurgerCategorieService burgerCategorieservice;
    private BurgerCategorieVue burgerCategorieVue;

    public BurgerVue(BurgerService burgerService, BurgerCategorieVue burgerCategorieVue) {

        this.burgerService = burgerService;
        this.burgerCategorieVue = burgerCategorieVue;
    }

    public Burger saisieBurger(Scanner scanner) {
        Burger b = new Burger();
        b.setId(burgerService.numberOfRows() + 1);

        b.setLibelle(saisieChaine(scanner, "Libellé : "));
        b.setDesc(saisieChaine(scanner, "Description : "));
        b.setPrix(Double.parseDouble(saisieChaine(scanner, "Prix : ")));
        b.setImageUrl(saisieChaine(scanner, "URL Image : "));
        b.setArchived(false);

        do {
            burgerCategorieVue.afficheBurgerCategories();
            b.setBurgerCategorieId(Integer.parseInt(saisieChaine(scanner, "ID Catégorie : ")));
        }while (burgerCategorieservice.selectById(b.getBurgerCategorieId()).isEmpty());

        return b;
    }
}
