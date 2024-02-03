package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.services.ServiceAchat;

import java.io.IOException;

public class SidebarController {



    @FXML
    public BorderPane borderPane;

    @FXML
    void goAcceuil(MouseEvent event) {

    }

    @FXML
    void goDeconect(MouseEvent event) {

    }

    @FXML
    void goDonation(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "ProduitsList.fxml"));
        Parent parent = (Parent)root.load();
        Object ProduitListController = root.getController();
        if (root.getController() instanceof ProduitsListController) {
            ((ProduitsListController) ProduitListController).sidebarController = this;
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    void goEvenement(MouseEvent event) {

    }

    @FXML
    void goReclamation(MouseEvent event) {

    }

    @FXML
    void goShop(MouseEvent event) throws IOException {
        ServiceAchat serviceAchat = new ServiceAchat();
        Achat achat = new Achat();
        achat.setIdUser(12);
        achat.setPaymentStatus("non");
        serviceAchat.add(achat);
        loadPage("Shop");
    }
    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();
        Object eventsController = root.getController();
        if (root.getController() instanceof ShopController) {
            ((ShopController) eventsController).sidebarController = this;

            borderPane.setCenter(parent);
        }

        System.out.println(root);
    }



}
