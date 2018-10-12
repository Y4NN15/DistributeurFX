package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Distributeur implements Serializable {

    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    private List<Boisson> boissons;
    public static final String PROP_BOISSONS_ADD = "BOISSONS_ADD";
    public static final String PROP_BOISSONS_DEL = "BOISSONS_DEL";

    public Distributeur(){
        this.boissons = new ArrayList<>();
    }

    public List<Boisson> getBoissons(){
        return Collections.unmodifiableList(this.boissons);
    }

    public void addBoisson(String nomBoisson){
        Boisson boisson = new Boisson(nomBoisson);
        this.boissons.add(boisson);
        support.fireIndexedPropertyChange(PROP_BOISSONS_ADD, this.boissons.indexOf(boisson), null, boisson);
    }

    public void supprBoisson(Boisson boissonAsuppr) {
        int index = this.boissons.indexOf(boissonAsuppr);
        this.boissons.remove(index);
        support.fireIndexedPropertyChange(PROP_BOISSONS_DEL, index, boissonAsuppr, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        if (support == null) {
            support = new PropertyChangeSupport(this);
        }
        this.support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.support.removePropertyChangeListener(listener);
    }
}
