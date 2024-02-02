package tn.esprit.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.PdfLoader;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DbConnection.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Sidebar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 890);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        PdfLoader pdfLoader = new PdfLoader();
        pdfLoader.generatePdf();
    }
    public static void main(String[] args) {
        launch();
    }
}
