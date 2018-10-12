package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boisson implements Serializable {

    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String nom;
    public final static String PROP_NOM = "NOM";

    private List<Ingredient> ingredients;
    public final static String PROP_INGR_ADD = "INGREDIENTS_ADD";
    public final static String PROP_INGR_DEL = "INGREDIENTS_DEL";

    public Boisson(String nom){
        this.ingredients = new ArrayList<>();
        this.nom = nom;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        this.support.firePropertyChange(PROP_NOM, oldNom, this.nom);
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public void addIngredient(String nomIngr) {
        Ingredient ingr = new Ingredient(nomIngr);
        this.ingredients.add(ingr);
        this.support.fireIndexedPropertyChange(PROP_INGR_ADD, this.ingredients.indexOf(ingr), null, ingr);
    }

    public void supprimerIngredient(Ingredient ingr) {
        int index = this.ingredients.indexOf(ingr);
        this.ingredients.remove(index);
        support.fireIndexedPropertyChange(PROP_INGR_DEL, index, ingr, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        if (support == null) {
            support = new PropertyChangeSupport(this);
        }
        this.support.addPropertyChangeListener(listener);
    }
}
