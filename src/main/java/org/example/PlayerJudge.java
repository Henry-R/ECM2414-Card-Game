package org.example;

public class PlayerJudge {
    int winningTurn;
    int winningPlayer;

    public PlayerJudge() {
        winningTurn = 0;
        winningPlayer = -1;
    }

    public synchronized void newWinner(int playerID, int turn) {
        if (turn < winningTurn || !playerHasWon()) {
            winningTurn = turn;
            winningPlayer = playerID;
        }
    }
    public synchronized boolean playerHasWon() {
        return winningPlayer != -1;
    }
    public synchronized int getWinningTurn() {
        return winningTurn;
    }
    public synchronized int getWinningPlayer() {
        return winningPlayer;
    }
}
