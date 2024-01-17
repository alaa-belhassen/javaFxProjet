package tn.esprit.javafxproject.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Evenement {

    private int idEvenement;
    private String lieu;
    private int max_places;
    private float prix;
    private String libelle;
    private LocalDate date_event;
    private LocalTime time_event;
    private int duration;
    private String  status;



    //add fields
    private Reserver reserver;


    public Evenement() {
    }

    public Evenement(int idEvenement, String lieu, int max_places, float prix, String libelle, LocalDate date_event, LocalTime time_event, int duration,String status) {
        this.idEvenement = idEvenement;
        this.lieu = lieu;
        this.max_places = max_places;
        this.prix = prix;
        this.libelle = libelle;
        this.date_event = date_event;
        this.time_event = time_event;
        this.duration = duration;
        //this.reserver = reserver;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getMax_places() {
        return max_places;
    }

    public void setMax_places(int max_places) {
        this.max_places = max_places;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDate_event() {
        return date_event;
    }

    public void setDate_event(LocalDate date_event) {
        this.date_event = date_event;
    }

    public LocalTime getTime_event() {
        return time_event;
    }

    public void setTime_event(LocalTime time_event) {
        this.time_event = time_event;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Reserver getReserver() {
        return reserver;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "idEvenement=" + idEvenement +
                ", lieu='" + lieu + '\'' +
                ", max_places=" + max_places +
                ", prix=" + prix +
                ", libelle='" + libelle + '\'' +
                ", date_event=" + date_event +
                ", time_event=" + time_event +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                ", reserver=" + reserver +
                '}';
    }

    public void setReserver(Reserver reserver) {
        this.reserver = reserver;
    }
}