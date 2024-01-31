package tn.esprit.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import tn.esprit.javafxproject.models.Publication;
import tn.esprit.javafxproject.services.PublicationService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("feed.fxml"));

        // Set the controller for the FXMLLoader
        FeedController feedController = new FeedController();
        loader.setController(feedController);

        Parent root = loader.load();

        Scene scene = new Scene(root, 1315, 890);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {

        launch();

        try {
            // Replace the databaseUrl, username, and password with your actual database connection details
            String databaseUrl = "jdbc:postgresql://localhost:5432/JavaGeeks";
            String username = "postgres";
            String password = "SYSTEM";

            // Establish a database connection
            try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
                // Create a PublicationService instance
                PublicationService publicationService = new PublicationService(connection);

                // Test getAll()
                ArrayList<Publication> publications = publicationService.getAll();
                System.out.println("All Publications:");
                for (Publication publication : publications) {
                    System.out.println(publication);
                }

                // Test add()
                Publication newPublication = new Publication(1, "New Publication Content", 0, 0, "New Attachments");
                boolean addResult = publicationService.add(newPublication);
                System.out.println("Publication Added: " + addResult);

                // Test getAll() after adding
                publications = publicationService.getAll();
                System.out.println("All Publications after adding:");
                for (Publication publication : publications) {
                    System.out.println(publication);
                }
                Publication updatepublication = new Publication(1, "NHELLOt", 0, 0, "New Attachments");

                // Test update()
                Publication updatePublication = new Publication(1, "NHELLOt", 0, 0, "New Attachments");

                boolean updateResult = publicationService.update(updatePublication);
                System.out.println("Publication Updated: " + updateResult);


                // Test getAll() after updating
                publications = publicationService.getAll();
                System.out.println("All Publications after updating:");
                for (Publication publication : publications) {
                    System.out.println(publication);
                }

                // Test delete()
                boolean deleteResult = publicationService.delete(newPublication);
                System.out.println("Publication Deleted: " + deleteResult);

                // Test getAll() after deleting
                publications = publicationService.getAll();
                System.out.println("All Publications after deleting:");
                for (Publication publication : publications) {
                    System.out.println(publication);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
