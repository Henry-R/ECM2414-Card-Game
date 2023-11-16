package player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PlayerJudgeTest {

    @Test
    void newWinner() {
        var judge = new PlayerJudge();
        judge.newWinner(0, 10);
        assertEquals(0, judge.getWinningPlayer());
        assertEquals(10, judge.getWinningTurn());
    }

    @Test
    void playerHasWon() {
        var judge = new PlayerJudge();
        judge.newWinner(0, 10);
        assertTrue(judge.playerHasWon());
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void judgeAsync() {
        var judge = new PlayerJudge();
        var threads = new ArrayList<Thread>();

        // List of threads that declares a winner to judge
        // Player 4 is the winner as they have the lowest turn number of 1
        threads.add(new Thread(() -> judge.newWinner(0, 5)));
        threads.add(new Thread(() -> judge.newWinner(1, 4)));
        threads.add(new Thread(() -> judge.newWinner(2, 3)));
        threads.add(new Thread(() -> judge.newWinner(3, 2)));
        threads.add(new Thread(() -> judge.newWinner(4, 1)));
        threads.add(new Thread(() -> judge.newWinner(5, 2)));
        threads.add(new Thread(() -> judge.newWinner(6, 3)));
        threads.add(new Thread(() -> judge.newWinner(7, 4)));
        threads.add(new Thread(() -> judge.newWinner(8, 5)));
        threads.add(new Thread(() -> judge.newWinner(9, 6)));

        threads.forEach(Thread::start);
        // Join all threads asserting none of the threads are interrupted
        threads.forEach((var t) -> assertDoesNotThrow(() -> t.join()));

        assertEquals(4, judge.getWinningPlayer());
        assertEquals(1, judge.getWinningTurn());
        assertTrue(judge.playerHasWon());
    }
}