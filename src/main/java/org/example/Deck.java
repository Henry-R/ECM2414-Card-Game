package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class Deck {
    private final Queue<Card> cards;

    public Deck() {
        cards = new ConcurrentLinkedQueue<>();
    }

    /**
     * Pushes the given card to the bottom of the deck
     * @param card The card that will be put in the deck
     */
    public void PushCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns and removes the top card in the deck
     * @return Card object or null if Deck is empty
     */
    public Card DealNextCard() {
        return cards.poll();
    }
}
