package tn.esprit.javafxproject.services;


import tn.esprit.javafxproject.models.Categorie;
import tn.esprit.javafxproject.models.Response;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.*;
import java.util.ArrayList;

public class CategorieServiceImpl implements ICrud<Categorie> {



    public CategorieServiceImpl() {
    }


    @Override
    public ArrayList<Categorie> getAll() throws SQLException {

        String query1="select * from categorie  ;";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        while (resultSet.next())
        {
            Categorie categorie=new Categorie();
            categorie.setIdCategorie(resultSet.getInt(1));
            categorie.setNom(resultSet.getString(2));

        }


        return null ;
    }

    @Override
    public boolean insert(Response rep) throws SQLException {
        return false;
    }

    @Override
    public boolean add(Categorie categorie) throws SQLException {

        String selectQuery = "SELECT * FROM categorie WHERE nom = ?";
        try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery)) {
            selectStatement.setString(1, categorie.getNom());
            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO categorie(nom,status) " +
                        "VALUES (?,?)";

                try (PreparedStatement insertStatement = DbConnection.getCnx().prepareStatement(insertQuery)) {
                    insertStatement.setString(1, categorie.getNom());
                    insertStatement.setString(2, Status.VALID.toString());

                    insertStatement.executeUpdate();
                    System.out.println("successfully added");
                    return true;
                }
            } else {
                System.out.println("category already exists");
                return false;
            }
        }
    }


    @Override
    public boolean delete(Categorie categorie) throws SQLException {
        Statement statement=DbConnection.getCnx().createStatement();
        String query2="update categorie set status= '"+Status.SUPPRIMER.toString()+"' where idcategorie ="+categorie.getIdCategorie()+";";
        statement.executeUpdate(query2);
        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String selectQuery = "SELECT * FROM categorie WHERE idcategorie = ? and status='"+Status.VALID.toString()+"'";
        String updateQuery = "UPDATE categorie SET status = '"+Status.SUPPRIMER.toString()+"' WHERE idcategorie = ?";
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
                    System.out.println("category already deleted");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public boolean update(Categorie categorie) throws SQLException {
        try {

            PreparedStatement statement = DbConnection.getCnx().prepareStatement(
                    "UPDATE categorie SET nom=? WHERE idcategorie=?");
            statement.setString(1, categorie.getNom());
            statement.setInt(2, categorie.getIdCategorie());


            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("updated successfully");
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }





}
