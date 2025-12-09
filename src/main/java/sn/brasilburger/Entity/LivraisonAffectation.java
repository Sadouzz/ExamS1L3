package sn.brasilburger.Entity;

import sn.brasilburger.Entity.Enum.StatutLivraison;

public class LivraisonAffectation {
    private int id;
    private int commandeId;
    private int livreurId;
    private int zoneId;
    private StatutLivraison statut;

    public LivraisonAffectation() {
    }

    public LivraisonAffectation(int id, int commandeId, int livreurId, int zoneId, StatutLivraison statut) {
        this.id = id;
        this.commandeId = commandeId;
        this.livreurId = livreurId;
        this.zoneId = zoneId;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public int getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(int livreurId) {
        this.livreurId = livreurId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public StatutLivraison getStatut() {
        return statut;
    }

    public void setStatut(StatutLivraison statut) {
        this.statut = statut;
    }
}