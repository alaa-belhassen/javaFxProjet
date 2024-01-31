package tn.esprit.javafxproject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.javafxproject.models.Publication;

public class FeedController {
    @FXML
    private TextField publicationInput;

    @FXML
    private Button postButton;

    @FXML
    private ListView<String> publicationListView;

    @FXML
    private void addPublication(MouseEvent event) {
        // Handle the button click event here
        String publicationText = publicationInput.getText();
        // Add logic to handle the new publication (e.g., add to the ListView)
        publicationListView.getItems().add(publicationText);
        // Clear the input field after posting
        publicationInput.clear();
    }
}
