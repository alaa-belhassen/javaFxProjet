package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.services.ICrud;
import tn.esprit.javafxproject.utils.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceProduit implements ICrud<Produit> {

    private Connection cnx ;

    public ServiceProduit() {
        cnx= DbConnection.getInstance().getCnx();
    }

    @Override
    public ArrayList<Produit> getAll() {
        ArrayList<Produit> Produits = new ArrayList<Produit>();
        String req= "Select * from produit";
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Produit p = new Produit();
                p.setProductID(res.getInt(1));
                p.setProductName(res.getString(2));
                p.setProductDescription(res.getString(3));
                p.setPrice(res.getDouble(4));
                p.setQuantityInStock(res.getInt(5));
                Produits.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Produits;
    }

    @Override
    public boolean add(Produit produit) {
        String requete = "INSERT INTO produit (productID, productName, productDescription, price, quantityInStock) VALUES (?,?,?,?,?)";
        int er = 0;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete)) {
            preparedStatement.setInt(1, produit.getProductID());
            preparedStatement.setString(2, produit.getProductName());
            preparedStatement.setString(3, produit.getProductDescription());
            preparedStatement.setDouble(4, produit.getPrice());
            preparedStatement.setInt(5, produit.getQuantityInStock());

            er = preparedStatement.executeUpdate();
            System.out.println("Ajout réussi");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return er > 0;
    }

    @Override
    public boolean delete(Produit p) {
        String req = "UPDATE produit  "

                + " SET status='supprimé' "
                + "WHERE productID='" + p.getProductID() + "';";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er= st.executeUpdate(req);
            System.out.println("Suppression affectuée");
        } catch (SQLException e) {

            e.printStackTrace();
        }


        return er == -1;
    }

    @Override
    public boolean delete(int id) {
        String req = "UPDATE produit	SET "
                + "status='supprimé' "
                + "WHERE idproduit ='" + id + "';";
        Statement st;
        int er = 0;
        try {
            st = cnx.createStatement();
            er= st.executeUpdate(req);
            System.out.println("Suppression affectuée");
        } catch (SQLException e) {

            e.printStackTrace();
        }


        return er == -1;
    }

    @Override
    public boolean update(Produit p) {
        String req = "UPDATE achat "
                + "SET dateachat=?, idclient=? "
                + "WHERE idproduit=?";

        int er = 0;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, p.getProductID());
            preparedStatement.setString(2, p.getProductName());
            preparedStatement.setString(3, p.getProductDescription());
            preparedStatement.setDouble(4, p.getPrice());
            preparedStatement.setDouble(5, p.getQuantityInStock());


            er = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return er != 0;
    }
}
