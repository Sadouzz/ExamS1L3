package sn.brasilburger.View;

import sn.brasilburger.Entity.Burger;
import sn.brasilburger.Entity.BurgerCategorie;
import sn.brasilburger.Service.BurgerCategorieService;
import sn.brasilburger.Service.BurgerService;
import sn.brasilburger.Service.CloudinaryService;

import java.util.List;
import java.util.Scanner;

public class BurgerVue extends Vue {
    private BurgerService burgerService;
    private BurgerCategorieService burgerCategorieService;
    private BurgerCategorieVue burgerCategorieVue;
    private final CloudinaryService cloudinaryService;

    public BurgerVue(BurgerService burgerService, BurgerCategorieService burgerCategorieService, CloudinaryService cloudinaryService, BurgerCategorieVue burgerCategorieVue) {

        this.burgerService = burgerService;
        this.burgerCategorieService = burgerCategorieService;
        this.burgerCategorieVue = burgerCategorieVue;
        this.cloudinaryService = cloudinaryService;
    }

    public Burger saisieBurger(Scanner scanner) {
        Burger b = new Burger();
        b.setId(burgerService.numberOfRows() + 1);

        b.setLibelle(saisieChaine(scanner, "Libellé : "));
        b.setDesc(saisieChaine(scanner, "Description : "));
        b.setPrix(Double.parseDouble(saisieChaine(scanner, "Prix : ")));

        String imageUrl = cloudinaryService.uploadImage();

        if (imageUrl != null) {
            System.out.println("Image disponible à : " + imageUrl);
        }
        b.setImageUrl(imageUrl);

        b.setArchived(false);

        do {
            System.out.println("============================");
            burgerCategorieVue.afficheBurgerCategories();
            if(!burgerCategorieService.selectAll().isEmpty())
            {
                System.out.println("Choix de l'ID de la catégorie de votre burger");
                b.setBurgerCategorieId(Integer.parseInt(saisieChaine(scanner, "ID Catégorie : ")));
            }
            else {
                System.out.println("Liste de catégories de burger vide");
                System.out.println("Veuillez créer une catégorie de burger!");
                BurgerCategorie burgerCategorie = burgerCategorieVue.saisieBurgerCategorie(scanner);
                burgerCategorieService.createBurgerCategorie(burgerCategorie);
                b.setBurgerCategorieId(burgerCategorie.getId());
            }
        }while (burgerCategorieService.selectById(b.getBurgerCategorieId()).isEmpty());

        return b;
    }

    public void afficheBurgers() {
        List<Burger> liste = burgerService.selectAll();
        if (liste.isEmpty()) {
            System.out.println("Aucun burger.");
        } else {
            liste.forEach(System.out::println);
        }
    }
}
