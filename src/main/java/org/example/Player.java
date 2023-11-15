package org.example;
import java.util.ArrayDeque;
import java.util.Queue;

public class Player {
    private final int playerNumber;
    private final Deck inputDeck;
    private final Deck outputDeck;
    private final Queue<Card> hand;
    private int preferredCount;
    final TextFile textFile;

    /**
     * Constructs the Player with a given input deck, output deck and initial hand
     * @param pd The preferred denomination of cards for the player
     * @param id The deck for the player to draw cards from
     * @param od The deck for the player to discard cards to
     */
    public Player(int pd, Deck id, Deck od) {
        playerNumber = pd;
        inputDeck = id;
        outputDeck = od;
        hand = new ArrayDeque<>();
        preferredCount = 0;
        textFile = new TextFile("player" +playerNumber+ "_output.txt");
    }

    /**
     * Constructs the Player with a given input deck, output deck and initial hand
     * @param pd The preferred denomination of cards for the player
     * @param id The deck for the player to draw cards from
     * @param od The deck for the player to discard cards to
     * @param h The initial hand of cards for the player
     */
    public Player(int pd, Deck id, Deck od, Queue<Card> h) {
        playerNumber = pd;
        inputDeck = id;
        outputDeck = od;
        hand = h;
        preferredCount = 0;
        textFile = new TextFile("player" +playerNumber+ "_output.txt");
    }

    /**
     * Pushes the given card to the end of the hand queue
     * @param card The card that will be put in the hand
     */
    public void pushCard(Card card) {
        hand.add(card);
    }

    /**
     * Checks if the hand contains 4 of the same value cards
     * @return boolean of whether the player has won immediately or not
     */
    public boolean preCheck() {
        // Check all four cards are the same
        assert hand.peek() != null;
        int n = hand.peek().getDenomination();
        for (Card c : hand) {
            if (c.getDenomination() != n) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all the preferred cards from the initial hand, so
     * they will not be discarded
     */
    public void removePreferred() {
        for (var it = hand.iterator(); it.hasNext();) {
            if (it.next().getDenomination() == playerNumber) {
                preferredCount++;
                it.remove();
            }
        }
    }

    /**
     * Draws card from input deck
     * @return newly drawn card
     */
    public Card drawCard() throws InterruptedException {
        return inputDeck.dealNextCard();
    }

    /**
     * Discards card from hand and adds to output deck
     * @return newly discarded card's denomination
     */
    public int discardCard() throws InterruptedException {
        Card discardedCard = hand.remove();
        outputDeck.pushCard(discardedCard);
        return discardedCard.getDenomination();
    }

    /**
     * Creates printable representation of the players current hand,
     * including the preferred values that are not in the hand queue
     * @return string of a representation of the denomination of the cards in the players hand
     */
    public String createPrintableHand() {
        StringBuilder printableHand = new StringBuilder();
        printableHand.append("1 ".repeat(preferredCount));
        for(Card c : hand) { 
            printableHand.append(c.getDenomination()).append(" ");
        }
        return printableHand.toString();
    }

    /**
     * Writes current play to text file
     * @param nCard newly drawn card denomination
     * @param oCard newly discarded card denomination
     */
    public boolean writeToFile(int nCard, int oCard) {
        String currentPlay = "player " +playerNumber+ " draws a " +nCard+ " from deck " + inputDeck.getDeckNumber()+
                            "\nplayer " +playerNumber+ " discards a " +oCard+ " to deck " + outputDeck.getDeckNumber()+
                            "\nplayer" +playerNumber+ " current hand is " +this.createPrintableHand();
        return textFile.write(currentPlay);
    }

    /** 
     * Does a single play for the player, draws new card,
     * discards a card, checks if player has won.
     * @return whether the player has now won after this play
    */
    public boolean play() {
        if (this.preCheck()) {
            return true;
        } else {
            removePreferred();
        }

        try {
            Card newCard = drawCard();
            int newCardDenomination = newCard.getDenomination();

            if (newCardDenomination == playerNumber) {
                preferredCount++;
            } else {
                this.pushCard(newCard);
            }

            int oldCardDenomination = discardCard();
            this.writeToFile(newCardDenomination, oldCardDenomination);
            if (preferredCount == 4) {
                return true;
            }
        } catch (InterruptedException e) {
            // Interrupted, didn't win
            return false;
        }
    
        return false;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
