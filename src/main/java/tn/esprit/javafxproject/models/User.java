package tn.esprit.javafxproject.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int idUser;
    private String adresse;
    private String email;
    private String nom;
    private String telephone;
    private String status;
    private Role role;
    private PageActu pageActu;
    private String password;

    public User() {
    }
    public User( String email, String nom,  String status, Role role,  String password) {


        this.email = email;
        this.nom = nom;

        this.status = status;
        this.role = role;

        this.password = hashPassword(password);
    }
    public User(int idUser, String adresse, String email, String nom, String telephone, String status, Role role, PageActu pageActu, String password) {
        this.idUser = idUser;
        this.adresse = adresse;
        this.email = email;
        this.nom = nom;
        this.telephone = telephone;
        this.status = status;
        this.role = role;
        this.pageActu = pageActu;
        this.password = hashPassword(password);
    }


    public int getIdUser() {
        return idUser;
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

    public String getTelephone() {
        return telephone;
    }

    public String getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public PageActu getPageActu() {
        return pageActu;
    }

    public String getPassword() {
        return password;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPageActu(PageActu pageActu) {
        this.pageActu = pageActu;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", status='" + status + '\'' +
                ", role=" + role +
                ", pageActu=" + pageActu +
                '}';
    }
}
