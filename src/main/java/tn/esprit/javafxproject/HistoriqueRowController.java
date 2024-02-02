package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.services.DonServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

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

    public Don don;

    @FXML
    private HBox row;
    DonationHistoriqueController donationHistoriqueController;
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
    @FXML
    void revoke(MouseEvent event) throws SQLException {
        DonServiceImpl donService = new DonServiceImpl();
        donService.delete(don.getIdDon());
        donationHistoriqueController.deleteSelectedRow(this);
    }
    public void setData(Don don){
        comment.setText(don.getCommentaire());
        nomDonneur.setText(don.getDonneur().getNom());
        nomReceveur.setText(don.getReceveur().getNom());
        nomEmoji.setText(don.getEmoji().getNomEmoji());
        montant.setText(Double.toString(don.getMontant()));
        qt.setText(Double.toString(don.getMontant()/don.getEmoji().getPrix()));
    }

    public HBox getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoriqueRowController that = (HistoriqueRowController) o;
        return Objects.equals(comment, that.comment) && Objects.equals(montant, that.montant) && Objects.equals(nomDonneur, that.nomDonneur) && Objects.equals(nomEmoji, that.nomEmoji) && Objects.equals(nomReceveur, that.nomReceveur) && Objects.equals(qt, that.qt) && Objects.equals(don, that.don) && Objects.equals(row, that.row) && Objects.equals(donationHistoriqueController, that.donationHistoriqueController);
    }


}
