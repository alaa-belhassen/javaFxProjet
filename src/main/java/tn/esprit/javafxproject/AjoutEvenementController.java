package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Categorie;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.services.CategorieServiceImpl;
import tn.esprit.javafxproject.services.EvenementServiceImpl;
import tn.esprit.javafxproject.utils.Status;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AjoutEvenementController implements Initializable {

    public SidebarController sidebarController;


    public ListEventsEquipesController listevenementEquipe;


    @FXML
    private DatePicker dateEvent;

    @FXML
    private MenuButton dropdownCategorie;

    @FXML
    private TextField hh;

    @FXML
    private TextField libEvent;

    @FXML
    private TextField lieuEvent;

    @FXML
    private TextField mm;

    @FXML
    private TextField placesEvent;

    @FXML
    private TextField ss;

    @FXML
    private TextField prixEvent;

    @FXML
    private TextField imageUrl;

    @FXML
    private Label verif;
    public boolean verif_Nombre() {
        boolean test = true;
        String places = placesEvent.getText();
        if (places == "") {
            test = false;
        }

        for (int i = 0; i < places.length(); i++) {
            if ((places.charAt(i) < '0') || (places.charAt(i) > '9') )
                test = false;
        }
        return test;
    }

    public boolean verif_Prix() {
        boolean test = true;
        String places = prixEvent.getText();

        if (places.isEmpty()) {
            test = false;
        }

        boolean hasDecimalSeparator = false;

        for (int i = 0; i < places.length(); i++) {
            char currentChar = places.charAt(i);

            if (!((currentChar >= '0' && currentChar <= '9') || currentChar == '.' )) {
                test = false;
                break;
            }

            if (currentChar == '.' ) {
                // Ensure there is only one decimal separator
                if (hasDecimalSeparator) {
                    test = false;
                    break;
                }
                hasDecimalSeparator = true;
            }
        }

        return test;
    }


    @FXML
    void addNew(MouseEvent event) throws SQLException {

        boolean v=true;

        if(ss.getText().equals("")){
            ss.setText("00");
        }
        if(dateEvent.getValue().equals(null)){
            dateEvent.setValue(LocalDate.now());

        }

        if(v && !ss.getText().isEmpty() && !hh.getText().isEmpty() && !mm.getText().isEmpty() && !lieuEvent.getText().isEmpty() && !libEvent.getText().isEmpty() && !dateEvent.getValue().equals(null) && !placesEvent.getText().isEmpty() && !prixEvent.getText().isEmpty() && !imageUrl.getText().isEmpty() ) {
            boolean v1=true;


            if(dateEvent.getValue().equals(LocalDate.now())){
                verif.setVisible(true);
                verif.setText("*choisir la date svp");
                verif.setTextFill(Color.RED );
                v1=false;
            }

                if(dropdownCategorie.getText().equals("-")){
                    verif.setVisible(true);
                    verif.setText("*choisir la catégorie svp");
                    verif.setTextFill(Color.RED );
                    v1=false;
                }

                if(imageUrl.getText().equals("")){
                    verif.setVisible(true);
                    verif.setText("*choisir l'image svp");
                    verif.setTextFill(Color.RED );
                    v1=false;
                }


        if( !hh.getText().isEmpty())
        {
            if(Integer.parseInt(hh.getText())>23 ){

                //|| Integer.parseInt(mm.getText())>59 || Integer.parseInt(ss.getText())  > 59 ){
                verif.setVisible(true);
                verif.setText("*verifier l'heure de l'évenement svp");
                verif.setTextFill(Color.RED );
                v1=false;
            }
        }
        if( !mm.getText().isEmpty())
        {
            if(Integer.parseInt(mm.getText())>59){
                verif.setVisible(true);
                verif.setText("*verifier les minutes svp");
                verif.setTextFill(Color.RED );
                v1=false;
            }

        }


if (!verif_Nombre()){
    verif.setVisible(true);
    verif.setText("*verifier le nombre de places svp");
    verif.setTextFill(Color.RED );
    v1=false;
}

            if (!verif_Prix()){
                verif.setVisible(true);
                verif.setText("*verifier le prix svp");
                verif.setTextFill(Color.RED );
                v1=false;
            }






        if( !ss.getText().isEmpty())
        {
            if(Integer.parseInt(ss.getText())>59 ){

                //|| Integer.parseInt(mm.getText())>59 || Integer.parseInt(ss.getText())  > 59 ){
                verif.setVisible(true);
                verif.setText("*verifier les secondes svp");
                verif.setTextFill(Color.RED );
                v1=false;
            }
        }






if(v1){


            Evenement evenement=new Evenement();
            evenement.setStatus(Status.PENDING.toString());
            evenement.setPrix(Float.parseFloat(prixEvent.getText()));
            evenement.setLibelle(libEvent.getText());
            evenement.setMax_places(Integer.parseInt(placesEvent.getText()));
            System.out.println();

            evenement.setLieu(lieuEvent.getText());
            evenement.setDate_event(dateEvent.getValue());
            String nom=dropdownCategorie.getText();
            System.out.println(nom);
            CategorieServiceImpl categorieService = new CategorieServiceImpl();
            List<Categorie> categories = categorieService.getAll();

            Categorie categorieDB = categories.stream()
                    .filter(x -> x.getNom().equals(dropdownCategorie.getText()))
                    .findFirst()
                    .orElse(null);

            evenement.setId_categorie(categorieDB);

            evenement.setIdUser(2);

            evenement.setTime_event(LocalTime.parse(hh.getText()+":"+mm.getText()+":"+ss.getText()));

            evenement.setDuration(90);

            evenement.setPhoto(imageUrl.getText());
            EvenementServiceImpl evenementService=new EvenementServiceImpl();
            evenementService.add(evenement);
            // evenement.setId_categorie();
}
        }else{
            verif.setVisible(true);
            verif.setText("*verifier les champs svp");
            verif.setTextFill(Color.RED );
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






    @FXML
    void reinitialize(MouseEvent event) {

        this.ss.setText("");
        this.hh.setText("");
        this.mm.setText("");
        this.imageUrl.setText("");
        this.dateEvent.setValue(LocalDate.now());
        this.libEvent.setText("");
        this.lieuEvent.setText("");
        this.placesEvent.setText("");
        this.prixEvent.setText("");
        this.dropdownCategorie.setText("");


    }



    @FXML
    void goBack(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("ListEventsEquipes.fxml"));
        AnchorPane anchorPane=  fxmlLoader.load();
        ListEventsEquipesController listEventsEquipesController = fxmlLoader.getController();
        listEventsEquipesController.sidebarController = this.listevenementEquipe.sidebarController;
        this.listevenementEquipe.sidebarController.borderPane.setCenter(anchorPane);

    }

    private void populateDropdown() throws SQLException {
        // Fetch category names from the database
        List<String> categorieNames = getCategorieNamesFromDB();

        // Create MenuItems and add them to the dropdown
        for (String categoryName : categorieNames) {
            MenuItem menuItem = new MenuItem(categoryName);
            menuItem.setOnAction(event -> handleCategorySelection(categoryName));
            dropdownCategorie.getItems().add(menuItem);
        }
    }

    private List<String> getCategorieNamesFromDB() throws SQLException {
        // Fetch category names from the database using your service or DAO
        // Replace this with your actual database fetching logic
        CategorieServiceImpl categorieService = new CategorieServiceImpl();
        List<Categorie> categories = categorieService.getAll();

        // Extract category names from the list of categories
        List<String> categorieNames = categories.stream()
                .map(Categorie::getNom)
                .collect(Collectors.toList());

        return categorieNames;
    }

    private void handleCategorySelection(String selectedCategory) {
        // Handle the selected category here
        dropdownCategorie.setText(selectedCategory);

        System.out.println("Selected category: " + selectedCategory);
        // You can perform any additional actions based on the selected category
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateDropdown();
            dateEvent.setValue(LocalDate.now());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
