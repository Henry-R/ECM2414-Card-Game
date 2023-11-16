package player;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.BufferedReader; 
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileTest {
    
    @Test
    void testTextFileConstructor() {
        String testFilename = "testFile.txt";

        //Test that once the the object has been initialised, you can
        //write to the file, meaning the file exists.
        var testTextFile = new TextFile(testFilename);
        assertTrue(testTextFile.write("s"));
    }

    @Test
    void testWrite() {
        String testString = "testString";
        String testFilename = "testFile.txt";
        var testTextFile = new TextFile(testFilename);
        testTextFile.write(testString);
        String text;

        try {
            BufferedReader Buff = new BufferedReader(new FileReader(testFilename)); 
            text = Buff.readLine();
            Buff.close();
        } catch (IOException e) {
            text = null;
        }

        //Test that the string was correctly written to the file
        assertEquals(testString, text);
    }

}
