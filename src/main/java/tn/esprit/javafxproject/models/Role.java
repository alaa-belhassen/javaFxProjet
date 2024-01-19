package tn.esprit.javafxproject.models;

public class Role {
    private int idRole;

    private String name;
    private String status;

    public Role() {

    }

    public Role(int idRole, String name, String status) {
        super();
        this.idRole = idRole;
        this.name = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
