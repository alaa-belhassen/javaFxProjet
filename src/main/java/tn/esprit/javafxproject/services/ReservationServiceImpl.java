package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.*;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.*;
import java.util.ArrayList;

public class ReservationServiceImpl implements ICrud<Reserver> {



    public ReservationServiceImpl() throws SQLException {

    }




    @Override
    public ArrayList<Reserver> getAll() throws SQLException {

        ArrayList<Reserver> reservers=new ArrayList<>();
        String query1="select * from reserver where status='"+ Status.VALID.toString() +"' ;";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(query1);

        while (resultSet.next())
        {
            Reserver reserver=new Reserver();
            reserver.setId_Res(resultSet.getInt(1));
            reserver.setCodeQR(resultSet.getString(2));
            reserver.setPrix_total(resultSet.getFloat(3));
            reserver.setDate(resultSet.getDate(4).toLocalDate());


            String query3="select * from evenement where idevenement=?" ;
            PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(query3);
            selectStatement.setInt(1, resultSet.getInt("idevenement"));
            ResultSet resultSet3= selectStatement.executeQuery();


            Evenement evenement=new Evenement();


            if (resultSet3.next()) {
                evenement.setIdEvenement(resultSet3.getInt(1));
                evenement.setLieu(resultSet3.getString(2));
                evenement.setMax_places(resultSet3.getInt(3));
                evenement.setPrix(resultSet3.getFloat(4));
                evenement.setLibelle(resultSet3.getString(5));
                evenement.setDate_event(resultSet3.getDate(6).toLocalDate());
                evenement.setTime_event(resultSet3.getTime(7).toLocalTime());
                evenement.setDuration(resultSet3.getInt(8));
                evenement.setStatus(resultSet3.getString(9));
                evenement.setPhoto(resultSet3.getString(10));


                String query4 = "select * from categorie where idcategorie=" + resultSet3.getInt(11) + " ;";
                Statement statement4 = DbConnection.getCnx().createStatement();
                ResultSet resultSet4 = statement4.executeQuery(query4);

                Categorie categorie = new Categorie();

                if (resultSet4.next()) {
                    categorie.setIdCategorie(resultSet4.getInt(1));
                    categorie.setNom(resultSet4.getString(2));
                    categorie.setStatus(resultSet4.getString(3));
                }


                evenement.setId_categorie(categorie);
                evenement.setIdUser(resultSet3.getInt(12));
            }

            String query4="select * from utilisateur where idUser="+resultSet.getInt("idUser")+" ;";
            Statement statement4=DbConnection.getCnx().createStatement();
            ResultSet resultSet4= statement4.executeQuery(query4);

            User user=new User();

            if(resultSet4.next()){
                user.setIdUser(resultSet4.getInt(1));
                user.setNom(resultSet4.getString(2));
                user.setEmail(resultSet4.getString(3));
                user.setTelephone(resultSet4.getString(4));


                user.setAdresse(resultSet4.getString(5));

                user.setStatus(resultSet4.getString(6));
                user.setPassword(resultSet4.getString(7));
                user.setImage(resultSet4.getString(8));

                String query5="select * from utilisateur where idUser="+resultSet.getInt("idUser")+" ;";
                Statement statement5=DbConnection.getCnx().createStatement();
                ResultSet resultSet5= statement5.executeQuery(query5);

                Role role =new Role();

                if(resultSet5.next()) {
                    role.setIdRole(resultSet5.getInt(1));
                    role.setName(resultSet5.getString(2));
                    user.setRole(role);
                }
            }
            reserver.setEvenement(evenement);
            reserver.setUser(user);


            reserver.setStatus(resultSet.getString(7));
            reservers.add(reserver);
        }
        return reservers;
    }





