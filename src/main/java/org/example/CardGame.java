package org.example;

import org.example.Exceptions.InsufficientCardException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardGame
{
    private static int getValidPlayerCount() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        return in.nextInt();
    }

    private static String getValidPackLocation() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        return in.nextLine();
    }

    private static ArrayList<Player> getPlayersFromPack(int player_count, String packURL)
            throws InsufficientCardException, FileNotFoundException {
        final int PLAYER_HAND_SIZE = 4;
        final int DECK_SIZE = 4;
        final int TOTAL_SIZE = PLAYER_HAND_SIZE + DECK_SIZE;
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Deck>   decks   = new ArrayList<>();

        // Create players and decks in round-robin topology
        for (int i = 0; i < player_count; i++) {
            decks.add(new Deck(i + 1));
        }
        for (int i = 0; i < player_count; i++) {
            Deck in_deck =  decks.get(i);
            // The modulus creates the round-robin topology
            Deck out_deck = decks.get((i + 1) % player_count);
            players.add(new Player(i + 1, in_deck, out_deck));
        }

        // Deal cards to newly constructed players and decks
        var initialCards = new CardReader(packURL);
        if (initialCards.getCardCount() < player_count * TOTAL_SIZE) {
            throw new InsufficientCardException("Not enough cards in " + packURL +
                    "! Expected " + TOTAL_SIZE + " but got " + initialCards.getCardCount());
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
        return players;
    }

    private static int playRound(List<Player> players) {
        // Every player makes a play
        for (var player : players) {
            player.play();
        }
        // Check if any players have won
        for (var player : players) {
            // TODO maybe rename Player.preCheck to Player.hasWon to be clearer?
            if (player.hasWon()) {
                return player.getPlayerNumber();
            }
        }
        // No one has won yet
        return 0;
    }

    public static void main( String[] args )
    {
        var player_count = 4;//getValidPlayerCount();
        var pack_location = "packs/one.txt";//getValidPackLocation();
        try {
            ArrayList<Player> players = getPlayersFromPack(player_count, pack_location);

            // Main game loop
            int winner = 0;
            while (winner == 0) {
                // If someone has won
                winner = playRound(players);
                if (winner > 0) {
                    System.out.println("Player " + winner + " wins");
                }
            }
        }
        catch (FileNotFoundException | InsufficientCardException e) {
            System.out.println(e.getMessage());
        }
    }
}
