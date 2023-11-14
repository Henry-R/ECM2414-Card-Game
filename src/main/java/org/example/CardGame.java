package org.example;

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

    private static ArrayList<Player> getPlayersFromPack(int player_count, String packURL) {
        final int PLAYER_HAND_SIZE = 4;
        final int DECK_SIZE = 4;
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
        if (!initialCards.success()) {
            System.out.println("Failed to read cards from " + packURL + " file!");
        }
        else if (initialCards.getCardCount() < player_count * (PLAYER_HAND_SIZE + DECK_SIZE)) {
            System.out.println("Not enough cards in " + packURL + "!");
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

    private static void playRound(List<Player> players) {
        // Every player makes a play
        for (var player : players) {
            player.play();
        }
        // Check if any players have won

    }

    public static void main( String[] args )
    {
        // The number of players, n
        int player_count;
        // The file URL of the initial card pack
        String pack_location;
        ArrayList<Player> players;

        player_count = 4;//getValidPlayerCount();
        pack_location = "packs/one.txt";//getValidPackLocation();

        players = getPlayersFromPack(player_count, pack_location);

        // Main game loop
        playRound(players);

        System.out.println("Hello, world!");
    }
}
