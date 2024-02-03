package tn.esprit.javafxproject.models;

public class Reclamation {
    private int id_reclamation;
    private String Description_reclamation;
    private String Status_reclamation;
    private int id_user;
    private String nom_user;

    public Reclamation() {
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getDescription_reclamation() {
        return Description_reclamation;
    }

    public void setDescription_reclamation(String description_reclamation) {
        Description_reclamation = description_reclamation;
    }

    public String getStatus_reclamation() {
        return Status_reclamation;
    }

    public void setStatus_reclamation(String status_reclamation) {
        Status_reclamation = status_reclamation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public Reclamation(int id_reclamation, String description_reclamation, String status_reclamation, int id_user, String nom_user) {
        this.id_reclamation = id_reclamation;
        Description_reclamation = description_reclamation;
        Status_reclamation = status_reclamation;
        this.id_user = id_user;
        this.nom_user = nom_user;
    }


    @Override
    public String toString() {
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", Description_reclamation='" + Description_reclamation + '\'' +
                ", Status_reclamation='" + Status_reclamation + '\'' +
                ", id_user=" + id_user +
                ", nom_user='" + nom_user + '\'' +
                '}';
    }
}
