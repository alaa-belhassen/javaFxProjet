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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;

import java.io.File;
import java.io.IOException;

public class AjouterEquipeController {

    public UsersController usersController;
    public tn.esprit.javafxproject.RolesController RolesController;
    UserServiceImpl UserService = new UserServiceImpl();
    DbConnection db = DbConnection.getInstance();
    @FXML
    private Label result;
    @FXML
    private TextField nom;
    @FXML
    private TextField email;
    @FXML
    private TextField telephone;
    @FXML
    private TextField adresse;
    @FXML
    private Button load;
    @FXML
    private TextField ImageString;

    @FXML
    void UploadImageActionPerformed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();


        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String im = selectedFile.toURI().toString().substring(6);

            this.ImageString.setText(im);
        } else {

            System.out.println("File selection canceled.");
        }


    }


    public Boolean verif_Telephone() {
        Boolean test = true;
        String id = telephone.getText();
        if (id == "") {
            test = false;
        }
        int count = 0;
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) == ' ') {
                count = count + 1;
            }
        }

        if (count == id.length()) {
            test = false;
        }
        for (int i = 0; i < id.length(); i++) {
            if ((id.charAt(i) < '0') || (id.charAt(i) > '9') || (id.length() != 8))
                test = false;
        }
        return test;
    }


    public Boolean verif_nom(TextField t) {
        Boolean test = true;
        String champ = t.getText();
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
        for (int i = 0; i < champ.length(); i++) {
            if (((champ.charAt(i) < 'A') || (champ.charAt(i) > 'Z')) &&
                    ((champ.charAt(i) < 'a') || (champ.charAt(i) > 'z')) &&
                    (champ.charAt(i) != ' ') || (champ.length() < 2))
                test = false;
        }


        return test;
    }

    public Boolean verif_mail() {
        Boolean test = true;
        String champ = email.getText();
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
        int count2 = 0;
        for (int i = 0; i < champ.length(); i++) {
            if (champ.charAt(i) == '@') {
                count2 = count2 + 1;
            }


        }

        if (count2 == 0) {
            test = false;
        }
        for (int i = 0; i < champ.length(); i++) {
            if ((champ.length() < 8))
                test = false;
        }
        return test;
    }

    @FXML
    private Button retour;

    @FXML
    void retour(ActionEvent event) throws IOException {
        retourner();
    }

    private void retourner() throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("Users.fxml"));
        Parent parent = (Parent) root.load();
        Object UsersController = root.getController();
        if (root.getController() instanceof UsersController) {
            ((UsersController) UsersController).AdminsidebarController = this.usersController.AdminsidebarController;
            this.usersController.AdminsidebarController.borderPane.setCenter(parent);
        }
    }

    @FXML
    void AddUser(ActionEvent event) throws IOException {
        User user = new User();
        user.setNom(nom.getText());
        user.setEmail(email.getText());
        user.setTelephone(telephone.getText());
        user.setAdresse(adresse.getText());
        user.setImage(this.ImageString.getText());

        if (UserService.emailExists(user.getEmail())) {

            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Participant existant !!! ");
            al.show();
        } else if (!verif_nom(nom)) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Vérifier le nom saisi ");
            al.show();
        } else if (!verif_mail()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Ce n'est pas une adresse électronique");
            al.show();
        } else if (!verif_Telephone()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Vérifier le numéro de téléphone saisi ");
            al.show();
        } else if (this.ImageString.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Insert image");
            al.show();
        } else if (verif_Telephone() && verif_nom(nom) && verif_mail()) {
            UserService.CreateAccount(user);
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Alert");
            al.setContentText("Votre compte est bien enregistré");
            al.show();
            this.retourner();
        }


    }
}
