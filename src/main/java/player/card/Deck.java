package player.card;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Deck {
    // No need to use synchronised methods as BlockingQueue is already thread-safe
    private final BlockingQueue<Card> cards;
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
     * Pushes the given card to the bottom of the deck
     * @param card The card that will be put in the deck
     */
    public void pushCard(Card card) throws InterruptedException {
        cards.put(card);
    }

    /**
     * Returns and removes the top card in the deck
     * @return Card object or null if Deck is empty
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
}
