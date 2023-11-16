package player.card;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Reads cards from a text file and provides methods for safely retrieving these cards
 */
public class CardReader {
    private ArrayDeque<Card> cards;
    private boolean readSuccessful;

    /**
     * Reads an ordered list of cards from a file in a safe way
     * Success parameter true if file loads correctly and false otherwise
     * @param filename The file containing an ordered list of cards
     */
    public CardReader(String filename) {
        try {
            var input  = new File(filename);
            var reader = new Scanner(input);
            cards = getCardsFromFile(reader);
            readSuccessful = true;
        } catch (FileNotFoundException e) {
            // No cards if file not found
            cards = new ArrayDeque<>();
            readSuccessful = false;
        }
    }

    /**
     * Loads a list of cards from a specified file.
     * Each card must be on a new line.
     * If a value on a line is not a positive integer, it is ignored.
     * @param reader The file reader used to read the file
     * @return A list of Card objects
     */
    private ArrayDeque<Card> getCardsFromFile(Scanner reader) {
        var cards = new ArrayDeque<Card>();
        while (reader.hasNextLine()) {
            var cardStr = reader.nextLine();
            try {
                var denominator = Integer.parseInt(cardStr);
                if (denominator > 0) {
                    cards.add(new Card(denominator));
                }
            } catch (NumberFormatException e) {
                // Ignore malformed cards
            }
        }
        return cards;
    }

    public boolean success() {
        return readSuccessful;
    }

    public Collection<Card> getCards() {
        return cards;
    }

    public int getCardCount() { return cards.size(); }

    public Card nextCard() {
        return cards.pop();
    }
}
