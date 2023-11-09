package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardReaderTest {
    private final String PATH = "C:\\Users\\henry\\IdeaProjects\\CardGame\\src\\test\\java\\org\\example\\";

    @Test
    void success() {
        var reader = new CardReader(PATH + "test_deck.txt");
        assertTrue(reader.success());
    }

    @Test
    void getCards() {
        var reader1 = new CardReader(PATH + "test_deck.txt");
        var cards1 = reader1.getCards();
        assertEquals(1, cards1.get(0).getDenomination());
        assertEquals(2, cards1.get(1).getDenomination());
        assertEquals(3, cards1.get(2).getDenomination());

        var reader2 = new CardReader(PATH + "test_bad_deck.txt");
        var cards2 = reader2.getCards();
        assertEquals(1, cards2.get(0).getDenomination());
        assertEquals(2, cards2.get(1).getDenomination());
        assertEquals(3, cards2.get(2).getDenomination());
    }
}