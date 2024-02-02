package tn.esprit.javafxproject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.TextFlow;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import tn.esprit.javafxproject.models.Comment;
import tn.esprit.javafxproject.models.Publication;
import tn.esprit.javafxproject.services.CommentService;
import tn.esprit.javafxproject.services.PublicationService;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FeedController implements Initializable {

    private String currentImagePath;

    private Map<Integer, HBox> publicationCommentBoxes = new HashMap<>();
    @FXML
    private TextArea publicationInput;
    public SidebarController sideBarController;
    public CommentService commentService;
    @FXML
    private Button postButton;

    @FXML
    private VBox publicationVBox;

    private PublicationService publicationService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.publicationService = new PublicationService(getDatabaseConnection());

            // Initialize CommentService with the database connection
            initializeCommentService(getDatabaseConnection());

            // Call updatePublicationVBox to load and display publications on initialization
            updatePublicationVBox();
            if (importButton == null) {
                importButton = new Button("Import"); // Adjust the label as needed
            }
        } catch (SQLException e) {
            handleDatabaseConnectionError(e);
        }
    }


    @FXML
    private Button importButton;

    @FXML
    private ImageView imageView;

    // ... existing code

    @FXML
    private void importPicture() {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        Stage stage = (Stage) importButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            displayImage(selectedFile);
            currentImagePath = selectedFile.getAbsolutePath();
            System.out.println("Image Path: " + currentImagePath);
        }
    }


    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
    }

    private void displayImage(File imageFile) {
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
        imageView.setFitWidth(200); // Adjust the width as needed
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }
    private Connection getDatabaseConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/JavaGeeks";
        String username = "postgres";
        String password = "SYSTEM";

        return DriverManager.getConnection(url, username, password);
    }
    public void initializeCommentService(Connection connection) {
        commentService = new CommentService(connection);
    }

    private void handleDatabaseConnectionError(SQLException e) {
        e.printStackTrace();
        // Handle database connection exception, e.g., show an error dialog
    }



    @FXML
    private void addPublication() {
        String content = publicationInput.getText();

        // Assuming IdUser, Likes, Shares need default or specific values
        Publication newPublication = new Publication(1, content, 0, 0, currentImagePath);

        try {
            boolean success = publicationService.add(newPublication);

            if (success) {
                Platform.runLater(() -> {
                    updatePublicationVBox();
                    publicationInput.clear();
                    // Reset the currentImagePath after adding a publication
                    currentImagePath = null;
                });
            } else {
                // Handle failure to add the publication
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

                // Fetch and add comments for the current publication
                List<Comment> comments = commentService.getCommentsForPublication(publication.getPublicationID());
                for (Comment comment : comments) {
                    VBox commentBox = createCommentBox(comment);
                    publicationBox.getChildren().add(commentBox);
                }

                // Add spacing between each VBox
                publicationVBox.getChildren().add(publicationBox);
                publicationVBox.setSpacing(10); // Adjust the spacing as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    private VBox createCommentBox(Comment comment) {
        // Create a custom VBox to represent a comment
        VBox commentBox = new VBox();
        commentBox.getStyleClass().add("comment-box");

        commentBox.setPadding(new Insets(10, 10, 10, 10)); // Adjust the values as needed

        // Create an HBox for userIdLabel
        HBox userIdBox = new HBox();
        userIdBox.setSpacing(10);

        // Add label for user ID
        Label userIdLabel = new Label(comment.getUserId() + " commented");

        // Add userIdLabel to the HBox
        userIdBox.getChildren().add(userIdLabel);

        // Format timestamp to display only date and time (hours and minutes)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTimestamp = comment.getTimestamp().format(formatter);

        // Create a Label for displaying the formatted timestamp
        Label timestampLabel = new Label("Time: " + formattedTimestamp);
        timestampLabel.getStyleClass().add("timestamp-label");

        // Create an HBox for timestampLabel, aligned to the right
        HBox timestampBox = new HBox();
        timestampBox.setAlignment(Pos.TOP_RIGHT);
        timestampBox.getChildren().add(timestampLabel);

        // Create a Label for displaying the content
        Label contentLabel = new Label(comment.getContent());

        // Add userIdBox, timestampBox, and contentLabel to the VBox with appropriate spacing
        commentBox.getChildren().addAll(userIdBox, timestampBox, contentLabel);

        // Apply styles to labels
        userIdLabel.getStyleClass().add("comment-label");
        userIdLabel.getStyleClass().add("user-id-label");

        timestampLabel.getStyleClass().add("timestamp-label");

        contentLabel.getStyleClass().add("comment-label");
        contentLabel.getStyleClass().add("content-label");

        return commentBox;
    }
    private void handleCommentButtonClick(Publication publication, Button commentButton) {
        // Create TextArea for commenting
        TextArea commentTextArea = new TextArea();
        commentTextArea.setPromptText("Type your comment...");

        // Create "Post Comment" button
        Button postCommentButton = new Button("Post Comment");
        postCommentButton.setOnAction(e -> handlePostCommentButtonClick(publication, commentTextArea));

        // Create HBox to hold TextArea and "Post Comment" button
        HBox commentBox = new HBox(commentTextArea, postCommentButton);
        commentBox.setSpacing(10);

        // Add the commentBox to the VBox
        VBox publicationBox = createPublicationBox(publication);
        publicationBox.getChildren().add(commentBox);

        // Update the UI
        publicationVBox.getChildren().setAll(publicationBox);
    }

    private void handlePostCommentButtonClick(Publication publication, TextArea commentTextArea) {
        String commentContent = commentTextArea.getText();

        // Assuming IdUser, Likes, Attachments need default or specific values
        Comment newComment = new Comment(publication.getPublicationID(), 1, commentContent, LocalDateTime.now(), 0, null);
        System.out.println(publication.getPublicationID());
        boolean success = commentService.addComment(newComment);

        if (success) {
            // Update the UI
            updatePublicationVBox();
        } else {
            // Handle failure to add the comment (e.g., show an error message)
        }

    }
    private VBox createPublicationBox(Publication publication) {
        // Create a custom VBox to represent a publication
        VBox publicationBox = new VBox();
        publicationBox.getStyleClass().add("publication-box");

        publicationBox.setStyle("-fx-background-color: #e0e0e0;");
        publicationBox.setPadding(new Insets(10, 10, 10, 10)); // Adjust the values as needed

        // Create an HBox for userIdLabel
        HBox userIdBox = new HBox();
        userIdBox.setSpacing(10);

        // Add label for user ID
        Label userIdLabel = new Label(publication.getIdUser() + " posted");

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


        TextFlow contentFlow = new TextFlow(new Text(publication.getContent()));
        contentFlow.setPrefWidth(600);  // Set the desired width for text wrapping (adjust as needed)


        // Create a Label for displaying the content
        Label contentLabel = new Label(publication.getContent());
        contentLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F4DECB;"); // Set font size, weight, and color
        contentLabel.setWrapText(true); // Set wrapText property to true
        // Check if there is an image path associated
        if (publication.getAttachments() != null && !publication.getAttachments().isEmpty()) {
            // Create an ImageView to display the image
            ImageView attachmentImageView = new ImageView(new Image("file:" + publication.getAttachments()));
            attachmentImageView.setFitWidth(200); // Adjust the width as needed
            attachmentImageView.setPreserveRatio(true);

            // Add the ImageView to the VBox
            publicationBox.getChildren().add(attachmentImageView);
        }


        // Create a Label for displaying the content
        Label contentLabel = new Label(publication.getContent());


        // Create an HBox for Likes and Shares, aligned to the bottom right
        HBox likesSharesBox = new HBox(10); // Adjust the spacing as needed
        Label likesLabel = new Label(String.valueOf(publication.getLikes()));
        Label sharesLabel = new Label("Shares: " + publication.getShares());

        // Create a Button for likes
        Button likeButton = new Button("Like");
        likeButton.setOnAction(e -> handleLikeButtonClick(publication));

        // Add Like button to the Likes/Shares HBox
        likesSharesBox.getChildren().addAll(likesLabel, likeButton, sharesLabel);

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
        // Create a Button for comments
        Button commentButton = new Button("Comment");
        commentButton.setOnAction(e -> handleCommentButtonClick(publication, commentButton));
        commentButton.setStyle("-fx-font-size: 10;");
        commentButton.setPrefWidth(100);

        // Create a TextArea for comments
        TextArea commentTextArea = new TextArea();
        commentTextArea.setPromptText("Write a comment...");
        commentTextArea.setVisible(false); // Initially, hide the commentTextArea

        // Create a Button for posting comments
        Button postCommentButton = new Button("Post Comment");
        postCommentButton.setOnAction(e -> handlePostCommentButtonClick(publication, commentTextArea));
        postCommentButton.setVisible(false);


        // Create an HBox for commentButton, commentTextArea, and postCommentButton
        HBox commentBox = new HBox(10); // Adjust the spacing as needed
        commentBox.getChildren().addAll(commentButton, commentTextArea, postCommentButton);

        // ... (existing code)

        // Add commentBox to the VBox
        publicationBox.getChildren().add(commentBox);

        return publicationBox;

    }

    private void handleLikeButtonClick(Publication publication) {
        try {
            // Increment likes count
            int newLikesCount = publication.getLikes() + 1;

            // Update likes count in the database
            publicationService.updateLikes(publication.getPublicationID(), newLikesCount);

            // Update UI
            Platform.runLater(this::updatePublicationVBox);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

}
