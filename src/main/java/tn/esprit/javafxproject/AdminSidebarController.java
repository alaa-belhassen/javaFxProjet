package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminSidebarController {

    @FXML
    private Hyperlink profil;
    @FXML
    private BorderPane borderPane;
    @FXML
    void goAcceuil(MouseEvent event) {

    }


    @FXML
    void profil (ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("Profil.fxml"));

        Parent parent = (Parent)root.load();

        Object profilController = root.getController();
        if(root.getController() instanceof ProfilController){
            ((ProfilController) profilController).AdminSidebarController = this;
            borderPane.setCenter(parent);
        }

        System.out.println(root);
    }
    @FXML
    void goDeconect(MouseEvent event) {

    }

    @FXML
    void goDonation(MouseEvent event) {

    }

    @FXML
    void goEvenement(MouseEvent event) {

    }

    @FXML
    void goReclamation(MouseEvent event) {

    }

    @FXML
    void goShop(MouseEvent event) {

    }
}
