package org.example;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFile {
    private final String filename;

    public TextFile(String fn) {
        filename = fn;
        try{
            File textFile = new File(filename);
            if (!textFile.createNewFile()) {
                textFile.delete();
                textFile.createNewFile();
            }
        } catch (IOException e) {}
    }

    public boolean write(String s) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(s);
            myWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
