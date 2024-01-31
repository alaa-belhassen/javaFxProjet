package tn.esprit.javafxproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import tn.esprit.javafxproject.models.Publication;
import tn.esprit.javafxproject.services.PublicationService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeedController {

    @FXML
    private TextArea publicationInput;

    @FXML
    private Button postButton;

    @FXML
    private ListView<String> publicationListView;

    private PublicationService publicationService;

    // Initialize the PublicationService with a database connection
    public FeedController() {
        try {
            this.publicationService = new PublicationService(getDatabaseConnection());
        } catch (SQLException e) {
            handleDatabaseConnectionError(e);
        }
    }

    private Connection getDatabaseConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/JavaGeeks";
        String username = "postgres";
        String password = "SYSTEM";

        return DriverManager.getConnection(url, username, password);
    }

    private void handleDatabaseConnectionError(SQLException e) {
        e.printStackTrace();
        // Handle database connection exception, e.g., show an error dialog
    }

    @FXML
    private void addPublication(ActionEvent event) {
        String content = publicationInput.getText();

        // Assuming IdUser, Likes, Shares, Attachments need default or specific values
        Publication newPublication = new Publication(1, content, 0, 0, "");

        try {
            boolean success = publicationService.add(newPublication);

            if (success) {
                Platform.runLater(() -> {
                    updatePublicationListView();
                    publicationInput.clear();
                });
            } else {
                // Handle failure to add the publication (e.g., show an error message)
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    private void updatePublicationListView() {
        try {
            // Get all publications from the database
            ArrayList<Publication> publications = publicationService.getAll();

            // Clear the current items in the ListView
            publicationListView.getItems().clear();

            // Add the content of each publication to the ListView
            for (Publication publication : publications) {
                publicationListView.getItems().add(publication.getContent());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }
}
