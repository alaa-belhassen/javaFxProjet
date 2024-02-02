package tn.esprit.javafxproject.models;

import java.util.Objects;

public class Don {
    private  int idDon;
    private double montant;
    private String commentaire;
    private Emoji emoji;
    private User donneur;
    private User Receveur;
    public Don() {

    }
    public Don(double montant, String commentaire, Emoji emoji, User donneur, User receveur) {
        this.montant = montant;
        this.commentaire = commentaire;
        this.emoji = emoji;
        this.donneur = donneur;
        Receveur = receveur;
    }

    public int getIdDon() {
        return idDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }


    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public void setEmoji(Emoji emoji) {
        this.emoji = emoji;
    }


    public User getDonneur() {
        return donneur;
    }

    public void setDonneur(User donneur) {
        this.donneur = donneur;
    }

    public User getReceveur() {
        return Receveur;
    }

    public void setReceveur(User receveur) {
        Receveur = receveur;
    }

    @Override
    public String toString() {
        return "Don{" +
                "idDon=" + idDon +
                ", montant=" + montant +
                ", commentaire='" + commentaire + '\'' +
                ", emoji=" + emoji +
                ", donneur=" + donneur +
                ", Receveur=" + Receveur +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Don don = (Don) o;
        return idDon == don.idDon && Double.compare(montant, don.montant) == 0 && Objects.equals(commentaire, don.commentaire) && Objects.equals(emoji, don.emoji) && Objects.equals(donneur, don.donneur) && Objects.equals(Receveur, don.Receveur);
    }


}
