package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.services.ICrud;
import tn.esprit.javafxproject.utils.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceAchat implements ICrud<Achat> {

    private Connection cnx;

    public ServiceAchat() {
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public ArrayList<Achat> getAll() {
        ArrayList<Achat> Achats = new ArrayList<Achat>();
        String req = "SELECT * FROM achat";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Achat a = new Achat();
                a.setPurchaseID(res.getInt(1));
                a.setPurchaseDate(res.getDate(2).toLocalDate());
                a.setIdUser(res.getInt(3));
                a.setTotalAmount(res.getDouble(4));
                a.setPaymentStatus(res.getString(5));
                Achats.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Achats;
    }

    @Override
    public boolean add(Achat a) {
        String requete = "INSERT INTO achat (idachat, dateachat, idclient , status) VALUES (?, ?, ?,?)";
        int er = 0;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete)) {
            preparedStatement.setInt(1, a.getPurchaseID());
            preparedStatement.setDate(2, Date.valueOf(a.getPurchaseDate()));
            preparedStatement.setInt(3, a.getIdUser());
            preparedStatement.setDouble(4, a.getTotalAmount());
            preparedStatement.setString(5, a.getPaymentStatus());

            er = preparedStatement.executeUpdate();
            System.out.println("Ajout réussi");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return er > 0; // Vérifiez si des lignes ont été affectées (er > 0) pour déterminer le succès.
    }

    @Override
    public boolean delete(Achat a) {
        String req = "UPDATE achat "

                + " SET status='supprimé' "
                + "WHERE idachat='" + a.getPurchaseID() + "';";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er = st.executeUpdate(req);
            System.out.println("Suppression affectuée");
        } catch (SQLException e) {

            e.printStackTrace();
        }


        return er == -1;
    }

    @Override
    public boolean delete(int id) {
        String req = "UPDATE achat	SET "
                + "status='supprimé' "
                + "WHERE idachat ='" + id + "';";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er = st.executeUpdate(req);
            System.out.println("Suppression affectuée");
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return er == -1;
    }

    @Override
    public boolean update(Achat a) {
        String req = "UPDATE achat "
                + "SET purchasedate=?, idUser=? "
                + "WHERE purchaseID=?";


        int er = 0;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, a.getPurchaseID());
            preparedStatement.setDate(2, java.sql.Date.valueOf(a.getPurchaseDate()));
            preparedStatement.setInt(3, a.getIdUser());
            preparedStatement.setDouble(4, a.getTotalAmount());
            preparedStatement.setString(5, a.getPaymentStatus());

            er = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return er != 0;
    };
}
