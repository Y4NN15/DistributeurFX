package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoaderBinary implements Loader {

    @Override
    public Object load(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            return ois.readObject();
        }
    }
}
