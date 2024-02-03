package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.services.ICrud;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                p.setStatus(res.getString(6));
                p.setImage(res.getString(7));
                Produits.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Produits;
    }

    @Override
    public boolean add(Produit produit) {
        String requete = "INSERT INTO produit ( productName, productDescription, price, quantityInStock, status , image) VALUES (?,?,?,?,?,?)";
        int er = 0;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete)) {

            preparedStatement.setString(1, produit.getProductName());
            preparedStatement.setString(2, produit.getProductDescription());
            preparedStatement.setDouble(3, produit.getPrice());
            preparedStatement.setInt(4, produit.getQuantityInStock());
            preparedStatement.setString(5, Status.VALID.toString());
            preparedStatement.setString(6, produit.getImage());

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

                + " SET status='SUPPRIMER' "
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

    public List<Produit> searchProduit(String searchTerm) {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM produit WHERE productName LIKE ? OR productDescription LIKE ?";

        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, "%" + searchTerm + "%");
            pst.setString(2, "%" + searchTerm + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setProductID(rs.getInt("productID"));
                produit.setProductName(rs.getString("productName"));
                produit.setProductDescription(rs.getString("productDescription"));
                produit.setPrice(rs.getDouble("price"));
                produit.setQuantityInStock(rs.getInt("quantityInStock"));
                produit.setStatus(rs.getString("status"));

                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }


    @Override
    public boolean delete(int id) {
        String req = "UPDATE produit	SET "
                + "status='SUPPRIMER' "
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
