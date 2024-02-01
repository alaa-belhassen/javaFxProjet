package tn.esprit.javafxproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Publication;
import tn.esprit.javafxproject.services.PublicationService;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class FeedController implements Initializable {

    @FXML
    private TextArea publicationInput;
    public SidebarController sideBarController;

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

            // Sort publications by timestamp in descending order
            publications.sort(Comparator.comparing(Publication::getTimestamp).reversed());

            // Clear the current items in the VBox
            publicationVBox.getChildren().clear();

            // Add each publication to the VBox
            for (Publication publication : publications) {
                // Create a custom VBox for each publication and add it to the VBox
                VBox publicationBox = createPublicationBox(publication);
                publicationVBox.getChildren().add(publicationBox);
                // Add spacing between each VBox
                publicationVBox.setSpacing(10); // Adjust the spacing as needed
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

        publicationBox.setPadding(new Insets(100, 100, 100, 100)); // Adjust the values as needed

        // Create an HBox for userIdLabel
        HBox userIdBox = new HBox();
        userIdBox.setSpacing(10);

        // Add label for user ID
        Label userIdLabel = new Label("User ID: " + publication.getIdUser());

        // Add userIdLabel to the HBox
        userIdBox.getChildren().add(userIdLabel);

        // Format timestamp to display only date and time (hours and minutes)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTimestamp = publication.getTimestamp().toLocalDateTime().format(formatter);

        // Create a Label for displaying the formatted timestamp
        Label timestampLabel = new Label("Time: " + formattedTimestamp);
        timestampLabel.getStyleClass().add("timestamp-label");

        // Create an HBox for timestampLabel, aligned to the right
        HBox timestampBox = new HBox();
        timestampBox.setAlignment(Pos.TOP_RIGHT);
        timestampBox.getChildren().add(timestampLabel);

        // Create a Label for displaying the content
        Label contentLabel = new Label("Content: " + publication.getContent());

        // Create an HBox for Likes and Shares, aligned to the bottom right
        HBox likesSharesBox = new HBox(10); // Adjust the spacing as needed
        Label likesLabel = new Label("Likes: " + publication.getLikes());
        Label sharesLabel = new Label("Shares: " + publication.getShares());

        // Add Likes and Shares labels to the HBox
        likesSharesBox.getChildren().addAll(likesLabel, sharesLabel);

        // Set alignment for Likes and Shares HBox to bottom right
        likesSharesBox.setAlignment(Pos.BOTTOM_RIGHT);

        // Apply styles to labels
        userIdLabel.getStyleClass().add("publication-label");
        userIdLabel.getStyleClass().add("user-id-label");

        timestampLabel.getStyleClass().add("timestamp-label");

        contentLabel.getStyleClass().add("publication-label");
        contentLabel.getStyleClass().add("content-label");

        likesLabel.getStyleClass().add("publication-label");
        likesLabel.getStyleClass().add("likes-shares-label");

        sharesLabel.getStyleClass().add("publication-label");
        sharesLabel.getStyleClass().add("likes-shares-label");

        // Add userIdBox, timestampBox, contentLabel, and Likes/Shares HBox to the VBox with appropriate spacing
        publicationBox.getChildren().addAll(userIdBox, timestampBox, contentLabel, likesSharesBox);

        return publicationBox;
    }

}
