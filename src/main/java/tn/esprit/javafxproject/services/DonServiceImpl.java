package tn.esprit.javafxproject.services;



import tn.esprit.javafxproject.models.*;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DonServiceImpl implements ICrud<Don> {
    @Override
    public ArrayList<Don> getAll() throws SQLException {
        ArrayList<Don> dons = new ArrayList<>();

        // sql script
        String getAllDons = "select * from don where status='"+ Status.VALID.toString()+"'";
        String getEmoji = "select * from emoji where idemoji = ? ";
        String getUser = "select * from utilisateur where iduser = ? ";

        // statement

        try (Statement statement = DbConnection.getCnx().createStatement();
             ResultSet getAllRs = statement.executeQuery(getAllDons) ) {
            while (getAllRs.next()) {
                // object init
                Don don = new Don();
                Emoji emoji = new Emoji();
                User userDonneur = new User();
                User userReceveur = new User();

                // dons
                don.setIdDon(getAllRs.getInt(1));
                don.setMontant(getAllRs.getInt(2));
                don.setCommentaire(getAllRs.getString(3));

                // emoji
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(getEmoji)) {
                    //params

                    selectStatement.setInt(1,getAllRs.getInt(4));
                    //execute query
                    ResultSet getEmojiRs = selectStatement.executeQuery();
                    while (getEmojiRs.next()) {
                        emoji.setIdEmoji(getEmojiRs.getInt(1));
                        emoji.setNomEmoji(getEmojiRs.getString(2));
                        emoji.setRank(getEmojiRs.getInt(3));
                        emoji.setPrix(getEmojiRs.getInt(4));
                        emoji.setImageUrl(getEmojiRs.getString(5));
                    }
                }

                // donneur
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(getUser)) {
                    selectStatement.setInt(1,getAllRs.getInt(5));
                    ResultSet getUserDonneurRs = selectStatement.executeQuery();
                    while (getUserDonneurRs.next()) {
                        userDonneur.setIdUser(getUserDonneurRs.getInt(1));
                        userDonneur.setNom(getUserDonneurRs.getString(2));
                        userDonneur.setEmail(getUserDonneurRs.getString(3));
                    }
                }

                // receveur
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(getUser)) {
                    selectStatement.setInt(1,getAllRs.getInt(6));
                    ResultSet getUserReceveurRs = selectStatement.executeQuery();
                    while (getUserReceveurRs.next()) {
                        userReceveur.setIdUser(getUserReceveurRs.getInt(1));
                        userReceveur.setNom(getUserReceveurRs.getString(2));
                        userReceveur.setEmail(getUserReceveurRs.getString(3));
                    }
                }

                // merge object
                don.setEmoji(emoji);
                don.setDonneur(userDonneur);
                don.setReceveur(userReceveur);

                // add to list
                dons.add(don);
            }
        }

        return dons;
    }
    @Override
    public boolean add(Don don) {
        String selectQuery = "SELECT * FROM don WHERE iddon = ?";
        String getemoji = "SELECT idemoji FROM emoji WHERE nomemoji = ?";
        String getUser = "SELECT idUser FROM utilisateur WHERE nom = ?";
        int idemoji = -1;
        int idUserReceveur = -1;
        try ( PreparedStatement selectStatementEmoji = DbConnection.getCnx().prepareStatement(getemoji);
             PreparedStatement selectStatementUser = DbConnection.getCnx().prepareStatement(getUser) ) {
            selectStatementEmoji.setString(1,don.getEmoji().getNomEmoji());
            ResultSet emojiRs = selectStatementEmoji.executeQuery();
            if (emojiRs.next()) {
                idemoji= emojiRs.getInt(1);
            }
            selectStatementUser.setString(1,don.getReceveur().getNom());
            ResultSet userRS = selectStatementUser.executeQuery();
            if (userRS.next()) {
                idUserReceveur= userRS.getInt(1);
                System.out.println(idUserReceveur);
            }
            try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery)) {
                selectStatement.setInt(1,don.getIdDon());
                ResultSet resultSet = selectStatement.executeQuery();
                if (!resultSet.next()) {
                    String insertQuery = "INSERT INTO don(montant,commentaire,emoji,idUserDonneur,idUserReceveur,status) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement insertStatement = DbConnection.getCnx().prepareStatement(insertQuery)) {
                        insertStatement.setDouble(1,don.getMontant());
                        insertStatement.setString(2,don.getCommentaire());
                        insertStatement.setInt(3,idemoji);
                        insertStatement.setInt(4, 1);
                        insertStatement.setInt(5, idUserReceveur);
                        insertStatement.setString(6, Status.VALID.toString());

                        int res = insertStatement.executeUpdate();
                        if(res > 0 )
                            System.out.println("successfully added");
                        return true;
                    }
                } else {
                    System.out.println("don already exists");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Don don) throws SQLException {
        Statement statement=DbConnection.getCnx().createStatement();
        String query2="update don set status= '"+Status.SUPPRIMER.toString()+"' where iddon ="+don.getIdDon();
        statement.executeUpdate(query2);
        return  true;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String selectQuery = "SELECT * FROM don WHERE iddon = ? and status='"+ Status.VALID.toString()+"'";
        String updateQuery = "UPDATE don SET status = '"+Status.SUPPRIMER.toString()+"' WHERE iddon = ?";
        try (
                PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery);
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
                    System.out.println("don deja supprimer");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean update(Don don) throws SQLException {
        try (PreparedStatement statement = DbConnection.getCnx().prepareStatement(
                "UPDATE don SET commentaire = ? ,  emoji = ? WHERE iddon=?")) {
            statement.setString(1,don.getCommentaire());
            statement.setInt(2, don.getEmoji().getIdEmoji());
            statement.setInt(3, don.getIdDon());
            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                return true;
            }else {
                return false;
            }

        }
    }

    public int calculateLvl(int id) throws SQLException {
        String sumQuery = "select sum(rank) from don where iduserdonneur = "+id+"  and status ='"+Status.VALID.toString()+"'";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(sumQuery);
        resultSet.next();
        int sum = resultSet.getInt(1);
        System.out.println(sum);
        return sum;
    }

    public boolean chearchUserByName(String name) throws SQLException {
        String cherchUser = "select nom from utilisateur where nom=?";
        PreparedStatement preparedStatement = DbConnection.getCnx().prepareStatement(cherchUser);
        preparedStatement.setString(1,name);
        ResultSet cherchUserRS = preparedStatement.executeQuery();
        return cherchUserRS.next();
    }

}
