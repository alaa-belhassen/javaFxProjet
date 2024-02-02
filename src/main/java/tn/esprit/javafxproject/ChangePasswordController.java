package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;

import java.io.IOException;

public class ChangePasswordController {

    public ProfilController profilController;
    private UserServiceImpl userService = new UserServiceImpl();

    @FXML
    private PasswordField NewPassword;

    @FXML
    private PasswordField OldPassword;

    @FXML
    private Button resetPassword;

    @FXML
    private Button retour;


    public void retour(ActionEvent event) throws IOException {

        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "Profil.fxml"));
        Parent parent = (Parent)root.load();
        Object ProfilController = root.getController();
        if (root.getController() instanceof ProfilController) {
            ((ProfilController)ProfilController).adminsidebarController = this.profilController.adminsidebarController;
            if(User.Role_User_Connecte==0)
            { this.profilController.adminsidebarController.borderPane.setCenter(parent);}
            else
            {
                this.profilController.sidebarController.borderPane.setCenter(parent);
            }
        }


    }

    public void resetPassword(ActionEvent event) {
     User u =userService.getUser(User.UserConnecte);

    }
}
