package sn.brasilburger.Entity;

public class CommandeItem {
    private int id;
    private int commandeId;
    private int burgerId;
    private int menuId;
    private int complementId;
    private int quantite;
    private Double prixTotal;

    public CommandeItem() {
    }

    public CommandeItem(int id, int commandeId, int burgerId, int menuId, int complementId, int quantite, Double prixTotal) {
        this.id = id;
        this.commandeId = commandeId;
        this.burgerId = burgerId;
        this.menuId = menuId;
        this.complementId = complementId;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
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

    public int getBurgerId() {
        return burgerId;
    }

    public void setBurgerId(int burgerId) {
        this.burgerId = burgerId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getComplementId() {
        return complementId;
    }

    public void setComplementId(int complementId) {
        this.complementId = complementId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }
}