package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.ServiceProduit;
import tn.esprit.javafxproject.utils.DbConnection;

import java.io.File;
import java.io.IOException;

public class AjoutProduitController {
    public ProduitsListController produitsListController;
    ServiceProduit serviceProduit = new ServiceProduit();
    DbConnection db = DbConnection.getInstance();
    @FXML
    private Label result;
    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField prix;
    @FXML
    private TextField stock;
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

        return test;
    }

    public boolean verif_Nombre() {
        boolean test = true;
        String places = stock.getText();
        if (places == "") {
            test = false;
        }

        for (int i = 0; i < places.length(); i++) {
            if ((places.charAt(i) < '0') || (places.charAt(i) > '9') )
                test = false;
        }
        return test;
    }

    public boolean verif_Prix() {
        boolean test = true;
        String places = prix.getText();

        if (places.isEmpty()) {
            test = false;
        }

        boolean hasDecimalSeparator = false;

        for (int i = 0; i < places.length(); i++) {
            char currentChar = places.charAt(i);

            if (!((currentChar >= '0' && currentChar <= '9') || currentChar == '.' )) {
                test = false;
                break;
            }

            if (currentChar == '.' ) {
                // Ensure there is only one decimal separator
                if (hasDecimalSeparator) {
                    test = false;
                    break;
                }
                hasDecimalSeparator = true;
            }
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
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("ProduitsList.fxml"));
        Parent parent = (Parent) root.load();
        Object produitsListController = root.getController();
        if (root.getController() instanceof ProduitsListController) {
            ((ProduitsListController) produitsListController).sidebarController = this.produitsListController.sidebarController;
            this.produitsListController.sidebarController.borderPane.setCenter(parent);
        }
    }

    @FXML
    void AddProduit(ActionEvent event) throws IOException {


        if (!verif_nom(nom)) {

            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Verifier le nom");
            al.show();
        } else if (!verif_nom(description)) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Verifier la description du produit ");
            al.show();
        } else if (!verif_Prix()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Verifier le prix");
            al.show();
        } else if (!verif_Nombre()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Verifier quantite du stock");
            al.show();
        } else if (this.ImageString.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");
            al.setContentText("Insert image");
            al.show();
        } else if (verif_nom(description) && verif_nom(nom) && verif_Prix() && verif_Nombre()) {
            Produit produit = new Produit();
            produit.setProductName(nom.getText());
            produit.setProductDescription(description.getText());
            produit.setPrice(Double.parseDouble(prix.getText()));
            produit.setQuantityInStock(Integer.parseInt(stock.getText()));
            produit.setImage(this.ImageString.getText());
            serviceProduit.add(produit);
            System.out.println(produit);
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Alert");
            al.setContentText("Produit ajouté");
            al.show();
            this.retourner();
        }


    }
}
