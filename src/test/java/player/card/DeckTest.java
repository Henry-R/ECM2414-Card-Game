package player.card;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckConstructor() throws InterruptedException {
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
    void testPushDealCards() {
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
    void testPrintDeckState() {
        // Initialize deck state
        var deck = new Deck(0);
        assertDoesNotThrow(() -> {
            deck.pushCard(new Card(1));
            deck.pushCard(new Card(2));
            deck.pushCard(new Card(3));
            deck.pushCard(new Card(4));
        });

        deck.printDeckState();
        assertDoesNotThrow(() -> {
            // Read deck contents that was outputted to file
            StringBuilder output = new StringBuilder();
            File file = new File("deck0_output.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                output.append(scanner.nextLine()).append("\n");
            }
            // Ensure that the output is as expected
            assertEquals("""
                    deck0 contents: 1 2 3 4\s
                    """, output.toString());
        });
    }

    @Test
    void testGetDeckNumber() {
        var TestDeck = new Deck(1);
        assertEquals(1, TestDeck.getDeckNumber());
    }
}