package org.example;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class Deck {
    private final Queue<Card> cards;
    private final int decknumber;

    /**
     * Constructs an empty deck of cards
     * @param dn the number of the deck
     */
    public Deck(int dn) {
        cards = new ConcurrentLinkedQueue<>();
        decknumber = dn;
    }

    /**
     * Constructs a deck containing a given collection of cards
     * @param initial_cards The cards the deck will be initialized using
     * @param dn the number of the deck
     */
    public Deck(Collection<Card> initial_cards, int dn) {
        cards = new ConcurrentLinkedQueue<>();
        cards.addAll(initial_cards);
        decknumber = dn;
    }

    /**
     * Pushes the given card to the bottom of the deck
     * @param card The card that will be put in the deck
     */
    public void pushCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns and removes the top card in the deck
     * @return Card object or null if Deck is empty
     */
    public Card dealNextCard() {
        return cards.poll();
    }

    /**
     * Returns the number of the deck
     * @return deck number
     */
    public int getDeckNumber() {
        return decknumber;
    }
}
