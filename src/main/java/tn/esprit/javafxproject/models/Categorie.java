package tn.esprit.javafxproject.models;

public class Categorie  {

    private int idCategorie;
    private String nom;

    private String status;

    public Categorie() {
    }

    public Categorie(int idCategorie, String nom,String status) {
        this.idCategorie = idCategorie;
        this.nom = nom;
        this.status=status;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categorie n°" +
                idCategorie +
                ", nom='" + nom + '\'' +
                '\n';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}