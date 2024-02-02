package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.*;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmojiServiceImpl implements ICrud<Emoji>{

    @Override
    public ArrayList<Emoji> getAll() throws SQLException {
        ArrayList<Emoji> emojis = new ArrayList<Emoji>();
        String query1="select * from emoji where status= '"+ Status.VALID.toString()+"';";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        while (resultSet.next())
        {
            Emoji emoji=new Emoji();
            emoji.setIdEmoji(resultSet.getInt(1));
            emoji.setNomEmoji(resultSet.getString(2));
            emoji.setRank(resultSet.getInt(3));
            emoji.setPrix(resultSet.getInt(4));
            emoji.setImageUrl(resultSet.getString(5));
            emoji.setStatus(resultSet.getString(6));
            emojis.add(emoji);
        }
        return emojis ;
    }


    @Override
    public boolean add(Emoji emoji) {
        String selectQuery = "SELECT * FROM emoji WHERE nomemoji = ?";
        try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery)) {
            selectStatement.setString(1,emoji.getNomEmoji());
            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO emoji(nomemoji,rank,imageurl,status,prix) " +
                        "VALUES (?, ?, ?, ?,?)";

                try (PreparedStatement insertStatement = DbConnection.getCnx().prepareStatement(insertQuery)) {
                    insertStatement.setString(1,emoji.getNomEmoji());
                    insertStatement.setInt(2,emoji.getRank());
                    insertStatement.setString(3,emoji.getImageUrl());
                    insertStatement.setString(4, Status.VALID.toString());
                    insertStatement.setInt(5,emoji.getPrix());

                    int res = insertStatement.executeUpdate();
                    if(res > 0 )
                        System.out.println("successfully added");
                    return true;
                }
            } else {
                System.out.println("emoji already exists");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("error"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Emoji emoji) throws SQLException {

        Statement statement=DbConnection.getCnx().createStatement();
        String query2="update emoji set status= '"+Status.SUPPRIMER.toString()+"' where idemoji ="+emoji.getIdEmoji();
        statement.executeUpdate(query2);
        return  true;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String selectQuery = "SELECT * FROM emoji WHERE idemoji = ? and status='"+Status.VALID.toString()+"'";
        String updateQuery = "UPDATE emoji SET status = '"+Status.SUPPRIMER.toString()+"' WHERE idemoji = ?";
        try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery);
             PreparedStatement updateStatement = DbConnection.getCnx().prepareStatement(updateQuery)) {
            // parametre requete select
            selectStatement.setInt(1, id);
            try {
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {

                    // parametre requete update
                    updateStatement.setInt(1, id);
                    updateStatement.executeUpdate();
                    return true;

                } else {
                    System.out.println("emoji deja supprimer");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean update(Emoji emoji) throws SQLException {
        try (PreparedStatement statement = DbConnection.getCnx().prepareStatement(
                "UPDATE emoji SET nomemoji = ? ,  rank = ? , imageurl = ? , prix =? WHERE idemoji=?")) {
            statement.setString(1,emoji.getNomEmoji());
            statement.setInt(2, emoji.getRank());
            statement.setString(3, emoji.getImageUrl());
            statement.setInt(4, emoji.getPrix());
            statement.setInt(5, emoji.getIdEmoji());
            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                return true;
            }else {
                return false;
            }

        }
    }
}
