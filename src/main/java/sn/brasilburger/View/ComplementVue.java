package sn.brasilburger.View;

import sn.brasilburger.Entity.Complement;
import sn.brasilburger.Entity.Enum.TypeComplement;
import sn.brasilburger.Service.CloudinaryService;
import sn.brasilburger.Service.ComplementService;

import java.util.List;
import java.util.Scanner;

public class ComplementVue extends Vue {
    private ComplementService service;
    private final CloudinaryService cloudinaryService;

    public ComplementVue(ComplementService service, CloudinaryService cloudinaryService) {
        this.service = service;
        this.cloudinaryService = cloudinaryService;
    }

    public Complement saisieComplement(Scanner scanner) {
        Complement c = new Complement();
        c.setId(service.numberOfRows() + 1);

        c.setLibelle(saisieChaine(scanner, "Libellé : "));
        c.setPrix(Double.parseDouble(saisieChaine(scanner, "Prix : ")));

        String imageUrl = cloudinaryService.uploadImage();

        if (imageUrl != null) {
            System.out.println("Image disponible à : " + imageUrl);
        }
        c.setImageUrl(imageUrl);

        c.setArchived(false);

        int typeComp;
        do {
            System.out.println("Type de complément:");
            System.out.println("1 - Boisson");
            System.out.println("2 - Frite");
            typeComp = Integer.parseInt(saisieChaine(scanner, "ID du complément : "));
        }while (typeComp != 1 && typeComp != 2);

        c.setTypeComplement(TypeComplement.getOptionByValue(typeComp));

        return c;
    }

    public void afficheComplements() {
        List<Complement> liste = service.selectAll();
        if (liste.isEmpty()) {
            System.out.println("Aucun complément.");
        } else {
            liste.forEach(System.out::println);
        }
    }

    public void afficheComplementsParType(TypeComplement typeComplement) {
        List<Complement> liste = service.selectByType(typeComplement);
        if (liste.isEmpty()) {
            System.out.println("Aucune " + typeComplement.name().toLowerCase());
        } else {
            liste.forEach(System.out::println);
        }
    }
}
