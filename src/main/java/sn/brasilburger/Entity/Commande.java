package sn.brasilburger.Entity;

import sn.brasilburger.Entity.Enum.StatutCommande;
import sn.brasilburger.Entity.Enum.TypeRetraitCommande;

public class Commande {
    private int id;
    private String adresse;
    private Double montantHorsLivraison;
    private Double montantTotal;
    private String createdAt;
    private String updatedAt;
    private Boolean isPaid;
    private StatutCommande statut;
    private TypeRetraitCommande typeRetrait;

    public Commande() {
    }

    public Commande(int id, String adresse, Double montantHorsLivraison, Double montantTotal, String createdAt, String updatedAt, Boolean isPaid, StatutCommande statut, TypeRetraitCommande typeRetrait) {
        this.id = id;
        this.adresse = adresse;
        this.montantHorsLivraison = montantHorsLivraison;
        this.montantTotal = montantTotal;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isPaid = isPaid;
        this.statut = statut;
        this.typeRetrait = typeRetrait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getMontantHorsLivraison() {
        return montantHorsLivraison;
    }

    public void setMontantHorsLivraison(Double montantHorsLivraison) {
        this.montantHorsLivraison = montantHorsLivraison;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public TypeRetraitCommande getTypeRetrait() {
        return typeRetrait;
    }

    public void setTypeRetrait(TypeRetraitCommande typeRetrait) {
        this.typeRetrait = typeRetrait;
    }
}