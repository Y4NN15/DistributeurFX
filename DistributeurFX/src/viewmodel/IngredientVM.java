package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Ingredient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class IngredientVM implements PropertyChangeListener {

    private Ingredient model;

    private StringProperty nomIngr = new SimpleStringProperty();
        public String getNomIngr() {return nomIngr.get();}
        public StringProperty nomIngrProperty() {return nomIngr;}
        public void setNomIngr(String nomIngr) {this.nomIngr.set(nomIngr);}

    public IngredientVM(Ingredient ingr) {
        this.model = ingr;
        this.setNomIngr(this.model.getNom());
        this.nomIngr.addListener((obs, oldV, newV) -> {
            this.setNomIngr(newV);
        });
        this.model.addPropertyChangeListener(this);
    }

    public Ingredient getModel(){
        return this.model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(model.PROP_NOM)){
            this.setNomIngr((String) evt.getNewValue());
        }
    }
}
