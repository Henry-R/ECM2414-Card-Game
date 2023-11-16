package player;

import player.card.CardReader;
import player.card.Deck;
import player.exceptions.InsufficientCardException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHandler {

    private final PlayerJudge judge;
    private final List<Player> players;

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
     *
     * @return the playerID of the winning player
     */
    public int playGame()
    throws InterruptedException {
        var playerThreads = new ArrayList<Thread>();
        for (var player : players) {
            var thread = new Thread(player);
            playerThreads.add(thread);
            thread.start();
        }
        for (var thread : playerThreads) {
            thread.join();
        }

        for (var player : players) {
            player.printPlayHistory();
        }

        return judge.getWinningPlayer();
    }
}
