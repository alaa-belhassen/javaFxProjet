package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
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
     String passwordHashed = BCrypt.hashpw(NewPassword.getText(), BCrypt.gensalt());

     if(userService.changePassword(u.getEmail(),OldPassword.getText(),passwordHashed))
     {
         Alert al = new Alert(Alert.AlertType.CONFIRMATION);
         al.setTitle("Alert");
         al.setContentText("Mot de passe changé ");
         al.show();
     }
     else {
         Alert al = new Alert(Alert.AlertType.CONFIRMATION);
         al.setTitle("Alert");
         al.setContentText("Les deux mots de passe ne sont pas correspondants");
         al.show();

     }

    }
}
