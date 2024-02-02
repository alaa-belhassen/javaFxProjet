package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.services.EmojiServiceImpl;

import java.io.File;

public class EmoteAddCardAdmin {

    @FXML
    private TextField imageUrl;

    @FXML
    private TextField nomemoji;

    @FXML
    private TextField prix;

    @FXML
    private TextField rank;

    @FXML
    private Label error;

    SidebarController sidebarController;
    @FXML
    void ajouterEmoji(MouseEvent event) {
        EmojiServiceImpl emojiService = new EmojiServiceImpl();
        Emoji emoji = new Emoji();
        try {
            emoji.setPrix(Integer.parseInt(prix.getText()));
            emoji.setNomEmoji(nomemoji.getText());
            emoji.setImageUrl(imageUrl.getText());
            emoji.setRank(Integer.parseInt(rank.getText()));

        }catch (Exception e){
            error.setText("");
        }

        if(nomemoji.getText() == "" || prix.getText() == ""  || rank.getText() == "" || imageUrl.getText()== ""){
            error.setText("data not correct ");
        }else{
            emojiService.add(emoji);
        }
    }



    @FXML
    private void UploadImageActionPerformed(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();


        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String im=selectedFile.toURI().toString().substring(6);

            System.out.println(im);
            this.imageUrl.setText(im);
        } else {
            // The user canceled the file selection
            System.out.println("File selection canceled.");
        }



    }




}
