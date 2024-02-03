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
    public Boolean verif_password() {
        Boolean test = true;
        String champ = NewPassword.getText();
        if (champ == "") {
            test = false;
        }
        int count = 0;
        for (int i = 0; i < champ.length(); i++) {
            if (champ.charAt(i) == ' ') {
                count = count + 1;
            }
        }

        if (count == champ.length()) {
            test = false;
        }
        if (champ.length() < 6) {
            test = false;
        }

        return test;
    }


    public void retour(ActionEvent event) throws IOException {

        FXMLLoader root = new FXMLLoader(this.getClass().getResource("Profil.fxml"));
        Parent parent = (Parent) root.load();
        Object ProfilController = root.getController();
        if (root.getController() instanceof ProfilController) {
            if (User.Role_User_Connecte == 0) {
                ((ProfilController)ProfilController).adminsidebarController = this.profilController.adminsidebarController;
                this.profilController.adminsidebarController.borderPane.setCenter(parent);
            } else {
                ((ProfilController)ProfilController).sidebarController = this.profilController.sidebarController;
                this.profilController.sidebarController.borderPane.setCenter(parent);
            }
        }


    }

    public void resetPassword(ActionEvent event) {
        User u =userService.getUser(User.UserConnecte);
        String passwordHashed = BCrypt.hashpw(NewPassword.getText(), BCrypt.gensalt());
        if (!verif_password())
        {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Mot de passe doit contenir au moins 6 caractères");
            al.show();
        } else {
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
                al.setContentText("Ancien mot de passe incorrect ");
                al.show();

            }}

    }
}