package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerConstructor() {
        var testInputDeck = new Deck(1);
        var testOutputDeck = new Deck(2);
        Queue<Card> testHand = new LinkedList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(1));
        testHand.add(new Card(1));
        testHand.add(new Card(1));
        
        var TestPlayer = new Player(1, testInputDeck, testOutputDeck, testHand);
    }

}