package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Ingredient implements Serializable {

    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String nom;
    public static final String PROP_NOM = "NOM";

    public Ingredient(String nom){
        this.nom = nom;
    }

    public String getNom() { return nom; }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        if (support == null) {
            support = new PropertyChangeSupport(this);
        }
        this.support.addPropertyChangeListener(listener);
    }
}
