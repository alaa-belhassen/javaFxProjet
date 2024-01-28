package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Don;

import java.io.IOException;

public class HistoriqueRowController {
    @FXML
    private Label comment;

    @FXML
    private Label montant;

    @FXML
    private Label nomDonneur;

    @FXML
    private Label nomEmoji;

    @FXML
    private Label nomReceveur;

    @FXML
    private Label qt;


    @FXML
    void getInvoice(MouseEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("invoice.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 583, 400);
            InvoiceController invoiceController = fxmlLoader.getController();
            invoiceController.setQt(qt.getText());
            invoiceController.setPrix(montant.getText());
            Stage stage = new Stage();
            stage.setTitle("Invoice");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Don don){
        comment.setText(don.getCommentaire());
        nomDonneur.setText(don.getDonneur().getNom());
        nomReceveur.setText(don.getReceveur().getNom());
        nomEmoji.setText(don.getEmoji().getNomEmoji());
        montant.setText(Double.toString(don.getMontant()));
        qt.setText(Double.toString(don.getMontant()/don.getEmoji().getPrix()));
    }
    

}