    public ArrayList<Reserver> getMyList(int id) throws SQLException {

        ArrayList<Reserver> reservers=new ArrayList<>();
        String query1="select * from reserver where iduser='"+id+"'and status='"+ Status.VALID.toString() +"' ;";
        Statement statement= DbConnection.getCnx().createStatement();
        ResultSet resultSet= statement.executeQuery(query1);

        while (resultSet.next())
        {
            Reserver reserver=new Reserver();
            reserver.setId_Res(resultSet.getInt(1));
            reserver.setCodeQR(resultSet.getString(2));
            reserver.setPrix_total(resultSet.getFloat(3));
            reserver.setDate(resultSet.getDate(4).toLocalDate());


            String query3="select * from evenement where idevenement=?" ;
            PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(query3);
            selectStatement.setInt(1, resultSet.getInt("idevenement"));
            ResultSet resultSet3= selectStatement.executeQuery();


            Evenement evenement=new Evenement();


            if (resultSet3.next()) {
                evenement.setIdEvenement(resultSet3.getInt(1));
                evenement.setLieu(resultSet3.getString(2));
                evenement.setMax_places(resultSet3.getInt(3));
                evenement.setPrix(resultSet3.getFloat(4));
                evenement.setLibelle(resultSet3.getString(5));
                evenement.setDate_event(resultSet3.getDate(6).toLocalDate());
                evenement.setTime_event(resultSet3.getTime(7).toLocalTime());
                evenement.setDuration(resultSet3.getInt(8));
                evenement.setStatus(resultSet3.getString(9));
                evenement.setPhoto(resultSet3.getString(10));


                String query4 = "select * from categorie where idcategorie=" + resultSet3.getInt(11) + " ;";
                Statement statement4 = DbConnection.getCnx().createStatement();
                ResultSet resultSet4 = statement4.executeQuery(query4);

                Categorie categorie = new Categorie();

                if (resultSet4.next()) {
                    categorie.setIdCategorie(resultSet4.getInt(1));
                    categorie.setNom(resultSet4.getString(2));
                    categorie.setStatus(resultSet4.getString(3));
                }


                evenement.setId_categorie(categorie);
                evenement.setIdUser(resultSet3.getInt(12));
            }

            String query4="select * from utilisateur where idUser="+resultSet.getInt("idUser")+" ;";
            Statement statement4=DbConnection.getCnx().createStatement();
            ResultSet resultSet4= statement4.executeQuery(query4);

            User user=new User();

            if(resultSet4.next()){
                user.setIdUser(resultSet4.getInt(1));
                user.setNom(resultSet4.getString(2));
                user.setEmail(resultSet4.getString(3));
                user.setTelephone(resultSet4.getString(4));


                user.setAdresse(resultSet4.getString(5));

                user.setStatus(resultSet4.getString(6));
                user.setPassword(resultSet4.getString(7));
                user.setImage(resultSet4.getString(8));

                String query5="select * from utilisateur where idUser="+resultSet.getInt("idUser")+" ;";
                Statement statement5=DbConnection.getCnx().createStatement();
                ResultSet resultSet5= statement5.executeQuery(query5);

                Role role =new Role();

                if(resultSet5.next()) {
                    role.setIdRole(resultSet5.getInt(1));
                    role.setName(resultSet5.getString(2));
                    user.setRole(role);
                }
            }
            reserver.setEvenement(evenement);
            reserver.setUser(user);


            reserver.setStatus(resultSet.getString(7));
            reservers.add(reserver);
        }
        return reservers;
    }




    @Override
    public boolean insert(Response rep) throws SQLException {
        return false;
    }

    @Override
    public boolean add(Reserver reserver) throws SQLException {

        String selectQuery = "SELECT * FROM reserver WHERE iduser = ? and idevenement= ? and status='"+Status.VALID.toString()+"'";
        try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(selectQuery)) {
            selectStatement.setInt(1, reserver.getIdUser().getIdUser());
            selectStatement.setInt(2, reserver.getEvenement().getIdEvenement());

            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO reserver(codeQR,prix_total,date_res,idevenement,iduser,status) " +
                        "VALUES (?,?,?,?,?,?)";

                try (PreparedStatement insertStatement = DbConnection.getCnx().prepareStatement(insertQuery)) {

                    insertStatement.setString(1, reserver.getCodeQR());
                    insertStatement.setFloat(2, reserver.getPrix_total());
                    insertStatement.setDate(3, Date.valueOf(reserver.getDate()));
                    insertStatement.setInt(4, reserver.getEvenement().getIdEvenement());
                    insertStatement.setInt(5, reserver.getUser().getIdUser());
                    insertStatement.setString(6, Status.VALID.toString());

                    insertStatement.executeUpdate();

                    int qte= (int) (reserver.getPrix_total()/reserver.getEvenement().getPrix());

                  //  update evenement set max_places=max_places-qte where reserver.getEvenement

                    updateEvent(reserver.getEvenement().getIdEvenement(),qte);

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
        String query2="update reserver set status= '"+Status.SUPPRIMER+"' where id_res ="+reserver.getId_Res()+";";
        statement.executeUpdate(query2);

        return  true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String selectQuery = "SELECT * FROM reserver WHERE id_res = ? and status='"+Status.VALID+"'";
        String updateQuery = "UPDATE reserver SET status = '"+Status.SUPPRIMER+"' WHERE id_res = ?";
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



    public boolean updateEvent(int idevenement, int qte) throws SQLException {
        try (PreparedStatement statement = DbConnection.getCnx().prepareStatement(
                "UPDATE evenement SET max_places=max_places-? WHERE idevenement=?")) {

            statement.setInt(1, qte);
            statement.setInt(2, idevenement);


            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {

                System.out.println("updated successfully");
                return true;
            }else {
                return false;
            }

        }
    }

    //                    "UPDATE evenement SET max_places=? WHERE idevenement=?")) {







}
