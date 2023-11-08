package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void pushCard() {
        var TestDeck = new Deck();
        TestDeck.PushCard(new Card(1));
        assertEquals(1, TestDeck.DealNextCard().GetDenomination());
        TestDeck.PushCard(new Card(2));
        TestDeck.PushCard(new Card(3));
        assertEquals(2, TestDeck.DealNextCard().GetDenomination());
    }

    @Test
    void getNextCard() {
        var TestDeck = new Deck();
        TestDeck.PushCard(new Card(1));
        TestDeck.PushCard(new Card(2));
        TestDeck.PushCard(new Card(3));
        assertEquals(1, TestDeck.DealNextCard().GetDenomination());
        assertEquals(2, TestDeck.DealNextCard().GetDenomination());
        assertEquals(3, TestDeck.DealNextCard().GetDenomination());
        assertNull(TestDeck.DealNextCard());
    }
}