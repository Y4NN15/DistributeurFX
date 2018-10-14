package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AjoutFenetre {

    @FXML
    private TextField tfieldAjout;

    private String ajout;

    @FXML
    public void ajouter(){
        this.ajout = this.tfieldAjout.getText();
        this.tfieldAjout.getScene().getWindow().hide();
    }

    @FXML
    public void annuler(){
        this.ajout = null;
        this.tfieldAjout.getScene().getWindow().hide();
    }

    public String getAjout(){
        return this.ajout;
    }
}
