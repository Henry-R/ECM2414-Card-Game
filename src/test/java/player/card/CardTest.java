package player.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void testGetDenomination() {
        var TestCard = new Card(5);
        assertEquals(5, TestCard.getDenomination());
        assertNotEquals(4, TestCard.getDenomination());
    }
}