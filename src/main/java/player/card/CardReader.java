package player.card;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Reads cards from a text file and provides methods for safely retrieving these cards
 */
public class CardReader {
    private final ArrayDeque<Card> cards;

    /**
     * Reads an ordered list of cards from a file in a safe way
     * @param filename The file containing an ordered list of cards, with each card on a new line
     * @throws FileNotFoundException The specified file cannot be found on this PC
     */
    public CardReader(String filename)
    throws FileNotFoundException {
        var input  = new File(filename);
        var reader = new Scanner(input);
        cards = getCardsFromFile(reader);
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
                // Cards must be positive integer
                if (denominator > 0) {
                    cards.add(new Card(denominator));
                }
            } catch (NumberFormatException e) {
                // Ignore malformed cards
            }
        }
        return cards;
    }

    /**
     * @return Total number of cards read from file
     */
    public int getCardCount() { return cards.size(); }

    /**
     * Returns and removes the next card in the list of cards read from the file
     * @return The next card from the file
     */
    public Card nextCard() {
        return cards.pop();
    }
}
