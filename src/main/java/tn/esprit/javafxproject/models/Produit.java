package tn.esprit.javafxproject.models;

import java.sql.Blob;

public class Produit {
    private int productID;
    private String productName;
    private String productDescription;
    private double price;
    private int quantityInStock;

    public Produit() {
    }

    public Produit(int productID, String productName, String productDescription, double price, int quantityInStock) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
