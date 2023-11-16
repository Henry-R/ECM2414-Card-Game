package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import player.Player;
import player.PlayerJudge;
import player.card.Card;
import player.card.Deck;

import java.io.File;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerConstructor() {
        // No public methods to test constructor was successful.
        // Test constructor through the other methods succeeding
    }

    @Test
    void pushCard() {
        var testPlayer1 = new Player(0, null, null, null);
        var testPlayer2 = new Player(0, null, null, null);

        // Setup state
        // Test one card
        testPlayer1.pushCard(new Card(1));
        assertEquals("1 ", testPlayer1.getHandString());
        // Test with full deck
        testPlayer1.pushCard(new Card(2));
        testPlayer1.pushCard(new Card(3));
        testPlayer1.pushCard(new Card(4));
        assertEquals("1 2 3 4 ", testPlayer1.getHandString());

        // Test with preferred card
        // (preferred cards are treated differently by the Player class, so test them separately)
        // Test with one card
        testPlayer2.pushCard(new Card(0));
        assertEquals("0 ", testPlayer2.getHandString());
        // Test with full deck
        testPlayer2.pushCard(new Card(0));
        testPlayer2.pushCard(new Card(0));
        testPlayer2.pushCard(new Card(0));
        assertEquals("0 0 0 0 ", testPlayer2.getHandString());
    }

    @Test
    void printPlayHistory() {
        var inDeck = new Deck(1);
        var outDeck = new Deck(2);
        var judge = new PlayerJudge();
        var player = new Player(0, inDeck, outDeck, judge);

        assertDoesNotThrow(() -> {
            inDeck.pushCard(new Card(0));
            outDeck.pushCard(new Card(1));
        });
        // Set winner so player only plays one move
        judge.newWinner(0, 1);
        player.pushCard(new Card(2));
        player.run();

        // Creates a file called "player0_output.txt" containing the play
        player.printPlayHistory();
        assertDoesNotThrow(() -> {
            StringBuilder output = new StringBuilder();
            File file = new File("player0_output.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                output.append(scanner.nextLine()).append("\n");
            }
            assertEquals("""
                    player 0 draws a 0 from deck 1
                    player 0 discards a 2 to deck 2
                    player 0 current hand is 0\s
                    """, output.toString());
        });
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void run() {
        // Test the game playing strategy
        var inDeck = new Deck(1);
        var outDeck = new Deck(2);
        var judge = new PlayerJudge();
        var player = new Player(0, inDeck, outDeck, judge);
        var playerThread = new Thread(player);

        // Keep out deck empty, so it's easier to analyze what the player discarded
        // Initialize hand
        player.pushCard(new Card(0));
        player.pushCard(new Card(1));
        player.pushCard(new Card(2));
        player.pushCard(new Card(3));

        playerThread.start();

        assertDoesNotThrow(() -> {
            // Initialize in-deck after game starts (player should win after drawing all the 0s)
            inDeck.pushCard(new Card(0));
            inDeck.pushCard(new Card(4));
            inDeck.pushCard(new Card(0));
            inDeck.pushCard(new Card(5));
            inDeck.pushCard(new Card(6));
            inDeck.pushCard(new Card(0));
            inDeck.pushCard(new Card(7));

            // Player wins after discarding only the following cards
            assertEquals(1, outDeck.dealNextCard().getDenomination());
            assertEquals(2, outDeck.dealNextCard().getDenomination());
            assertEquals(3, outDeck.dealNextCard().getDenomination());
            assertEquals(4, outDeck.dealNextCard().getDenomination());
            assertEquals(5, outDeck.dealNextCard().getDenomination());
            assertEquals(6, outDeck.dealNextCard().getDenomination());
            // No more cards in discard pile
            assertTrue(outDeck.isEmpty());

            // Check in-deck has one card remaining
            assertFalse(inDeck.isEmpty());
            inDeck.dealNextCard();
            assertTrue(inDeck.isEmpty());

            playerThread.join();
        });
        // Player wins in six moves exactly
        assertEquals(0, judge.getWinningPlayer());
        assertEquals(6, judge.getWinningTurn());
    }
}