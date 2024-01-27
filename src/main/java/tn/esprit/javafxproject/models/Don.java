package tn.esprit.javafxproject.models;

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
}
