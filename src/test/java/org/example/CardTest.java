package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void getDenomination() {
        var TestCard = new Card(5);
        assertEquals(5, TestCard.GetDenomination());
        assertNotEquals(4, TestCard.GetDenomination());
    }
}