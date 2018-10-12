package viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Boisson;
import model.Ingredient;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BoissonVM implements PropertyChangeListener {

    private Boisson model;

    private StringProperty nomBoisson = new SimpleStringProperty();
        public String getNomBoisson() {return nomBoisson.get();}
        public StringProperty nomBoissonProperty() {return nomBoisson;}
        public void setNomBoisson(String nomBoisson) {this.nomBoisson.set(nomBoisson);}

    private ObservableList<IngredientVM> ingredientsVMObs = FXCollections.observableArrayList();
    private ListProperty<IngredientVM> ingredientsVM = new SimpleListProperty<>(ingredientsVMObs);
        public ObservableList<IngredientVM> getIngredientsVM() {return FXCollections.unmodifiableObservableList(ingredientsVM.get());}
        public ReadOnlyListProperty<IngredientVM> ingredientsVMProperty() {return ingredientsVM;}
        private void setIngredientsVM(ObservableList<IngredientVM> ingredientsVM) {this.ingredientsVM.set(ingredientsVM);}

    public BoissonVM(Boisson boisson) {
        this.model = boisson;
        this.setNomBoisson(this.model.getNom());
        this.nomBoisson.addListener((obs, oldV, newV) -> {
            this.setNomBoisson(newV);
        });
        this.model.addPropertyChangeListener(this);

        this.model.getIngredients().forEach(elem -> this.ingredientsVMObs.add(new IngredientVM(elem)));
    }

    public void addIngredient(String nomIngr) {
        this.model.addIngredient(nomIngr);
    }

    public void supprimerIngr(Ingredient ingr){
        this.model.supprimerIngredient(ingr);
    }

    public Boisson getModel(){
        return this.model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IndexedPropertyChangeEvent ipce = (IndexedPropertyChangeEvent) evt;
        if (evt.getPropertyName().equals(model.PROP_NOM)){
            this.nomBoisson.set((String)evt.getNewValue());
        }
        if (evt.getPropertyName().equals(model.PROP_INGR_ADD)){
            this.ingredientsVMObs.add(ipce.getIndex(), new IngredientVM((Ingredient) ipce.getNewValue()));
        }
        if (evt.getPropertyName().equals(model.PROP_INGR_DEL)){
            this.ingredientsVMObs.remove(ipce.getIndex());
        }
    }
}
