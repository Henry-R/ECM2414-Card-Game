import player.exceptions.InsufficientCardException;
import player.PlayerHandler;

import java.io.FileNotFoundException;
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

    public static void main( String[] args )
    {
        var player_count = getValidPlayerCount();
        var pack_location = getValidPackLocation();
        try {
            var playerHandler = new PlayerHandler(player_count, pack_location);
            int winner = playerHandler.playGame();
            System.out.println("Player " + winner + " wins!");
        }
        catch (FileNotFoundException | InsufficientCardException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
