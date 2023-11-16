package player;

/**
 * Acts as shared memory for Player objects. Player objects use this class to communicate when a player has won, and
 * how many turns it took them. This allows the Player objects to stop if they know they cannot win
 */
public class PlayerJudge {
    // Turn the current winner won on
    int winningTurn;
    // Player number of the currently winning player
    int winningPlayer;

    /**
     * Constructs an instance of PlayerJudge class where no players have already won
     */
    public PlayerJudge() {
        winningTurn = -1;
        winningPlayer = -1;
    }

    /**
     * Declares a new winner. If the new winner has won sooner than the old winner, update to the new winner
     * @param playerID Player number of the new winner
     * @param turn Turn the winner won on
     */
    public synchronized void newWinner(int playerID, int turn) {
        if (turn < winningTurn || !playerHasWon()) {
            winningTurn = turn;
            winningPlayer = playerID;
        }
    }

    /**
     * @return True is a player has won, false otherwise
     */
    public synchronized boolean playerHasWon() {
        return winningPlayer != -1;
    }

    /**
     * @return The turn the currently winning player won on, -1 if no player has won yet
     */
    public synchronized int getWinningTurn() {
        return winningTurn;
    }

    /**
     * @return Player number of the currently winning player, -1 if no player has won yet
     */
    public synchronized int getWinningPlayer() {
        return winningPlayer;
    }
}
