package org.example;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class Deck {
    private final Queue<Card> cards;

    /**
     * Constructs an empty deck of cards
     */
    public Deck() {
        cards = new ConcurrentLinkedQueue<>();
    }

    /**
     * Constructs a deck containing a given collection of cards
     * @param initial_cards The cards the deck will be initialized using
     */
    public Deck(Collection<Card> initial_cards) {
        cards = new ConcurrentLinkedQueue<>();
        cards.addAll(initial_cards);
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
}
