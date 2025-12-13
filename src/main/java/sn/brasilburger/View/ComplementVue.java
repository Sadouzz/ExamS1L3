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

    public Complement modifierComplement(Scanner scanner) {
        if (service.selectAll().isEmpty()) {
            System.out.println("Aucun complément à modifier !");
            return null;
        }
        afficheComplements();

        int complementId;
        do {
            complementId = Integer.parseInt(saisieChaine(scanner, "ID du complément à modifier : "));
        } while (service.selectById(complementId).isEmpty());

        Complement complement = service.selectById(complementId).get();

        System.out.println("Modification du complément : " + complement.getLibelle());

        String libelle = saisieChaine(scanner, "Nouveau libellé (" + complement.getLibelle() + ") : ");
        if (!libelle.isEmpty()) complement.setLibelle(libelle);

        String prixStr = saisieChaine(scanner, "Nouveau prix (" + complement.getPrix() + ") : ");
        if (!prixStr.isEmpty()) complement.setPrix(Double.parseDouble(prixStr));

        String imageUrl = cloudinaryService.uploadImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            System.out.println("Nouvelle image disponible à : " + imageUrl);
            complement.setImageUrl(imageUrl);
        }

        int typeComp;
        do {
            System.out.println("Type de complément:");
            System.out.println("1 - Boisson");
            System.out.println("2 - Frite");
            typeComp = Integer.parseInt(saisieChaine(scanner, "ID du complément : "));
        }while (typeComp != 1 && typeComp != 2);

        complement.setTypeComplement(TypeComplement.getOptionByValue(typeComp));

        int updated = service.update(complement);
        if (updated > 0) {
            System.out.println("Complément mis à jour avec succès !");
        } else {
            System.out.println("Erreur lors de la mise à jour du complément.");
        }

        return complement;
    }

    public void archiverComplement(Scanner scanner) {
        if (service.selectAll().isEmpty()) {
            System.out.println("Aucun complément à archiver !");
            return;
        }
        afficheComplements();

        int complementId;
        do {
            complementId = Integer.parseInt(saisieChaine(scanner, "ID du complément à archiver/désarchiver : "));
        } while (service.selectById(complementId).isEmpty());

        Complement complement = service.selectById(complementId).get();

        System.out.println("Complément sélectionné : " + complement.getLibelle());
        System.out.println("État actuel : " + (complement.getArchived() ? "Archivé" : "Actif"));

        String rep = saisieChaine(scanner, "Voulez-vous changer son état ? (o/n) : ");
        if (rep.equalsIgnoreCase("o")) {
            complement.setArchived(!complement.getArchived());
            int updated = service.update(complement);
            if (updated > 0) {
                System.out.println("État du complément modifié : " + (complement.getArchived() ? "Archivé" : "Actif"));
            } else {
                System.out.println("Erreur lors de la modification de l'état du complément.");
            }
        } else {
            System.out.println("Aucune modification effectuée.");
        }
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
