package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Automatically load goAcceuil when the sidebar is initialized
            goAcceuil(null);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    @FXML
    void goAcceuil(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("feed.fxml"));
        Parent parent = (Parent) root.load();
        Object feedController = root.getController();
        if (root.getController() instanceof FeedController) {
            ((FeedController) feedController).sideBarController = this;
            this.borderPane.setCenter(parent);
        }
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
