package util;

import java.io.IOException;

public interface Saver {

    void save(Object o, String filename) throws IOException;

}
