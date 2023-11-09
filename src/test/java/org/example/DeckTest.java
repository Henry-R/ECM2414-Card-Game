package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void deckConstructor() {
        var cardArray = new Card[]{new Card(1), new Card(2), new Card(3)};
        Collection<Card> CardCollection = Arrays.asList(cardArray);
        Collection<Card> EmptyCardCollection = new ArrayList<>();

        // Test empty deck is actually empty
        var TestDeck1 = new Deck(1);
        assertNull(TestDeck1.dealNextCard());

        // Test cards are all in deck in the correct order
        var TestDeck2 = new Deck(CardCollection, 2);
        assertEquals(1, TestDeck2.dealNextCard().getDenomination());
        assertEquals(2, TestDeck2.dealNextCard().getDenomination());
        assertEquals(3, TestDeck2.dealNextCard().getDenomination());
        assertNull(TestDeck2.dealNextCard());

        // Test empty list of cards give empty deck
        var TestDeck3 = new Deck(EmptyCardCollection, 3);
        assertNull(TestDeck3.dealNextCard());
    }

    @Test
    void pushCard() {
        var TestDeck = new Deck(1);
        TestDeck.pushCard(new Card(1));
        assertEquals(1, TestDeck.dealNextCard().getDenomination());
        TestDeck.pushCard(new Card(2));
        TestDeck.pushCard(new Card(3));
        assertEquals(2, TestDeck.dealNextCard().getDenomination());
    }

    @Test
    void dealNextCard() {
        var TestDeck = new Deck(1);
        TestDeck.pushCard(new Card(1));
        TestDeck.pushCard(new Card(2));
        TestDeck.pushCard(new Card(3));
        assertEquals(1, TestDeck.dealNextCard().getDenomination());
        assertEquals(2, TestDeck.dealNextCard().getDenomination());
        assertEquals(3, TestDeck.dealNextCard().getDenomination());
        assertNull(TestDeck.dealNextCard());
    }
}