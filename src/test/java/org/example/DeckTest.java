package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void pushCard() {
        var TestDeck = new Deck();
        TestDeck.pushCard(new Card(1));
        assertEquals(1, TestDeck.dealNextCard().getDenomination());
        TestDeck.pushCard(new Card(2));
        TestDeck.pushCard(new Card(3));
        assertEquals(2, TestDeck.dealNextCard().getDenomination());
    }

    @Test
    void dealNextCard() {
        var TestDeck = new Deck();
        TestDeck.pushCard(new Card(1));
        TestDeck.pushCard(new Card(2));
        TestDeck.pushCard(new Card(3));
        assertEquals(1, TestDeck.dealNextCard().getDenomination());
        assertEquals(2, TestDeck.dealNextCard().getDenomination());
        assertEquals(3, TestDeck.dealNextCard().getDenomination());
        assertNull(TestDeck.dealNextCard());
    }
}