package tn.esprit.javafxproject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.javafxproject.models.Reclamation;
import tn.esprit.javafxproject.models.Response;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.ReclamationService;
import tn.esprit.javafxproject.services.ReponseService;

import java.sql.SQLException;
import java.util.List;

public class ReclamationAdminController {

    @FXML
    private TableColumn<Reclamation, Integer> Colnumrec;

    @FXML
    private TableView<Reclamation> TabRec;

    @FXML
    private TextArea TfRec;

    @FXML
    private Button btnRes;

    @FXML
    private Button btnValid;

    @FXML
    private TableColumn<Reclamation, String> colrec;

    @FXML
    private TableColumn<Reclamation, String> colstatus;

    @FXML
    private TableColumn<Reclamation, String> coluser;

    @FXML
    private TextArea tfRep;

    public AdminSidebarController adminSideBar;
    private ReclamationService reclamationService = new ReclamationService();
    private ReponseService reponseService=new ReponseService();

    @FXML
    void ResetReponse(ActionEvent event) {
        tfRep.clear();
    }

    @FXML
    void ValiderReponse(ActionEvent event) {
        // Assuming you have the selected Reclamation object from the TableView
        Reclamation selectedReclamation = TabRec.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            // Assuming you have the Response object (rep) to be inserted
            Response rep = new Response();
            rep.setId_user(selectedReclamation.getId_user());
            rep.setDescription_reponse(tfRep.getText());

            try {
                boolean insertionSuccess = reponseService.addrep(rep, selectedReclamation);

                Alert alert = new Alert(insertionSuccess ? Alert.AlertType.INFORMATION: Alert.AlertType.ERROR);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);

                if (insertionSuccess) {
                    alert.setContentText("Response inserted successfully!");
                    boolean updateSuccess = reclamationService.update(selectedReclamation);

                    selectedReclamation.setStatus_reclamation("Résolu");

                    // Refresh the TableView to reflect the changes
                    TabRec.refresh();


                } else {
                    alert.setContentText("Failed to insert the response.");
                }

                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case where no row is selected in the TableView
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row in the TableView.");
            alert.showAndWait();
        }

        String descriptionReponse = tfRep.getText();

        if (descriptionReponse.isEmpty()) {
            // Show an alert for empty description
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a description for the response.");
            alert.showAndWait();
            return; // Exit the method if the description is empty
        }



    }












    @FXML
    void initialize() {
        // Set up column mappings
        Colnumrec.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_reclamation()).asObject());
        coluser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_user()));
        colstatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus_reclamation()));
        colrec.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription_reclamation()));


        // Call refreshTableView to initially populate the table
        refreshTableView();



        TfRec.setEditable(false);
        // Ajoutez un EventHandler pour gérer les événements de sélection dans le TableView
        TabRec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettez à jour le texte du TextArea avec la valeur de la colonne colrec
                TfRec.setText(newValue.getDescription_reclamation());
            }
        });






    }

    private void refreshTableView() {
        try {
            List<Reclamation> reclamationsList = reclamationService.getAll();
            ObservableList<Reclamation> observableList = FXCollections.observableArrayList(reclamationsList);
            TabRec.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
