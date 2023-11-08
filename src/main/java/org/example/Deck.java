package org.example;

import java.util.ArrayDeque;
import java.util.Queue;

public class Deck {
    private final Queue<Card> cards;

    public Deck() {
        cards = new ArrayDeque<>();
    }

    public void PushCard(Card card) {
        cards.add(card);
    }

    public Card GetNextCard() {
        return cards.remove();
    }
}
