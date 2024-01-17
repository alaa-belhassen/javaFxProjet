package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.*;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.*;
import java.util.ArrayList;

public class ReservationServiceImpl implements ICrud<Reserver> {



    public ReservationServiceImpl()  {

    }




    @Override
    public ArrayList<Reserver> getAll() throws SQLException {
        String query1="select * from reserver where status='"+ Status.VALID.toString()+"'";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        while (resultSet.next())
        {
            Reserver reserver=new Reserver();
            reserver.setId_Res(resultSet.getInt(1));
            reserver.setCodeQR(resultSet.getString(2));
            reserver.setPrix_total(resultSet.getFloat(3));
            reserver.setDate(resultSet.getDate(4).toLocalDate());
            //  reserver.setEvenement((Evenement) resultSet.getObject(5));
            //reserver.setUser_id((User) resultSet.getObject(6));
            reserver.setStatus(resultSet.getString(7));
        }
        return null;
    }

    @Override
    public boolean add(Reserver reserver) throws SQLException {

        String selectQuery = "SELECT * FROM reserver WHERE codeQR = ?";
        try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery)) {
            selectStatement.setString(1, reserver.getCodeQR());
            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO reserver(id_Res,codeQR,prix_total,date_res,idevenement,user_id,status) " +
                        "VALUES (?,?,?,?,?,?,?)";

                try (PreparedStatement insertStatement = DbConnection.getCnx().prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, reserver.getId_Res());
                    insertStatement.setString(2, reserver.getCodeQR());
                    insertStatement.setFloat(3, reserver.getPrix_total());
                    insertStatement.setDate(4, Date.valueOf(reserver.getDate()));
                    insertStatement.setInt(5, reserver.getEvenement().getIdEvenement());
                    insertStatement.setInt(6, reserver.getUser().getIdUser());
                    insertStatement.setString(7, Status.VALID.toString());

                    insertStatement.executeUpdate();
                    System.out.println("successfully added");
                    return true;
                }
            } else {
                System.out.println("reservation already made");
                return false;
            }
        }




    }

    @Override
    public boolean delete(Reserver reserver) throws SQLException {
        Statement statement=DbConnection.getCnx().createStatement();
        String query2="update reserver set status= '"+Status.SUPPRIMER.toString()+"' where id_res ="+reserver.getId_Res()+";";
        statement.executeUpdate(query2);

        return  true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String selectQuery = "SELECT * FROM reserver WHERE id_res = ? and status='"+Status.VALID.toString()+"'";
        String updateQuery = "UPDATE reserver SET status = '"+Status.SUPPRIMER.toString()+"' WHERE id_res = ?";
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

                    //    evenements.get(evenements.indexOf(resultSet));

                    System.out.println("deleted successfully");
                    return true;

                } else {
                    System.out.println("la categorie est déja supprimé");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    @Override
    public boolean update(Reserver reserver) throws SQLException {
        try (PreparedStatement statement = DbConnection.getCnx().prepareStatement(
                "UPDATE reserver SET codeqr=?, prix_total=? WHERE id_res=?")) {

            statement.setString(1, reserver.getCodeQR());
            statement.setFloat(2, reserver.getPrix_total());
            statement.setInt(3, reserver.getId_Res());

            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {

                System.out.println("updated successfully");
                return true;
            }else {
                return false;
            }

        }
    }
}
