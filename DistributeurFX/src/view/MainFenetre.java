package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viewmodel.BoissonVM;
import viewmodel.DistributeurVM;
import viewmodel.IngredientVM;

import java.io.IOException;

public class MainFenetre {

    private DistributeurVM viewmodel;

    @FXML
    private TextField tfieldBoisson;

    @FXML
    private TextField tfieldIngredient;

    @FXML
    private ListView<BoissonVM> listViewBoissons;

    @FXML
    private ListView<IngredientVM> listViewIngredients;

    public void initialize(){
        this.viewmodel = new DistributeurVM();

        /*
         * LISTE DE BOISSONS
         */

        this.listViewBoissons.itemsProperty().bind(this.viewmodel.boissonsVMProperty());

        this.listViewBoissons.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (oldV != null) {
                tfieldBoisson.textProperty().unbindBidirectional(oldV.nomBoissonProperty());
                tfieldBoisson.setText("");
            }
            if (newV != null) {
                tfieldBoisson.textProperty().bindBidirectional(newV.nomBoissonProperty());
            }
        });

        this.listViewBoissons.setCellFactory(__ -> new ListCell<BoissonVM>() {
            @Override
            protected void updateItem(BoissonVM item, boolean empty){
                super.updateItem(item, empty);
                if (!empty){
                    textProperty().bind(item.nomBoissonProperty());
                } else {
                    textProperty().unbind();
                    setText("");
                }
            }
        });

        /*
         * LISTE D'INGREDIENTS
         */

        // this.listViewIngredients.itemsProperty().bind(this.listViewBoissons.getSelectionModel().getSelectedItem().ingredientsVMProperty());

        this.listViewIngredients.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (oldV != null) {
                this.tfieldIngredient.textProperty().unbindBidirectional(oldV.nomIngrProperty());
                this.tfieldIngredient.setText("");
            }
            if (newV != null) {
                this.tfieldIngredient.textProperty().bindBidirectional(newV.nomIngrProperty());
            }
        });

        this.listViewIngredients.setCellFactory(__ -> new ListCell<IngredientVM>() {
            @Override
            protected void updateItem(IngredientVM item, boolean empty){
                super.updateItem(item, empty);
                if (!empty){
                    textProperty().bind(item.nomIngrProperty());
                } else {
                    textProperty().unbind();
                    setText("");
                }
            }
        });
    }

    @FXML
    public void addBoisson(){
        try {
            String boissonAajouter = this.ouvrirFenetreAjout();
            if (boissonAajouter != null) {
                this.viewmodel.addBoisson(boissonAajouter);
            }
        } catch (IOException ioe) {
            this.fenetreErreur("La fenetre d'ajout n'a pas pu s'ouvrir.");
        }
    }

    @FXML
    public void addIngr(){
        try {
            String ingrAajouter = this.ouvrirFenetreAjout();
            if (ingrAajouter != null) {
                this.listViewBoissons.getSelectionModel().getSelectedItem().addIngredient(ingrAajouter);
            }
        } catch (IOException ioe) {
            this.fenetreErreur("La fenetre d'ajout n'a pas pu s'ouvrir.");
        }
    }

    public String ouvrirFenetreAjout() throws IOException {
        Stage stageFenetreAjout = new Stage();
        stageFenetreAjout.initOwner(tfieldBoisson.getScene().getWindow());
        stageFenetreAjout.initModality(Modality.WINDOW_MODAL);
        AjoutFenetre controller = initFenetreAjout(stageFenetreAjout);
        if (controller.getAjout() != null){
            return controller.getAjout();
        }
        return null;
    }

    public AjoutFenetre initFenetreAjout(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjoutFenetre.fxml"));
        AjoutFenetre controller = new AjoutFenetre();
        loader.setController(controller);
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
        return controller;
    }

    @FXML
    public void supprimerBoisson(){
        this.viewmodel.supprBoisson(this.listViewBoissons.getSelectionModel().getSelectedItem().getModel());
    }

    @FXML
    public void supprimerIngr(){
        this.listViewBoissons.getSelectionModel().getSelectedItem().supprimerIngr(this.listViewIngredients.getSelectionModel().getSelectedItem().getModel());
    }

    @FXML
    public void save(){
        try {
            this.viewmodel.saveDistributeur();
        } catch (IOException ioe){
            this.fenetreErreur("La sauvegarde a échoué.");
        }
    }

    @FXML
    public void load(){
        try {
            this.viewmodel.loadDistributeur();
        } catch (Exception e){
            this.fenetreErreur("Le chargement a échoué.");
        }
    }

    @FXML
    public void about(){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Programme conçu par Yannis Perrot, LP Web Clermont-Ferrnad \n\nContact : yannis.perrot@etu.uca.fr", ButtonType.OK);
        alertInfo.setHeaderText(null);
        alertInfo.showAndWait();
    }

    @FXML
    public void quit(){
        this.tfieldBoisson.getScene().getWindow().hide();
    }

    public void fenetreErreur(String texteAafficher) {
        Alert alertError = new Alert(Alert.AlertType.ERROR, texteAafficher, ButtonType.OK);
        alertError.setHeaderText(null);
        alertError.show();
    }
}
