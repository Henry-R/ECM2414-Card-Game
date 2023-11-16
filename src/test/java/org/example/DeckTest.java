package org.example;

import org.junit.jupiter.api.Timeout;
import player.card.Card;
import player.card.Deck;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void deckConstructor() throws InterruptedException {
        // Test cards are all in deck in the correct order
        var TestDeck2 = new Deck(2);
        TestDeck2.pushCard(new Card(1));
        TestDeck2.pushCard(new Card(2));
        TestDeck2.pushCard(new Card(3));
        assertEquals(1, TestDeck2.dealNextCard().getDenomination());
        assertEquals(2, TestDeck2.dealNextCard().getDenomination());
        assertEquals(3, TestDeck2.dealNextCard().getDenomination());
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void pushDealCards() {
        final Deck testDeck = new Deck(0);
        // Test that the deck works when reading and writing to it from multiple threads
        var t = new Thread(() -> assertDoesNotThrow(() -> {
            testDeck.pushCard(new Card(1));
            testDeck.pushCard(new Card(2));
            testDeck.pushCard(new Card(3));
        }));
        t.start();
        assertDoesNotThrow(() -> {
            assertEquals(1, testDeck.dealNextCard().getDenomination());
            assertEquals(2, testDeck.dealNextCard().getDenomination());
            assertEquals(3, testDeck.dealNextCard().getDenomination());
            t.join();
        });
    }

    @Test
    void getDeckNumber() {
        var TestDeck = new Deck(1);
        assertEquals(1, TestDeck.getDeckNumber());
    }
}