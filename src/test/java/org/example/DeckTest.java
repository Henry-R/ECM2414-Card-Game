package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

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
    void pushCard()  throws InterruptedException {
        var TestDeck = new Deck(1);
        TestDeck.pushCard(new Card(1));
        assertEquals(1, TestDeck.dealNextCard().getDenomination());
        TestDeck.pushCard(new Card(2));
        TestDeck.pushCard(new Card(3));
        assertEquals(2, TestDeck.dealNextCard().getDenomination());
    }

    @Test
    void dealNextCard()  throws InterruptedException {
        var TestDeck = new Deck(1);
        TestDeck.pushCard(new Card(1));
        TestDeck.pushCard(new Card(2));
        TestDeck.pushCard(new Card(3));
        assertEquals(1, TestDeck.dealNextCard().getDenomination());
        assertEquals(2, TestDeck.dealNextCard().getDenomination());
        assertEquals(3, TestDeck.dealNextCard().getDenomination());
    }

    @Test
    void getDeckNumber() {
        var TestDeck = new Deck(1);
        assertEquals(1, TestDeck.getDeckNumber());
    }
}