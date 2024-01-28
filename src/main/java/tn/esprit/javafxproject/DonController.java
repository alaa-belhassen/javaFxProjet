package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.services.EmojiServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DonController implements Initializable {
    @FXML
    private GridPane donationsContainer;
    private List<Emoji> emojis;

    SidebarController sidebarController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try{
            emojis = new ArrayList<>(getEmojis());
            System.out.println(emojis);
            for(Emoji emoji : emojis){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("donationCard.fxml"));
                VBox vbox = fxmlLoader.load();
                DonationCardController donationCardController = fxmlLoader.getController();
                donationCardController.donController = this;
                donationCardController.setDataEmoji(emoji);
                if(column==3){
                    column=0;
                    ++row;
                }
                donationsContainer.add(vbox,column++,row);
                GridPane.setMargin(vbox, new Insets(10));
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goToHistorque(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("historiqueDonation.fxml"));
        Parent parent = fxmlLoader.load();
        DonationHistoriqueController historiqueController =fxmlLoader.getController();
        historiqueController.donController = this;
        sidebarController.getScreen().setCenter(parent);
    }
    private List<Emoji> getEmojis() throws SQLException {
        List<Emoji> emojis ;
        EmojiServiceImpl emoji = new EmojiServiceImpl();
        emojis = emoji.getAll();
        return emojis ;
    }



}
