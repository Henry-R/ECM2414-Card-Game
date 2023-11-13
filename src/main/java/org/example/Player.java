package org.example;
import java.util.Queue;

public class Player {
    private final int playerNumber;
    private final Deck inputdeck;
    private final Deck outputdeck;
    private Queue<Card> hand;
    private int preferredcount;
    final TextFile textFile;

    /**
     * Constructs the Player with a given input deck, output deck and initial hand
     * @param pd The preferred denomination of cards for the player
     * @param id The deck for the player to draw cards from
     * @param od The deck for the player to discard cards to
     */
    public Player(int pd, Deck id, Deck od) {
        playerNumber = pd;
        inputdeck = id;
        outputdeck = od;
        preferredcount = 0;
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
        inputdeck = id;
        outputdeck = od;
        hand = h;
        preferredcount = 0;
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
        boolean hasWon = true;
        int n = hand.peek().getDenomination();
        for (Card c : hand) {
            if (c.getDenomination() != n) {
                hasWon = false;
            }
        }
        return hasWon;
    }

    /**
     * Removes all the preferred cards from the initial hand so 
     * they will not be discarded
     */
    public void removePreferred() {
        for (Card c : hand) {
            if (c.getDenomination() == playerNumber) {
                preferredcount++;
                hand.remove(c);
            }
        }
    }

    /**
     * Draws card from input deck
     * @return newly drawn card
     */
    public Card drawCard() {
        Card drawnCard = inputdeck.dealNextCard();
        return drawnCard;
    }

    /**
     * Discards card from hand and adds to output deck
     * @return newly discarded card's denomination
     */
    public int discardCard() {
        Card discardedCard = hand.remove();
        outputdeck.pushCard(discardedCard);
        return discardedCard.getDenomination();
    }

    /**
     * Creates printable representation of the players current hand,
     * including the preferred values that are not in the hand queue
     * @return string of a representation of the denomination of the cards in the players hand
     */
    public String createPrintableHand() {
        String printableHand = "";
        for (int i = 0; i < preferredcount; i++) {
            printableHand += "1 ";
        }
        for(Card c : hand) { 
            printableHand += String.valueOf(c.getDenomination()) + " ";
        }
        return printableHand;
    }

    /**
     * Writes current play to text file
     * @param nCard newly drawn card denomination
     * @param oCard newly discarded card denomination
     */
    public boolean writeToFile(int nCard, int oCard) {
        String currentPlay = "player " +playerNumber+ " draws a " +nCard+ " from deck " +inputdeck.getDeckNumber()+ 
                            "\nplayer " +playerNumber+ " discards a " +oCard+ " to deck " +outputdeck.getDeckNumber()+ 
                            "\nplayer" +playerNumber+ " current hand is " +this.createPrintableHand();
        return textFile.write(currentPlay);
    }

    /** 
     * Does a single play for the player, draws new card,
     * discards a card, checks if player has won.
     * @return whether the player has now won after this play
    */
    public boolean play() {
        boolean hasWon = false;
        if (this.preCheck()) {
            hasWon = true;
            return hasWon;
        } else {
            this.removePreferred();
        }

        Card newCard = this.drawCard();
        int newCardDenom = newCard.getDenomination();

        if (newCardDenom == playerNumber) {
            preferredcount ++;
        } else {
            this.pushCard(newCard);
        }

        int oldCardDenom = this.discardCard();
        this.writeToFile(newCardDenom, oldCardDenom);
        if (preferredcount == 4) {
            hasWon = true;
        }
    
        return hasWon;
    }


}
