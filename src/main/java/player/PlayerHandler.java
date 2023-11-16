package player;

import player.card.CardReader;
import player.card.Deck;
import player.exceptions.InsufficientCardException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for setting up the game and playing it. Game is played synchronously with every player having a
 * different thread
 */
public class PlayerHandler {

    private final PlayerJudge judge;
    private final List<Player> players;

    /**
     * Initializes the game by created and dealing cards to players and decks
     * @param player_count The number of players in the game
     * @param packURL The location of the pack of cards on this PC
     * @throws InsufficientCardException The pack does not have enough cards to deal to every player and deck
     * @throws FileNotFoundException The pack cannot be found on the PC
     * @throws InterruptedException Interrupted while setting up the game
     */
    public PlayerHandler(int player_count, String packURL)
            throws InsufficientCardException, FileNotFoundException, InterruptedException{
        int PLAYER_HAND_SIZE = 4;
        int DECK_SIZE = 4;
        int TOTAL_SIZE = PLAYER_HAND_SIZE + DECK_SIZE;
        judge   = new PlayerJudge();
        players = new ArrayList<>();
        var decks   = new ArrayList<Deck>();

        // Create players and decks in round-robin topology
        for (int i = 0; i < player_count; i++) {
            decks.add(new Deck(i + 1));
        }
        for (int i = 0; i < player_count; i++) {
            Deck in_deck =  decks.get(i);
            // The modulus creates the round-robin topology
            Deck out_deck = decks.get((i + 1) % player_count);
            players.add(new Player(i + 1, in_deck, out_deck, judge));
        }

        // Deal cards to newly constructed players and decks
        var initialCards = new CardReader(packURL);
        if (initialCards.getCardCount() < player_count * TOTAL_SIZE) {
            throw new InsufficientCardException("Not enough cards in " + packURL +
                    "! Expected " + player_count * TOTAL_SIZE + " but got " + initialCards.getCardCount());
        }
        else {
            // First deal to players
            for (int i = 0; i < player_count * PLAYER_HAND_SIZE; i++) {
                var nextCard = initialCards.nextCard();
                players.get(i % player_count).pushCard(nextCard);
            }
            // Then deal to decks
            for (int i = 0; i < player_count * DECK_SIZE; i++) {
                var nextCard = initialCards.nextCard();
                decks.get(i % player_count).pushCard(nextCard);
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

        // Judge keeps track of the winner during the game
        return judge.getWinningPlayer();
    }
}
