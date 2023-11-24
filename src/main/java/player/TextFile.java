package player;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Provides a wrapper for file string output
 */
public class TextFile {
    private final String filename;

    /**
     * Opens a text file and readies for writing
     * @param fn the file path of the file to read
     */
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

    /**
     * Outputs a string to the file associated with this TextFile object
     * @param s the text that will be outputted
     * @return True if the write was successful, false otherwise
     */
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
