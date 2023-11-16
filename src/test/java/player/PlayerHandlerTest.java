package player;

import org.junit.jupiter.api.Test;
import player.exceptions.InsufficientCardException;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerHandlerTest {
    @Test
    void playerHandler() {
        // Success
        assertDoesNotThrow(() -> {
            new PlayerHandler(1, "packs/one.txt");
            new PlayerHandler(2, "packs/two.txt");
            new PlayerHandler(4, "packs/three.txt");
            new PlayerHandler(100, "packs/four.txt");
        });
        // File does not exist
        assertThrows(FileNotFoundException.class, () ->
                new PlayerHandler(4, "packs/fileNotFound.txt"));
        // Not enough cards in deck (one.txt only has enough cards for 1 player, not 4)
        assertThrows(InsufficientCardException.class, () ->
            new PlayerHandler(4, "packs/one.txt"));
    }

    @Test
    void playGame() {
        assertDoesNotThrow(() -> {
            var handler1 = new PlayerHandler(1, "packs/one.txt");
            var handler2 = new PlayerHandler(2, "packs/two.txt");
            var handler3 = new PlayerHandler(4, "packs/three.txt");
            var handler4 = new PlayerHandler(100, "packs/four.txt");

            // Player 1 wins one.txt, player 2 wins two.txt, ect...
            assertEquals(1, handler1.playGame());
            assertEquals(2, handler2.playGame());
            assertEquals(3, handler3.playGame());
            assertEquals(4, handler4.playGame());
        });
    }
}