package tn.esprit.javafxproject.models;

public class LigneDeCommande {

    private int purchaseID;
    private int productID;
    private double price;
    private String status;
    private int quantityOrdred;
    private Produit produit;
    private Achat achat;
    public LigneDeCommande() {
    }

    public LigneDeCommande(int purchaseID, int productID, double price, String status, int quantityOrdred) {
        this.purchaseID = purchaseID;
        this.productID = productID;
        this.price = price;
        this.status = status;
        this.quantityOrdred = quantityOrdred;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantityOrdred() {
        return quantityOrdred;
    }

    public void setQuantityOrdred(int quantityOrdred) {
        this.quantityOrdred = quantityOrdred;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Achat getAchat() {
        return achat;
    }

    public void setAchat(Achat achat) {
        this.achat = achat;
    }


    @Override
    public String toString() {
        return "LigneDeCommande{" +
                "purchaseID=" + purchaseID +
                ", productID=" + productID +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", quantityOrdred=" + quantityOrdred +
                ", produit=" + produit +
                ", achat=" + achat +
                '}';
    }
}