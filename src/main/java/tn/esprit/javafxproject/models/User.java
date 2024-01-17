package tn.esprit.javafxproject.models;

public class User {
    private int IdUser;
    private String adresse;
    private String email;
    private String nom;

    // zidou les champs lokhrin
    private Role role;
    private PageActu pageActu;

    public User() {
    }

    public User(int idUser) {
        IdUser = idUser;
    }

    public int getIdUser() {
        return IdUser;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public Role getRole() {
        return role;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "IdUser=" + IdUser +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", role=" + role +
                ", pageActu=" + pageActu +
                '}';
    }
}
