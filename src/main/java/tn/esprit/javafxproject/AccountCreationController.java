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
import tn.esprit.javafxproject.Controllers.LoginController;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;

import java.io.File;

public class AccountCreationController {
    UserServiceImpl UserService = new UserServiceImpl();
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
    private TextField password;
    @FXML
    private Button retour;
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

    @FXML
    void retour(ActionEvent event) {
        this.retourner();
    }

    private void retourner() {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) retour.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception errr) {

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
            if (((champ.charAt(i)) < 'A') || (champ.charAt(i) > 'Z') && ((champ.charAt(i)) < 'a') || (champ.charAt(i) > 'z') || (champ.length() < 2))
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

    public Boolean verif_password() {
        Boolean test = true;
        String champ = password.getText();
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

    @FXML
    void CreateAcoount(ActionEvent event) {
        User user = new User();
        user.setNom(nom.getText());
        user.setEmail(email.getText());
        user.setTelephone(telephone.getText());
        user.setAdresse(adresse.getText());
        user.setPassword(password.getText());
        user.setImage(this.ImageString.getText());
        System.out.println("value" + this.ImageString.getText());
        System.out.println(this.ImageString.getText().isEmpty());
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
        } else if (!verif_password()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Le mot de passe doit avoir au moins 6 caractères");
            al.show();
        } else if (this.ImageString.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Insert image");
            al.show();
        } else if (verif_Telephone() && verif_nom(nom) && verif_mail() && verif_password()) {
            UserService.add(user);
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Alert");
            al.setContentText("Votre compte est bien enregistré");
            al.show();
            this.retourner();
        }


    }

}
