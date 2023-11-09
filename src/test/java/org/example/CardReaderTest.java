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
        var cards1 = reader1.getCards();
        assertEquals(1, cards1.get(0).getDenomination());
        assertEquals(2, cards1.get(1).getDenomination());
        assertEquals(3, cards1.get(2).getDenomination());

        final var test2URL = this.getClass().getResource("/test_bad_deck.txt");
        assertNotNull(test2URL);
        var reader2 = new CardReader(test2URL.getPath());
        var cards2 = reader2.getCards();
        assertEquals(1, cards2.get(0).getDenomination());
        assertEquals(2, cards2.get(1).getDenomination());
        assertEquals(3, cards2.get(2).getDenomination());
    }
}