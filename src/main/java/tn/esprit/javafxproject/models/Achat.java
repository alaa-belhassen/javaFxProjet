package tn.esprit.javafxproject.models;

import java.sql.Date;
import java.time.LocalDate;

public class Achat {
    private int purchaseID;
    private LocalDate purchaseDate;
    private int idUser;
    private double totalAmount;
    private String paymentStatus;

    public Achat() {
    }

    public Achat(int purchaseID, LocalDate purchaseDate, int idUser, double totalAmount, String paymentStatus) {
        this.purchaseID = purchaseID;
        this.purchaseDate = purchaseDate;
        this.idUser = idUser;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Achat{" +
                "purchaseID=" + purchaseID +
                ", purchaseDate=" + purchaseDate +
                ", idUser=" + idUser +
                ", totalAmount=" + totalAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}

