package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSidebarController  implements Initializable {

    @FXML
    private Hyperlink profil;
    @FXML
    public BorderPane borderPane;
    private UserServiceImpl userService = new UserServiceImpl();
    @FXML
    private ImageView image;
    @FXML
    private Button deconnecter;

    @FXML
    void goAcceuil(MouseEvent event) {

    }


    @FXML
    void profil (ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("Profil.fxml"));

        Parent parent = (Parent)root.load();

        Object profilController = root.getController();
        if(root.getController() instanceof ProfilController){
            ((ProfilController) profilController).adminsidebarController= this;
            borderPane.setCenter(parent);
        }

        System.out.println(root);
    }
    @FXML
    void goUsers(MouseEvent event) throws IOException {

        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "Users.fxml"));
        Parent parent = (Parent)root.load();
        Object usersController = root.getController();
        if (root.getController() instanceof UsersController) {
            ((UsersController)usersController).AdminsidebarController = this;
            this.borderPane.setCenter(parent);
        }

    }
    @FXML
    void goRoles(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "Roles.fxml"));
        Parent parent = (Parent)root.load();
        Object rolesController = root.getController();
        if (root.getController() instanceof RolesController) {
            ((RolesController)rolesController).AdminsidebarController = this;
            this.borderPane.setCenter(parent);
        }
    }
    @FXML
    void goDeconect(MouseEvent event) throws IOException {

        Stage stage = (Stage) deconnecter.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        User.UserConnecte=-1;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        User user = userService.getUser(User.UserConnecte);
        profil.setText(user.getNom());
        Image image = null;
        try {
            image = new Image(new FileInputStream(user.getImage()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.image.setImage(image);


    }
}