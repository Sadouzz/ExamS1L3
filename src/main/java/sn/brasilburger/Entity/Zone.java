package sn.brasilburger.Entity;

public class Zone {
    private int id;
    private String nom;
    private Double prixLivraison;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrixLivraison() {
        return prixLivraison;
    }

    public void setPrixLivraison(Double prixLivraison) {
        this.prixLivraison = prixLivraison;
    }

    public Zone() {
    }

    public Zone(int id, String nom, Double prixLivraison) {
        this.id = id;
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }
}