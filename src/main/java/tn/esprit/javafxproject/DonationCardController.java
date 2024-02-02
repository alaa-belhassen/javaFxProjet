package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.services.EmojiServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class DonationCardController {
    @FXML
    private Button btnDonate;

    @FXML
    private Label exp;

    @FXML
    private Label name;

    @FXML
    private Label prix;

    @FXML
    private ImageView image;
    DonController donController;
    DonationHistoriqueController donationHistoriqueController;

    @FXML
    void donate(MouseEvent event) throws IOException, SQLException {
        loadPage("donate-Details");
    }
    public void setDataEmoji(Emoji emoji) throws FileNotFoundException {

        Image image =  new Image(new FileInputStream(emoji.getImageUrl()));
        this.image.setImage(image);
        exp.setText(Integer.toString(emoji.getRank()));
        name.setText(emoji.getNomEmoji());
        prix.setText(Integer.toString(emoji.getPrix()));
    }


    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();
        DonateDetailsController donateDetailsController = root.getController();
        donateDetailsController.setName(this.name.getText());
        donateDetailsController.setExp(this.exp.getText());
        donateDetailsController.setPrix(this.prix.getText());
        donateDetailsController.donationCardController = this;
        donController.sidebarController.getScreen().setCenter(parent);
        System.out.println(parent);
    }
}
