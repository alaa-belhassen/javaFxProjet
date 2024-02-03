package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.services.ServiceAchat;
import tn.esprit.javafxproject.services.ServiceProduit;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    @FXML
    private GridPane donationsContainer;
    private List<Produit> produits;

    SidebarController sidebarController;

    public int idAchat;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         idAchat = ajouterAchat();
        int column = 0;
        int row = 1;
        try{
            produits = new ArrayList<>(getProduits());
            System.out.println(produits);
            for(Produit produit : produits){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                VBox vbox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.shopController = this;
                cardController.nom.setText(produit.getProductName());
                cardController.prix.setText(String.valueOf(produit.getPrice()));
               // if(!produit.getImage().isEmpty()){
               //////  }//
                cardController.produit=produit;
                cardController.idachat = idAchat;
                cardController.setDataEmoji(produit);
                if(column==4){
                    column=0;
                    ++row;
                }
                donationsContainer.add(vbox,column++,row);
                GridPane.setMargin(vbox, new Insets(10));
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void goNext(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Booking.fxml"));
        Parent parent = fxmlLoader.load();
        BookingController bookingController = fxmlLoader.getController();
        System.out.println("yasser"+idAchat);
        bookingController.idAchat = idAchat;
        sidebarController.borderPane.setCenter(parent);
    }

    private List<Produit> getProduits() throws SQLException {
        List<Produit> produits ;
        ServiceProduit produitservice = new ServiceProduit();
        produits = produitservice.getAll();
        return produits ;
    }
    private  int ajouterAchat() {
        ServiceAchat serviceAchat = new ServiceAchat();
        return serviceAchat.getLastId();
    }





}

