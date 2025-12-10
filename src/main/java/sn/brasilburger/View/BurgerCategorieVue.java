package sn.brasilburger.View;

import sn.brasilburger.Entity.BurgerCategorie;
import sn.brasilburger.Service.BurgerCategorieService;

import java.util.List;
import java.util.Scanner;

public class BurgerCategorieVue extends Vue {
    private BurgerCategorieService service;

    public BurgerCategorieVue(BurgerCategorieService service) {
        this.service = service;
    }

    public BurgerCategorie saisieBurgerCategorie(Scanner scanner) {
        BurgerCategorie bc = new BurgerCategorie();
        bc.setId(service.numberOfRows() + 1);

        bc.setNom(saisieChaine(scanner, "Nom catégorie : "));
        return bc;
    }

    public void afficheBurgerCategories() {
        List<BurgerCategorie> liste = service.selectAll();
        if (liste.isEmpty()) {
            System.out.println("Aucune catégorie.");
        } else {
            liste.forEach(System.out::println);
        }
    }
}
