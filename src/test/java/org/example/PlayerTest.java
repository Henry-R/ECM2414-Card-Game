package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerConstructor() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Test cards in hand are in the correct order
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        assertEquals("1 2 3 4 ", TestPlayer.createPrintableHand());
    }

    @Test
    void createTextFile() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();

        // Test that file has been created
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        assertEquals("player1_output.txt", TestPlayer.createTextFile());
    }

    @Test
    void preCheck() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(2));
        testHand.add(new Card(2));
        testHand.add(new Card(2));
        testHand.add(new Card(2));

        //Test player has won if all cards have same value
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        assertEquals(true, TestPlayer.preCheck());
    }

    @Test
    void removePreferred() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Test preferred card has been removed from the hand
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        TestPlayer.removePreferred();
        assertEquals(2, TestPlayer.discardCard());
    }

    @Test
    void drawCard() {
        var cardArray = new Card[]{new Card(1), new Card(2), new Card(3)};
        Collection<Card> CardCollection = Arrays.asList(cardArray);
        var testInputDeck = new Deck(CardCollection, 1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();

        // Test that the new card is the first card in deck 1
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        assertEquals(1, TestPlayer.drawCard().getDenomination());
    }

    @Test
    void discardCard() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Test that card at front of queue has been discarded, 
        // and that this card has been added to the output deck
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        assertEquals(1, TestPlayer.discardCard());
        assertEquals(1, testOutputDeck.dealNextCard().getDenomination());
    }

    @Test
    void createPrintableHand() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Test that the printable hand is the correct representation of the current hand
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
        assertEquals("1 2 3 4 ", TestPlayer.createPrintableHand());
    }

    @Test
    void writeToFile() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Test that the printable hand is the correct representation of the current hand
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);

        //TODO: finish this test, maybe do a writeToFile and then read to check
    }

    @Test
    void play() {
        var cardArray = new Card[]{new Card(1), new Card(2), new Card(3)};
        Collection<Card> CardCollection = Arrays.asList(cardArray);
        var testInputDeck = new Deck(CardCollection, 1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand1 = new LinkedList<>();
        testHand1.add(new Card(1));
        testHand1.add(new Card(1));
        testHand1.add(new Card(1));
        testHand1.add(new Card(1));

        // Test that the player wins if they start with all the same cards
        var TestPlayer1 = new Player(1, testInputDeck, testOutputDeck, testHand1);
        assertEquals(true, TestPlayer1.play());

        // Test that the player wins if they have the same value cards after discarding and drawing a card
        Queue<Card> testHand2 = new LinkedList<>();
        testHand2.add(new Card(2));
        testHand2.add(new Card(1));
        testHand2.add(new Card(1));
        testHand2.add(new Card(1));
        var TestPlayer2 = new Player(1, testInputDeck, testOutputDeck, testHand2);
        assertEquals(true, TestPlayer2.play());
        assertEquals(2, testOutputDeck.dealNextCard().getDenomination());

        // Test that the player doesn't win if the have different value cards after discarding and drawing a card
        Queue<Card> testHand3 = new LinkedList<>();
        testHand3.add(new Card(2));
        testHand3.add(new Card(3));
        testHand3.add(new Card(1));
        testHand3.add(new Card(1));
        var TestPlayer3 = new Player(1, testInputDeck, testOutputDeck, testHand3);
        assertEquals(false, TestPlayer2.play());
        assertEquals(2, testOutputDeck.dealNextCard().getDenomination());
    }
}