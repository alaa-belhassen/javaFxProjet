package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Categorie;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.*;
import java.util.ArrayList;

public class EvenementServiceImpl implements ICrud<Evenement> {


    public EvenementServiceImpl() throws SQLException {
    }


    @Override
    public ArrayList<Evenement> getAll() throws SQLException {
        ArrayList<Evenement> evenements = new ArrayList<Evenement>();
        String query1="select * from evenement where status='"+ Status.VALID.toString() +"' ;";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        while (resultSet.next()) {
            Evenement evenement=new Evenement();
            evenement.setIdEvenement(resultSet.getInt(1));
            evenement.setLieu(resultSet.getString(2));
            evenement.setMax_places(resultSet.getInt(3));
            evenement.setPrix(resultSet.getFloat(4));
            evenement.setLibelle(resultSet.getString(5));
            evenement.setDate_event(resultSet.getDate(6).toLocalDate());
            evenement.setTime_event(resultSet.getTime(7).toLocalTime());
            evenement.setDuration(resultSet.getInt(8));
            evenement.setStatus(resultSet.getString(9));

            String query3="select * from categorie where idcategorie=?" ;
            PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(query3);
            selectStatement.setInt(1, resultSet.getInt("idcategorie"));
            ResultSet resultSet3= selectStatement.executeQuery();


            Categorie categorie1=new Categorie();
            if (resultSet3.next()) {
                categorie1.setIdCategorie(resultSet3.getInt("idcategorie"));
                categorie1.setNom(resultSet3.getString("nom"));


            }
            evenement.setId_categorie(categorie1);

            evenement.setPhoto(resultSet.getString(11));

            evenement.setIdUser(resultSet.getInt(12));

            evenements.add(evenement);
        }

        return evenements;
    }

    @Override
    public boolean add(Evenement evenement) throws SQLException {
        String selectQuery = "SELECT * FROM evenement WHERE libelle = ?";
        try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery)) {
            selectStatement.setString(1, evenement.getLibelle());
            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO Evenement(libelle, duration, date_event, time_event, max_places, prix, lieu,status,photo,idcategorie,iduser) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

                try (PreparedStatement insertStatement = DbConnection.getCnx().prepareStatement(insertQuery)) {
                    insertStatement.setString(1, evenement.getLibelle());
                    insertStatement.setInt(2, evenement.getDuration());
                    insertStatement.setDate(3, Date.valueOf(evenement.getDate_event()));
                    insertStatement.setTime(4, Time.valueOf(evenement.getTime_event())); // Assuming time_event is of type TIMESTAMP
                    insertStatement.setInt(5, evenement.getMax_places());
                    insertStatement.setDouble(6, evenement.getPrix());
                    insertStatement.setString(7, evenement.getLieu());
                    insertStatement.setString(8, evenement.getStatus());
                    insertStatement.setString(9, evenement.getPhoto());
                    insertStatement.setInt(10, evenement.getId_categorie().getIdCategorie());
                    insertStatement.setInt(11, evenement.getIdUser());

                    insertStatement.executeUpdate();
                    System.out.println("successfully added");
                    return true;
                }
            } else {
                System.out.println("Event already exists");
                return false;
            }
        }
    }


    @Override
    public boolean delete(Evenement evenement) throws SQLException {
        Statement statement=DbConnection.getCnx().createStatement();
        String query2="update evenement set status= 'Supprimé' where idEvenement ="+evenement.getIdEvenement()+";";
        statement.executeUpdate(query2);
        return  true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String selectQuery = "SELECT * FROM evenement WHERE idEvenement = ? and status='valid'";
        String updateQuery = "UPDATE evenement SET status = 'Supprimé' WHERE idEvenement = ?";
        try (Connection connection = DbConnection.getCnx();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            // parametre requete select
            selectStatement.setInt(1, id);
            try {
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {

                    // parametre requete update
                    updateStatement.setInt(1, id);
                    updateStatement.executeUpdate();
                    System.out.println("deleted successfully");


                    //    evenements.get(evenements.indexOf(resultSet));


                    return true;

                } else {
                    System.out.println("l'evenement est déja supprimé");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean update(Evenement evenement) throws SQLException {

        try (PreparedStatement statement = DbConnection.getCnx().prepareStatement(
                "UPDATE evenement SET lieu=?, libelle=?, max_places=?, prix=?, date_event=?, time_event=?, duration=? WHERE idEvenement=?")) {
            statement.setString(1, evenement.getLieu());
            statement.setString(2, evenement.getLibelle());
            statement.setInt(3, evenement.getMax_places());
            statement.setFloat(4, evenement.getPrix());
            statement.setDate(5, Date.valueOf(evenement.getDate_event()));
            statement.setTime(6, Time.valueOf(evenement.getTime_event()));
            statement.setInt(7, evenement.getDuration());
            statement.setInt(8, evenement.getIdEvenement());

            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("updated successfully");
                return true;
            }else {
                return false;
            }

        }
    }}

