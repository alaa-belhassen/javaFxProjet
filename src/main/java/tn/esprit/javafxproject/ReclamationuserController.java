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

public class ReclamationuserController {


    public SidebarController sidebarController;
    @FXML
    private Button btnreset;

    @FXML
    private Button btnvalider;

    @FXML
    private TableColumn<Response, Integer> colidrec;

    @FXML
    private TableColumn<Response, Integer> colidrep;

    @FXML
    private TableColumn<Response, String> colrep;
    @FXML
    private TableColumn<Response, String> colRecdesc;

    @FXML
    private TableView<Response> tabrep;

    @FXML
    private TextArea tfrec;

    // Create an instance of ReclamationService when the controller is initialized
    private ReclamationService reclamationService = new ReclamationService();
    private ReponseService reponseService=new ReponseService();


    @FXML
    void initialize() {
        // Set up column mappings
        colidrec.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_reclamation()).asObject());
        colidrep.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_reponse()).asObject());
        colrep.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription_reponse()));

        // Add the new column mapping for description_reclamation
        colRecdesc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription_reclamation_response()));

        // Call refreshTableView to initially populate the table
        refreshTableView();
    }


    @FXML
    void Validerrec(ActionEvent event) {
        System.out.println("debuT");
        System.out.println("TextArea Content: " + tfrec.getText());

        String contenuReclamation = tfrec.getText();
        System.out.println(contenuReclamation);

        Reclamation nouvelleReclamation = new Reclamation();
        nouvelleReclamation.setDescription_reclamation(contenuReclamation);
        nouvelleReclamation.setStatus_reclamation("En attente");
        nouvelleReclamation.setId_user(User.UserConnecte);

        try {
            boolean ajoutReussi = reclamationService.add(nouvelleReclamation);

            Alert alert = new Alert(ajoutReussi ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);

            if (ajoutReussi) {
                alert.setContentText("Réclamation ajoutée avec succès !");
            } else {
                alert.setContentText("Échec de l'ajout de la réclamation.");
            }

            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetrec(ActionEvent event) {
        tfrec.clear();
    }

    private void refreshTableView() {
        try {
            List<Response> responsesList = reponseService.getResponsesForUser(User.UserConnecte);

            // Print the contents of responsesList
            System.out.println("Responses List:");
            for (Response response : responsesList) {
                System.out.println(response); // Assuming Response class has a proper toString() method
            }

            ObservableList<Response> observableList = FXCollections.observableArrayList(responsesList);
            tabrep.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
