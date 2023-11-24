package player.card;

import player.TextFile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Wrapper for BlockingQueue. Contains a queue of Card objects and methods for safely dealing to and from queue
 */
public class Deck {
    // No need to use synchronised methods as BlockingQueue is already thread-safe
    private final BlockingQueue<Card> cards;
    // Number that identifies this deck
    private final int deckNumber;

    /**
     * Constructs an empty deck of cards
     * @param dn the number of the deck
     */
    public Deck(int dn) {
        cards = new LinkedBlockingQueue<>();
        deckNumber = dn;
    }

    /**
     * Pushes the given card to the bottom of the deck. Might wait if the current deck is full, but this is unlikely
     * @param card The card that will be put in the deck
     */
    public void pushCard(Card card) throws InterruptedException {
        cards.put(card);
    }

    /**
     * Returns and removes the top card in the deck. If the deck is empty, this method waits until
     * the deck is not empty, then returns the next card object
     * @return Card at top of deck
     */
    public Card dealNextCard() throws InterruptedException {
        return cards.take();
    }

    /**
     * Returns the number of the deck
     * @return deck number
     */
    public int getDeckNumber() {
        return deckNumber;
    }

    private String getDeckString() {
        var result = new StringBuilder();
        for (var card : cards) {
            result.append(card.getDenomination())
                    .append(" ");
        }
        return result.toString();
    }

    public void printDeckState() {
        var out = new TextFile("deck" + deckNumber + "_output.txt");
        var deckContents = getDeckString();
        out.write("deck" + deckNumber + " contents: " + deckContents);
    }

    /**
     * @return True if queue empty, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
