package player;

import player.card.Card;
import player.card.Deck;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Player implements Runnable {
    private final int playerNumber;
    private final Deck inputDeck;
    private final Deck outputDeck;
    private final PlayerJudge judge;
    private final Queue<Card> hand;
    private final StringBuilder fileOutput;
    private int preferredCount;


    /**
     * Constructs the Player with a given input deck, output deck and initial hand
     * @param pd The preferred denomination of cards for the player
     * @param id The deck for the player to draw cards from
     * @param od The deck for the player to discard cards to
     */
    public Player(int pd, Deck id, Deck od, PlayerJudge j) {
        playerNumber = pd;
        inputDeck = id;
        outputDeck = od;
        judge = j;
        hand = new ArrayDeque<>();
        fileOutput = new StringBuilder();
        preferredCount = 0;
    }

    /**
     * Checks if the hand contains 4 of the same value cards
     * @return boolean of whether the player has won immediately or not
     */
    private boolean allCardsSame() {
        // Get all the card denominations
        var denominations = hand.stream().map((Card c) -> c.getDenomination());
        // Check if set has size 1, implies all cards have same denomination
        var denominationSet = denominations.collect(Collectors.toSet());
        return denominationSet.size() == 1;
    }

    private boolean hasWon() {
        return preferredCount == 4 || allCardsSame();
    }

    public void pushCard(Card newCard) {
        if (newCard.getDenomination() == playerNumber) {
            preferredCount++;
        } else {
            hand.add(newCard);
        }
    }

    /**
     * Draws card from input deck
     * @return newly drawn card
     */
    private Card drawCard() throws InterruptedException {
        var newCard = inputDeck.dealNextCard();
        pushCard(newCard);
        return newCard;
    }

    /**
     * Discards card from hand and adds to output deck
     * @return newly discarded card
     */
    private Card discardCard() throws InterruptedException {
        Card discardedCard = hand.poll();
        outputDeck.pushCard(discardedCard);
        return discardedCard;
    }

    /**
     * Creates printable representation of the players current hand,
     * including the preferred values that are not in the hand queue
     * @return string of a representation of the denomination of the cards in the players hand
     */
    public String getHandString() {
        StringBuilder printableHand = new StringBuilder();
        printableHand.append((playerNumber + " ").repeat(preferredCount));
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
    private String getPlayString(int nCard, int oCard) {
        return "player " + playerNumber + " draws a " + nCard + " from deck " + inputDeck.getDeckNumber() +
                "\nplayer " + playerNumber + " discards a " + oCard + " to deck " + outputDeck.getDeckNumber() +
                "\nplayer " + playerNumber + " current hand is " + getHandString() + "\n";
    }

    /** 
     * Does a single play for the player, draws new card,
     * discards a card, checks if player has won.
    */
    private void play() {
        int currentTurn = 1;
        // Until thread exits (either because of an interrupt or because player has won)
        while (true) {
            if (judge.playerHasWon() && judge.getWinningTurn() < currentTurn) {
                // Taken more turns than the winner, so we'll never do better
                // Exit thread
                return;
            }

            try {
                var newCard = drawCard();
                var oldCard = discardCard();

                var printablePlay = getPlayString(
                        newCard.getDenomination(),
                        oldCard.getDenomination());
                fileOutput.append(printablePlay);
            } catch (InterruptedException e) {
                // Exit thread
                return;
            }
            if (hasWon()) {
                // Notify other players this player has won
                judge.newWinner(playerNumber, currentTurn);
                // Exit thread
                return;
            }
            currentTurn++;
        }
    }

    public void printPlayHistory() {
        var out = new TextFile("player" + playerNumber + "_output.txt");
        out.write(fileOutput.toString());
    }

    @Override
    public void run() {
        play();
    }
}
