package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.javafxproject.SidebarController;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfilController {



    private UserServiceImpl userService = new UserServiceImpl();

    public User user;
    private String password;
    public AdminSidebarController adminsidebarController;
    public SidebarController sidebarController;

    public ProfilController() {

    }
    @FXML
    private ImageView card;
    @FXML
    private TextField nom ;
    @FXML
    private TextField email ;
    @FXML
    private TextField telephone ;
    @FXML
    private TextField adresse ;

    @FXML
    private Button ChangePassword;

    @FXML
    void ModifierProfil(ActionEvent event) throws IOException {
        User u = userService.getUser(User.UserConnecte);
        boolean passwordUpdated = false;

        u.setNom(nom.getText());
        u.setEmail(email.getText());
        u.setTelephone(telephone.getText());
        u.setAdresse(adresse.getText());

        userService.update(u);
        Alert al= new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Alert");al.setContentText("Profil modifié ");al.show();

    }


    @FXML
    private Button modifierProfil;

    @FXML
    void ChangePassword(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "ChangePassword.fxml"));
        Parent parent = (Parent)root.load();
        Object ChangePasswordController = root.getController();
        if (root.getController() instanceof ChangePasswordController) {
            ((ChangePasswordController)ChangePasswordController).profilController = this;

            if (User.Role_User_Connecte==0)
            { this.adminsidebarController.borderPane.setCenter(parent);}
            else
            { this.sidebarController.borderPane.setCenter(parent);}

        }

    }
    public void initialize() throws FileNotFoundException {

       User u = userService.getUser(User.UserConnecte);
        Image image =  new Image(new FileInputStream(u.getImage()));
        this.card.setImage(image);
        nom.setText(u.getNom());
        email.setText(u.getEmail());
        telephone.setText(u.getTelephone());
        adresse.setText(u.getAdresse());
        password=u.getPassword();

    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("User f page profil "+this.user);
    }

}
