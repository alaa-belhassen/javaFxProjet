package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Reclamation;
import tn.esprit.javafxproject.models.Response;
import tn.esprit.javafxproject.utils.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReclamationService implements ICrud<Reclamation>{
    private Connection cnx;

    public ReclamationService() {
        DbConnection.getInstance();
        cnx = DbConnection.getCnx();
    }

    @Override
    public ArrayList<Reclamation> getAll() throws SQLException {
        ArrayList<Reclamation> reclamations = new ArrayList<>();

        // Use JOIN to fetch the user's name associated with each reclamation
        String rec = "SELECT r.*, u.nom AS nom_user FROM Reclamation r JOIN utilisateur u ON r.iduser = u.IdUser";



        try (Statement st = cnx.createStatement();
             ResultSet resultSet = st.executeQuery(rec)) {

            while (resultSet.next()) {
                Reclamation v = new Reclamation();
                v.setId_reclamation(resultSet.getInt("id_reclamation"));
                v.setDescription_reclamation(resultSet.getString("Description_reclamation"));
                v.setStatus_reclamation(resultSet.getString("Status_reclamation"));
                v.setId_user(resultSet.getInt("iduser"));
                v.setNom_user(resultSet.getString("nom_user"));

                reclamations.add(v);
            }
        }

        return reclamations;
    }




    @Override
    public boolean add(Reclamation reclamation) throws SQLException {
        String req = "INSERT INTO reclamation (description_reclamation, status_reclamation, iduser) " +
                "VALUES ('" + reclamation.getDescription_reclamation() + "', '" + "En Attente" + "', '" + reclamation.getId_user() + "')";

        try (Statement st = cnx.createStatement()) {
            // Execute the SQL statement and get the number of affected rows
            int rowsAffected = st.executeUpdate(req);

            // Check if any rows were affected (indicating success)
            return rowsAffected > 0;
        }
    }


    @Override
    public boolean delete(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation set status_reclamation+='inactive' WHERE i_reclamation = +reclamation.get()";
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "update reclamation set status_reclamation= '"+"inactive"+"' where id_reclamation ="+id+";";
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET status_reclamation = '"+"Résolu"+ "' WHERE id_reclamation = "+ reclamation.getId_reclamation();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }









}
