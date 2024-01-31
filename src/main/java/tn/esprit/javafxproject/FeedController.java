package tn.esprit.javafxproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Publication;
import tn.esprit.javafxproject.services.PublicationService;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeedController implements Initializable {

    @FXML
    private TextArea publicationInput;

    @FXML
    private Button postButton;

    @FXML
    private VBox publicationVBox;

    private PublicationService publicationService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.publicationService = new PublicationService(getDatabaseConnection());

            // Call updatePublicationVBox to load and display publications on initialization
            updatePublicationVBox();
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
    private void addPublication() {
        String content = publicationInput.getText();

        // Assuming IdUser, Likes, Shares, Attachments need default or specific values
        Publication newPublication = new Publication(1, content, 0, 0, "");

        try {
            boolean success = publicationService.add(newPublication);

            if (success) {
                Platform.runLater(() -> {
                    updatePublicationVBox();
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

    private void updatePublicationVBox() {
        try {
            // Get all publications from the database
            ArrayList<Publication> publications = publicationService.getAll();

            // Clear the current items in the VBox
            publicationVBox.getChildren().clear();

            // Add each publication to the VBox
            for (Publication publication : publications) {
                // Create a custom HBox for each publication and add it to the VBox
                VBox publicationBox = createPublicationBox(publication);
                publicationVBox.getChildren().add(publicationBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    private VBox createPublicationBox(Publication publication) {
        // Create a custom VBox to represent a publication
        VBox publicationBox = new VBox();
        publicationBox.getStyleClass().add("publication-box");

        // Add labels or other nodes to display details of the publication
        Label userIdLabel = new Label("User ID: " + publication.getIdUser());
        Label contentLabel = new Label("Content: " + publication.getContent());
        Label likesLabel = new Label("Likes: " + publication.getLikes());
        Label sharesLabel = new Label("Shares: " + publication.getShares());

        // Apply styles to labels
        userIdLabel.getStyleClass().add("publication-label");
        contentLabel.getStyleClass().add("publication-label");
        likesLabel.getStyleClass().add("publication-label");
        sharesLabel.getStyleClass().add("publication-label");

        // Add labels to the VBox
        publicationBox.getChildren().addAll(userIdLabel, contentLabel, likesLabel, sharesLabel);

        return publicationBox;
    }


}
