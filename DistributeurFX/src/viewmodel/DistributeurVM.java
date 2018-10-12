package viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Boisson;
import model.Distributeur;
import util.Loader;
import util.LoaderBinary;
import util.Saver;
import util.SaverBinary;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class DistributeurVM implements PropertyChangeListener {

    private Distributeur model;

    private ObservableList<BoissonVM> boissonsVMObs = FXCollections.observableArrayList();
    private ListProperty<BoissonVM> boissonsVM = new SimpleListProperty(boissonsVMObs);
        public ObservableList<BoissonVM> getBoissonsVM() {return FXCollections.unmodifiableObservableList(boissonsVM.get());}
        public ReadOnlyListProperty<BoissonVM> boissonsVMProperty() {return boissonsVM;}
        private void setBoissonsVM(ObservableList<BoissonVM> boissonsVM) {this.boissonsVM.set(boissonsVM);}

    public DistributeurVM(){
        this.model = new Distributeur();
        this.initModel();
    }

    public void initModel(){
        this.model.addPropertyChangeListener(this);
        this.model.getBoissons().forEach(elem -> this.boissonsVMObs.add(new BoissonVM(elem)));
    }

    public void addBoisson(String nomBoisson) {
        this.model.addBoisson(nomBoisson);
    }

    public void supprBoisson(Boisson boissonAsuppr) {
        this.model.supprBoisson(boissonAsuppr);
    }

    public void saveDistributeur() throws IOException {
        Saver saver = new SaverBinary();
        saver.save(this.model, "distributeur.txt");
    }

    public void loadDistributeur() throws Exception {
        Loader loader = new LoaderBinary();
        Distributeur dis = (Distributeur) loader.load("distributeur.txt");
        this.model.removePropertyChangeListener(this);
        this.model = dis;
        this.initModel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IndexedPropertyChangeEvent ipce = (IndexedPropertyChangeEvent) evt;
        if (evt.getPropertyName().equals(this.model.PROP_BOISSONS_ADD)){
            this.boissonsVMObs.add(ipce.getIndex(), new BoissonVM((Boisson)ipce.getNewValue()));
        }
        if (evt.getPropertyName().equals(this.model.PROP_BOISSONS_DEL)){
            this.boissonsVMObs.remove(ipce.getIndex());
        }
    }
}
