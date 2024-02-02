package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.services.DonServiceImpl;
import tn.esprit.javafxproject.services.EmojiServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DonationHistoriqueController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    public VBox rowsContainer;
    private List<Don> dons;

    DonController donController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            dons = new ArrayList<Don>(getDons());
            System.out.println(dons);
            for(Don don : dons){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("historiqueRow.fxml"));
                VBox vbox = fxmlLoader.load();
                HistoriqueRowController historiqueRowController = fxmlLoader.getController();
                historiqueRowController.don = don;
                historiqueRowController.donationHistoriqueController = this;
                historiqueRowController.setData(don);
                rowsContainer.getChildren().add(vbox);
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSelectedRow(HistoriqueRowController rowController) {
        // Remove the corresponding VBox from rowsContainer
        rowsContainer.getChildren().remove(rowController.getRow());

        // Now remove the data from your list or wherever it is stored
        Don deletedDon = rowController.don;
        dons.remove(deletedDon);
        // Call any other necessary cleanup or update methods
    }
    @FXML
    void userInvoice(MouseEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("invoice.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 583, 400);
            InvoiceController invoiceController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setTitle("Invoice");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Don> getDons() throws SQLException {
        List<Don> dons ;
        DonServiceImpl donService = new DonServiceImpl();
        dons = donService.getAll();
        return dons ;
    }

}
