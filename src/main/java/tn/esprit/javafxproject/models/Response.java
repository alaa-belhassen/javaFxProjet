package tn.esprit.javafxproject.models;

public class Response {
    private int id_reponse;
    private int id_reclamation;
    private String description_reponse;
    private  String status_reponse;
    private String description_reclamation_response;
    private int id_user;


    public Response() {
    }

    @Override
    public String toString() {
        return "Response{" +
                "id_reponse=" + id_reponse +
                ", id_reclamation=" + id_reclamation +
                ", description_reponse='" + description_reponse + '\'' +
                ", status_reponse='" + status_reponse + '\'' +
                ", description_reclamation_response='" + description_reclamation_response + '\'' +
                ", id_user=" + id_user +
                '}';
    }

    public Response(int id_reponse, int id_reclamation, String description_reponse, String status_reponse, String description_reclamation_response, int id_user) {
        this.id_reponse = id_reponse;
        this.id_reclamation = id_reclamation;
        this.description_reponse = description_reponse;
        this.status_reponse = status_reponse;
        this.description_reclamation_response = description_reclamation_response;
        this.id_user = id_user;
    }

    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getDescription_reponse() {
        return description_reponse;
    }

    public void setDescription_reponse(String description_reponse) {
        this.description_reponse = description_reponse;
    }

    public String getStatus_reponse() {
        return status_reponse;
    }

    public void setStatus_reponse(String status_reponse) {
        this.status_reponse = status_reponse;
    }

    public String getDescription_reclamation_response() {
        return description_reclamation_response;
    }

    public void setDescription_reclamation_response(String description_reclamation_response) {
        this.description_reclamation_response = description_reclamation_response;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
