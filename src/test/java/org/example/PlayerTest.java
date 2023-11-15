package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerConstructor() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);

        // Test cards in hand are in the correct order
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck);
        TestPlayer.pushCard(new Card(1));
        TestPlayer.pushCard(new Card(2));
        TestPlayer.pushCard(new Card(3));
        TestPlayer.pushCard(new Card(4));
        assertEquals("1 2 3 4 ", TestPlayer.createPrintableHand());
    }

    @Test
    void createPrintableHand() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);

        // Test that the printable hand is the correct representation of the current hand
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck);
        TestPlayer.pushCard(new Card(1));
        TestPlayer.pushCard(new Card(2));
        TestPlayer.pushCard(new Card(3));
        TestPlayer.pushCard(new Card(4));
        assertEquals("1 2 3 4 ", TestPlayer.createPrintableHand());
    }

    @Test
    void writeToFile() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);

        // Test that the write to file is successful
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck);
        TestPlayer.pushCard(new Card(1));
        TestPlayer.pushCard(new Card(2));
        TestPlayer.pushCard(new Card(3));
        TestPlayer.pushCard(new Card(4));
        assertTrue(TestPlayer.writeToFile(1, 2));
    }
}