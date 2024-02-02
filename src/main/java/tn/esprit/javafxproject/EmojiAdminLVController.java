package tn.esprit.javafxproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.*;
import tn.esprit.javafxproject.services.DonServiceImpl;
import tn.esprit.javafxproject.services.EmojiServiceImpl;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;

public class EmojiAdminLVController implements Initializable {

    public SidebarController sidebarController;

    @FXML
    private TableColumn<Emoji, Void> acceptdeclineEvent;

    @FXML
    private TableView<Emoji> eventTable;

    @FXML
    private TableColumn<Emoji, Integer> id;

    @FXML
    private TableColumn<Emoji, String> imageurl;

    @FXML
    private TableColumn<Emoji, String> nomemoji;

    @FXML
    private TableColumn<Emoji, Integer> prix;

    @FXML
    private TableColumn<Emoji, Integer> rank;

    @FXML
    private TableColumn<Emoji, String> status;





    @FXML
    private TextField imageUrl;

    @FXML
    private TextField nomemoji1;

    @FXML
    private TextField prix1;

    @FXML
    private TextField rank1;

    @FXML
    private Label error;

    @FXML
    void ajouterEmoji(MouseEvent event) throws SQLException {
        EmojiServiceImpl emojiService = new EmojiServiceImpl();
        Emoji emoji = new Emoji();
        try {
            emoji.setPrix(Integer.parseInt(prix1.getText()));
            emoji.setNomEmoji(nomemoji1.getText());
            emoji.setImageUrl(imageUrl.getText());
            emoji.setRank(Integer.parseInt(rank1.getText()));

        }catch (Exception e){
            error.setText("");
        }

        if(nomemoji1.getText() == "" || prix1.getText() == ""  || rank1.getText() == "" || imageUrl.getText()== ""){
            error.setText("data not correct ");
            error.setVisible(true);
        }else{
            emojiService.add(emoji);
            shoxwList(getAllList());

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





    private ObservableList<Emoji> getAllList() throws SQLException {
        EmojiServiceImpl emojiService = new EmojiServiceImpl();
        ObservableList<Emoji> oblist= FXCollections.observableArrayList(emojiService.getAll());
        return oblist;
    }

    public void shoxwList(ObservableList observableList){


        imageurl.setCellValueFactory(cellData -> {
            Emoji yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getImageUrl());
        });
        nomemoji.setCellValueFactory(cellData -> {
            Emoji yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getNomEmoji());
        });
        prix.setCellValueFactory(cellData -> {
            Emoji yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getPrix());
        });
        rank.setCellValueFactory(cellData -> {
            Emoji yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getRank());
        });
        status.setCellValueFactory(cellData -> {
            Emoji yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getStatus());
        });

        id.setCellValueFactory(cellData -> {
            Emoji yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getIdEmoji());
        });
        acceptdeclineEvent.setCellFactory(param -> new TableCell<Emoji, Void>() {
            private final Button buttonDecline = new Button("Delete");
            private final Button buttonAccept = new Button("Update");

            {
                buttonDecline.setStyle("-fx-background-color: #B90E0A;-fx-text-fill: white;");
                buttonAccept.setStyle("-fx-background-color: #3CB043;-fx-text-fill: white;");

                // Handle decline button action
                buttonDecline.setOnAction(event -> {
                    Emoji emoji = getTableView().getItems().get(getIndex());
                    try {
                        EmojiServiceImpl Service = new EmojiServiceImpl();
                        Service.delete(emoji);
                        observableList.remove(emoji);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Handle accept button action
                buttonAccept.setOnAction(event -> {
                    Emoji emoji = getTableView().getItems().get(getIndex());
                    Emoji emojiUpadate = new Emoji(emoji);

                    if(nomemoji1.getText()!=""){
                        emojiUpadate.setNomEmoji(nomemoji1.getText());
                    }
                    if(rank1.getText()!=""){
                        emojiUpadate.setRank(Integer.parseInt(rank1.getText()));
                    }
                    if(imageUrl.getText()!=""){
                        emojiUpadate.setImageUrl(imageUrl.getText());
                    }
                    if(prix1.getText()!=""){
                        emojiUpadate.setPrix(Integer.parseInt(prix1.getText()));
                        System.out.println(prix1.getText());
                    }

                    try {
                        EmojiServiceImpl emojiService = new EmojiServiceImpl();
                        emojiService.update(emojiUpadate);
                        observableList.remove(emoji);
                        observableList.add(emojiUpadate);


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    // Implement your accept logic here
                });

               /* buttonAccept.setOnAction(event -> {
                    Emoji don = getTableView().getItems().get(getIndex());
                    //pdf display
                    // Implement your accept logic here
                });*/
            }

            // Display the buttons in the cell
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(buttonDecline, buttonAccept);
                    setGraphic(buttonBox);
                }
            }
        });
        eventTable.setItems(observableList);

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            shoxwList(getAllList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




}