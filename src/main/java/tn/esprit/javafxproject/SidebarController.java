package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {
    @FXML
    private BorderPane borderPane;

    @FXML
    void goAcceuil(MouseEvent event) {

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
    void goReclamation(MouseEvent event) throws IOException {

        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "ReclamationUser.fxml"));
        Parent parent = (Parent)root.load();
        Object usersController = root.getController();
        if (root.getController() instanceof ReclamationuserController) {
            ((ReclamationuserController)usersController).sidebarController = this;
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    void goShop(MouseEvent event) {

    }
}
