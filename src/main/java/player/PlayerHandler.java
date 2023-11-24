package player;

import player.card.CardReader;
import player.card.Deck;
import player.exceptions.InsufficientCardException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Provides methods for setting up the game and playing it. Game is played synchronously with every player having a
 * different thread
 */
public class PlayerHandler {

    private final PlayerJudge judge;
    private final ArrayList<Deck> decks;
    private final ArrayList<Player> players;

    /**
     * Initializes the game by loading and dealing cards to players and decks
     * @param playerCount The number of players in the game
     * @param packURL The location of the pack of cards on this PC
     * @throws InsufficientCardException The pack does not have enough cards to deal to every player and deck
     * @throws FileNotFoundException The pack cannot be found on the PC
     * @throws InterruptedException Interrupted while setting up the game
     */
    public PlayerHandler(int playerCount, String packURL)
            throws InsufficientCardException, FileNotFoundException, InterruptedException {
        judge = new PlayerJudge();
        decks = CreateDecks(playerCount);
        players = CreatePlayers(decks);
        DealCards(players, decks, packURL);
    }

    /**
     * Creates a list of decks with deck number initialized to their index in the list plus one
     * @param playerCount the number of players playing the game. Equal to the number of returned decks
     * @return A list of initialized decks
     */
    ArrayList<Deck> CreateDecks(int playerCount) {
        var decks = new ArrayList<Deck>();
        for (int i = 0; i < playerCount; i++) {
            decks.add(new Deck(i + 1));
        }
        return decks;
    }

    /**
     * Creates a list of initialized players. Each player's player number is their index in the list plus one.
     * Each player is automatically assigned an input and output using round-robin topology as described in the brief.
     * The number of returned players is equal to the number of given decks.
     * @param decks The decks the players will be drawing and discarding from
     * @return A list of initialized players
     */
    ArrayList<Player> CreatePlayers(ArrayList<Deck> decks) {
        int playerCount = decks.size();
        var players = new ArrayList<Player>();
        for (int i = 0; i < playerCount; i++) {
            Deck in_deck =  decks.get(i);
            // The modulus creates the round-robin topology
            Deck out_deck = decks.get((i + 1) % playerCount);
            players.add(new Player(i + 1, in_deck, out_deck, judge));
        }
        return players;
    }

    /**
     *
     * @param players The players some cards will be dealt to
     * @param decks The decks some cards will be dealt to
     * @param packURL The location of the pack of cards in the filesystem
     * @throws InsufficientCardException If the pack of cards does not have
     * enough cards to deal to every player and deck
     * @throws FileNotFoundException If the packURL is not found
     * @throws InterruptedException If this method is interrupted while inputting cards to decks. Should never happen
     */
    void DealCards(ArrayList<Player> players, ArrayList<Deck> decks, String packURL)
            throws InsufficientCardException, FileNotFoundException, InterruptedException {
        int PLAYER_HAND_SIZE = 4;
        int DECK_SIZE = 4;
        int TOTAL_SIZE = PLAYER_HAND_SIZE + DECK_SIZE;
        int playerCount = players.size();

        var initialCards = new CardReader(packURL);
        if (initialCards.getCardCount() < playerCount * TOTAL_SIZE) {
            throw new InsufficientCardException("Not enough cards in " + packURL +
                    "! Expected " + playerCount * TOTAL_SIZE + " but got " + initialCards.getCardCount());
        }
        else {
            // First deal to players
            for (int i = 0; i < playerCount * PLAYER_HAND_SIZE; i++) {
                var nextCard = initialCards.nextCard();
                players.get(i % playerCount).pushCard(nextCard);
            }
            // Then deal to decks
            for (int i = 0; i < playerCount * DECK_SIZE; i++) {
                var nextCard = initialCards.nextCard();
                decks.get(i % playerCount).pushCard(nextCard);
            }
        }
    }

    /**
     * Plays the game from start to finish and determines a winning player
     * @return Player number of the player who won
     * @throws InterruptedException Interrupted during the game
     */
    public int playGame()
    throws InterruptedException {
        var playerThreads = new ArrayList<Thread>();
        // Create and start all the players as parallel threads
        for (var player : players) {
            var thread = new Thread(player);
            playerThreads.add(thread);
            thread.start();
        }
        // Wait for all the players to finish the game
        for (var thread : playerThreads) {
            thread.join();
        }

        // Output all the player play history to file
        for (var player : players) {
            player.printPlayHistory();
        }
        // Output all the deck states to file
        for (var deck : decks) {
            deck.printDeckState();
        }

        // Judge keeps track of the winner during the game
        return judge.getWinningPlayer();
    }
}
