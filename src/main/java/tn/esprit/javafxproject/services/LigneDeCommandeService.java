package tn.esprit.javafxproject.services;

import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.models.LigneDeCommande;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.sql.*;
import java.util.ArrayList;

public class LigneDeCommandeService implements ICrud<LigneDeCommande> {

    private Connection cnx;
    public LigneDeCommandeService() {
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public ArrayList<LigneDeCommande> getAll() throws SQLException {
        ArrayList<LigneDeCommande> lignesDeCommande = new ArrayList<>();

        String req = "SELECT * FROM lignedecommande  where status='"+ Status.VALID.toString()+"'" ;
        String achat = "SELECT * FROM achat where purchaseid = ?";
        String produit = "SELECT * FROM produit where productid= ?";
        // statement

        try (Statement statement = DbConnection.getCnx().createStatement();
             ResultSet getAllRs = statement.executeQuery(req) ) {
            while (getAllRs.next()) {
                // object init
                Achat achat2 = new Achat();
                LigneDeCommande ligne = new LigneDeCommande();
                Produit produit1 = new Produit();
                ligne.setPurchaseID(getAllRs.getInt("purchaseID"));
                ligne.setProductID(getAllRs.getInt("productID"));
                ligne.setPrice(getAllRs.getDouble("price"));
                ligne.setQuantityOrdred(getAllRs.getInt("quantityOrdered"));
                ligne.setStatus(getAllRs.getString("status"));
                lignesDeCommande.add(ligne);
                // emoji
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(achat)) {
                    //params

                    selectStatement.setInt(1,getAllRs.getInt("purchaseID"));
                    //execute query
                    ResultSet getAchatRs = selectStatement.executeQuery();
                    if (getAchatRs.next()) {
                        achat2.setPurchaseID(getAchatRs.getInt(1));
                        achat2.setPurchaseDate(getAchatRs.getDate(2).toLocalDate());
                        achat2.setIdUser(getAchatRs.getInt(3));
                        achat2.setTotalAmount(getAchatRs.getInt(4));
                        achat2.setPaymentStatus(getAchatRs.getString(5));
                        achat2.setStatus(getAchatRs.getString(5));
                    }
                }

                // produit
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(produit)) {
                    selectStatement.setInt(1,getAllRs.getInt("productid"));
                    ResultSet getProduit = selectStatement.executeQuery();
                    while (getProduit.next()) {
                        produit1.setProductID(getProduit.getInt(1));
                        produit1.setProductDescription(getProduit.getString(3));
                        produit1.setStatus(getProduit.getString(6));
                        produit1.setPrice(getProduit.getDouble(4));
                        produit1.setProductName(getProduit.getString(2));
                        produit1.setQuantityInStock(getProduit.getInt(5));
                        }
                }


                // merge object
                ligne.setAchat(achat2);
                ligne.setProduit(produit1);

                // add to list
                lignesDeCommande.add(ligne);
            }
        }
        System.out.println("yasser1"+lignesDeCommande);
        return lignesDeCommande;
    }




    public ArrayList<LigneDeCommande> getMyList(int id) throws SQLException {
        ArrayList<LigneDeCommande> lignesDeCommande = new ArrayList<>();
        System.out.println(id);
        String req = "SELECT * FROM lignedecommande  where purchaseid="+id+" and  status='"+ Status.VALID.toString()+"'" ;
        String achat = "SELECT * FROM achat where purchaseid = ?";
        String produit = "SELECT * FROM produit where productid= ?";
        // statement

        try (Statement statement = DbConnection.getCnx().createStatement();
             ResultSet getAllRs = statement.executeQuery(req) ) {
            while (getAllRs.next()) {
                // object init
                Achat achat2 = new Achat();
                LigneDeCommande ligne = new LigneDeCommande();
                Produit produit1 = new Produit();
                ligne.setPurchaseID(getAllRs.getInt("purchaseID"));
                ligne.setProductID(getAllRs.getInt("productID"));
                ligne.setPrice(getAllRs.getDouble("price"));
                ligne.setQuantityOrdred(getAllRs.getInt("quantityOrdered"));
                ligne.setStatus(getAllRs.getString("status"));
                // emoji
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(achat)) {
                    //params

                    selectStatement.setInt(1,getAllRs.getInt("purchaseID"));
                    //execute query
                    ResultSet getAchatRs = selectStatement.executeQuery();
                    if (getAchatRs.next()) {
                        achat2.setPurchaseID(getAchatRs.getInt(1));
                        achat2.setPurchaseDate(getAchatRs.getDate(2).toLocalDate());
                        achat2.setIdUser(getAchatRs.getInt(3));
                        achat2.setTotalAmount(getAchatRs.getInt(4));
                        achat2.setPaymentStatus(getAchatRs.getString(5));
                        achat2.setStatus(getAchatRs.getString(6));
                    }
                }

                // produit
                try (PreparedStatement selectStatement = DbConnection.getCnx().prepareStatement(produit)) {
                    selectStatement.setInt(1,getAllRs.getInt("productid"));
                    ResultSet getProduit = selectStatement.executeQuery();
                    while (getProduit.next()) {
                        produit1.setProductID(getProduit.getInt(1));
                        produit1.setProductDescription(getProduit.getString(3));
                        produit1.setStatus(getProduit.getString(6));
                        produit1.setPrice(getProduit.getDouble(4));
                        produit1.setProductName(getProduit.getString(2));
                        produit1.setQuantityInStock(getProduit.getInt(5));
                    }
                }


                // merge object
                ligne.setAchat(achat2);
                ligne.setProduit(produit1);

                // add to list
                lignesDeCommande.add(ligne);
            }
        }
        System.out.println("yasser2"+lignesDeCommande);
        return lignesDeCommande;
    }
    @Override
    public boolean add(LigneDeCommande ligneDeCommande) {
        String requete = "INSERT INTO lignedecommande (purchaseID, productID, price, quantityOrdered, status) VALUES (?,?,?,?,?)";
        int er = 0;
        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete)) {
            preparedStatement.setInt(1, ligneDeCommande.getPurchaseID());
            preparedStatement.setInt(2, ligneDeCommande.getProductID());
            preparedStatement.setDouble(3, ligneDeCommande.getPrice());
            preparedStatement.setInt(4, ligneDeCommande.getQuantityOrdred());
            preparedStatement.setString(5,  String.valueOf(Status.VALID));

            er = preparedStatement.executeUpdate();
            System.out.println("Ajout réussi");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return er > 0;
    }

    @Override
    public boolean delete(LigneDeCommande ligneDeCommande) {
        String req = "UPDATE lignedecommande SET status='SUPPRIMER' WHERE purchaseID=? AND productID=?";
        int er = 0;
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, ligneDeCommande.getPurchaseID());
            preparedStatement.setInt(2, ligneDeCommande.getProductID());
            er = preparedStatement.executeUpdate();
            System.out.println("Mise à jour effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return er > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }


    public boolean delete(int purchaseID, int productID) {
        String req = "UPDATE lignedecommande SET status='SUPPRIMER' WHERE purchaseID=? AND productID=?";
        int er = 0;
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, purchaseID);
            preparedStatement.setInt(2, productID);
            er = preparedStatement.executeUpdate();
            System.out.println("Mise à jour effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return er > 0;
    }

    @Override
    public boolean update(LigneDeCommande ligneDeCommande) {
        String req = "UPDATE lignedecommande SET price=?, quantityOrdered=?, status=? WHERE purchaseID=? AND productID=?";
        int er = 0;
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setDouble(1, ligneDeCommande.getPrice());
            preparedStatement.setInt(2, ligneDeCommande.getQuantityOrdred());
            preparedStatement.setString(3, ligneDeCommande.getStatus());
            preparedStatement.setInt(4, ligneDeCommande.getPurchaseID());
            preparedStatement.setInt(5, ligneDeCommande.getProductID());
            er = preparedStatement.executeUpdate();
            System.out.println("Mise à jour effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return er > 0;
    }
}
