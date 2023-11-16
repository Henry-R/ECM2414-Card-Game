package player.card;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CardReaderTest {
    @Test
    void success() {
        assertDoesNotThrow(() -> new CardReader("packs/test_deck.txt"));
        assertThrows(FileNotFoundException.class, () -> new CardReader(""));
    }

    @Test
    void nextCards() {
        assertDoesNotThrow(() -> {
            var reader1 = new CardReader("packs/test_deck.txt");
            assertEquals(3, reader1.getCardCount());
            assertEquals(1, reader1.nextCard().getDenomination());
            assertEquals(2, reader1.nextCard().getDenomination());
            assertEquals(3, reader1.nextCard().getDenomination());
        });

        assertDoesNotThrow(() -> {
            var reader2 = new CardReader("packs/test_bad_deck.txt");
            assertEquals(3, reader2.getCardCount());
            assertEquals(1, reader2.nextCard().getDenomination());
            assertEquals(2, reader2.nextCard().getDenomination());
            assertEquals(3, reader2.nextCard().getDenomination());
        });
    }
}