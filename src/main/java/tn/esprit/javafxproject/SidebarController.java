package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import tn.esprit.javafxproject.models.User;

import java.io.IOException;

public class SidebarController {

public int id;
    public User user;
    @FXML
    private Hyperlink profil;
    @FXML
    private BorderPane borderPane;
    @FXML
    void goAcceuil(MouseEvent event) {

    }

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    void profil (ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("Profil.fxml"));

        Parent parent = (Parent)root.load();

       ProfilController profilController = root.getController();
      profilController.setUser(this.user);

        if(root.getController() instanceof ProfilController){
            ((ProfilController) profilController).SidebarController = this;
            borderPane.setCenter(parent);
        }
        //Passage de variabl

        System.out.println(this.id);
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

    public void setUser(User u) {
        this.user=u;
    }
    public void initialize() {
       System.out.println("bonjour c bon "+this.user);
    }

}
