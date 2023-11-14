package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardReaderTest {
    @Test
    void success() {
        final var URL = this.getClass().getResource("/test_deck.txt");
        assertNotNull(URL);

        var reader1 = new CardReader(URL.getPath());
        assertTrue(reader1.success());

        var reader2 = new CardReader("");
        assertFalse(reader2.success());
    }

    @Test
    void getCards() {
        final var test1URL = this.getClass().getResource("/test_deck.txt");
        assertNotNull(test1URL);
        var reader1 = new CardReader(test1URL.getPath());
        assertEquals(3, reader1.getCardCount());
        assertEquals(1, reader1.nextCard().getDenomination());
        assertEquals(2, reader1.nextCard().getDenomination());
        assertEquals(3, reader1.nextCard().getDenomination());

        final var test2URL = this.getClass().getResource("/test_bad_deck.txt");
        assertNotNull(test2URL);
        var reader2 = new CardReader(test2URL.getPath());
        assertEquals(3, reader2.getCardCount());
        assertEquals(1, reader2.nextCard().getDenomination());
        assertEquals(2, reader2.nextCard().getDenomination());
        assertEquals(3, reader2.nextCard().getDenomination());
    }
}