package org.example;
import java.util.Queue;
import java.util.ArrayDeque;

public class Player {
    private final Deck inputdeck;
    private final Deck outputdeck;
    private Queue<Card> hand;

    /**
     * Constructs the Player with a given input deck, output deck and initial hand
     * @param id The deck for the player to draw cards from
     * @param od The deck for the player to discard cards to
     * @param h The initial hand of cards for the player
     */
    public Player(Deck id, Deck od, Queue<Card> h) {
        inputdeck = id;
        outputdeck = od;
        hand = h;
    }
}
