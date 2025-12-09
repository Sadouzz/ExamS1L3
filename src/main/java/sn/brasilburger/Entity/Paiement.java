package sn.brasilburger.Entity;

import sn.brasilburger.Entity.Enum.MoyenPaiement;

public class Paiement {
    private int id;
    private Double montant;
    private String refTransaction;
    private String date;
    private MoyenPaiement moyenPaiement;
    private int commandeId;

    public Paiement() {
    }

    public Paiement(int id, Double montant, String refTransaction, String date, MoyenPaiement moyenPaiement, int commandeId) {
        this.id = id;
        this.montant = montant;
        this.refTransaction = refTransaction;
        this.date = date;
        this.moyenPaiement = moyenPaiement;
        this.commandeId = commandeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getRefTransaction() {
        return refTransaction;
    }

    public void setRefTransaction(String refTransaction) {
        this.refTransaction = refTransaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MoyenPaiement getMoyenPaiement() {
        return moyenPaiement;
    }

    public void setMoyenPaiement(MoyenPaiement moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }
}