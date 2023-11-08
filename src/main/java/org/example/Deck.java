package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class Deck {
    private final Queue<Card> cards;

    public Deck() {
        cards = new ConcurrentLinkedQueue<>();
    }

    public void PushCard(Card card) {
        cards.add(card);
    }

    public Card GetNextCard() {
        return cards.remove();
    }
}
