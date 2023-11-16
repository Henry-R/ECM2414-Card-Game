package player;

import player.card.Card;
import player.card.Deck;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Player implements Runnable {
    // The unique number used to identify players in output logs
    // Also doubles as this player's preferred denomination according to the player strategy
    private final int playerNumber;
    // The deck the player draws cards from
    private final Deck inputDeck;
    // The deck the player discards cards to
    private final Deck outputDeck;
    // Used as a shared memory to communicate between players if they should stop
    private final PlayerJudge judge;
    // All the cards this player will not keep indefinitely
    private final PriorityQueue<Card> hand;
    // The player's output which describes all their moves
    private final StringBuilder fileOutput;


    /**
     * Constructs the Player from a given input deck, output deck, and judge instance
     * @param playerNumber Preferred denomination of cards for the player
     * @param inputDeck Deck the player to draw cards from
     * @param outputDeck Deck the player to discard cards to
     * @param judge Shared memory that allows players to communicate when a player has won
     */
    public Player(int playerNumber, Deck inputDeck, Deck outputDeck, PlayerJudge judge) {
        this.playerNumber = playerNumber;
        this.inputDeck = inputDeck;
        this.outputDeck = outputDeck;
        this.judge = judge;

        // This comparator always puts a preferred card last
        final Comparator<Card> preferredCardComparator = (v1, v2) -> {
            if (v1.getDenomination() == playerNumber) return 1;
            if (v2.getDenomination() == playerNumber) return -1;
            // If neither card is preferred, use regular integer comparison
            else return v1.getDenomination() - v2.getDenomination();
        };
        hand = new PriorityQueue<>(4, preferredCardComparator);

        fileOutput = new StringBuilder();
    }

    /**
     * Checks if the hand only contains cards with the same denomination
     * @return True if hand's cards have same denomination, false otherwise
     */
    private boolean allCardsSame() {
        // Get all the card denominations
        var denominations = hand.stream().map(Card::getDenomination);
        // Check if set has size 1, implies all cards have same denomination
        var denominationSet = denominations.collect(Collectors.toSet());
        // Preferred cards will never be in the hand, as the hand is reserved for cards that might be
        // discarded, so if preferredCount > 0, the deck contains cards with different denominations
        return denominationSet.size() == 1;
    }

    /**
     * A player has won either if their hand consists entirely of cards with the same denomination,
     * or if they have four cards which are preferred
     * @return True if the player has met the conditions for winning, false otherwise
     */
    private boolean hasWon() {
        return allCardsSame();
    }

    /**
     * Pushes a card into this player's hand
     * @param newCard the card that will be pushed into this player's hand
     */
    public void pushCard(Card newCard) {
         hand.add(newCard);
    }

    /**
     * Draws card from input deck, pushes it to this player's hand, and returns it as an object
     * @return The card that was drawn
     */
    private Card drawCard() throws InterruptedException {
        var newCard = inputDeck.dealNextCard();
        pushCard(newCard);
        return newCard;
    }

    /**
     * Discards card from hand to the output deck
     * @return The card that was discarded
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
        for(Card c : hand) { 
            printableHand.append(c.getDenomination()).append(" ");
        }
        return printableHand.toString();
    }

    /**
     * Writes current play to text file
     * @param drawnCard newly drawn card denomination
     * @param discardedCard newly discarded card denomination
     */
    private String getPlayString(int drawnCard, int discardedCard) {
        return "player " + playerNumber + " draws a " + drawnCard + " from deck " + inputDeck.getDeckNumber() +
                "\nplayer " + playerNumber + " discards a " + discardedCard + " to deck " + outputDeck.getDeckNumber() +
                "\nplayer " + playerNumber + " current hand is " + getHandString() + "\n";
    }

    /**
     * Outputs all of this player's play history to a file in the root directory called
     * "player{player_number}_output.txt"
     */
    public void printPlayHistory() {
        var out = new TextFile("player" + playerNumber + "_output.txt");
        out.write(fileOutput.toString());
    }

    /**
     * Plays a single round by drawing and discarding a card
     * @throws InterruptedException if interrupted while waiting either to draw or discard a card
     */
    private void playRound()
    throws InterruptedException {
        var newCard = drawCard();
        var oldCard = discardCard();

        var printablePlay = getPlayString(
                newCard.getDenomination(),
                oldCard.getDenomination());
        fileOutput.append(printablePlay);
    }

    /**
     * The main game loop. Loops until either this player wins, or the judge declares another player has already won
     */
    @Override
    public void run() {
        int currentTurn = 1;
        // Until thread exits (either because of an interrupt or because player has won)
        while (true) {
            if (judge.playerHasWon() && judge.getWinningTurn() < currentTurn) {
                // Taken more turns than the winner, so this player will never do better
                // Exit thread
                return;
            }
            try {
                playRound();
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
}
