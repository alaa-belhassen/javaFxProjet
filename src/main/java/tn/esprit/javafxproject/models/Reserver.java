package tn.esprit.javafxproject.models;

import java.time.LocalDate;
import java.util.Objects;

public class Reserver {

    private int id_Res;
    private String codeQR;
    private float prix_total;

    private LocalDate date_res;
    private Evenement Evenement;
    private User idUser;


    private String status;

    public Reserver() {
    }

    public Reserver(String codeQR, float prix_total, LocalDate date_res,Evenement evenement, User idUser,String status) {

        this.codeQR = codeQR;
        this.prix_total = prix_total;
        this.date_res = date_res;
        this.Evenement = evenement;
        this.idUser = idUser;
        this.status = "valid";
    }

    public LocalDate getDate_res() {
        return date_res;
    }

    public void setDate_res(LocalDate date_res) {
        this.date_res = date_res;
    }

    public User getUser_id() {
        return idUser;
    }

    public void setUser_id(User user_id) {
        this.idUser = user_id;
    }



    public LocalDate getDate() {
        return date_res;
    }

    public void setDate(LocalDate date) {
        this.date_res = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_Res() {
        return id_Res;
    }

    public void setId_Res(int id_Res) {
        this.id_Res = id_Res;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public float getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(float prix_total) {
        this.prix_total = prix_total;
    }

    public Evenement getEvenement() {
        return Evenement;
    }

    public void setEvenement(Evenement evenement) {
        Evenement = evenement;
    }

    public User getUser() {
        return idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserver reserver)) return false;
        return getId_Res() == reserver.getId_Res() && Float.compare(reserver.getPrix_total(), getPrix_total()) == 0 && Objects.equals(getCodeQR(), reserver.getCodeQR()) && Objects.equals(date_res, reserver.date_res) && Objects.equals(getEvenement(), reserver.getEvenement()) && Objects.equals(idUser, reserver.idUser) && Objects.equals(getStatus(), reserver.getStatus());
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Reserver{" +
                "id_Res=" + id_Res +
                ", codeQR='" + codeQR + '\'' +
                ", prix_total=" + prix_total +
                ", date_res=" + date_res +
                ", Evenement=" + Evenement +
                ", user_id=" + idUser +
                ", status='" + status + '\'' +
                '}';
    }

    public void setUser(User user) {
        this.idUser = user;
    }
}
