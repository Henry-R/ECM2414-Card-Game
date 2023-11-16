package player.card;

/**
 * A wrapper for a Card's denomination. Provides a safe read-only abstraction for the denomination integer value
 */
public class Card {
    // Value of this card
    private final int denomination;

    /**
     * Constructs the card with a given denominator
     * @param denomination The denominator of the card
     */
    public Card(int denomination) {
        this.denomination = denomination;
    }

    /**
     * Returns the denomination (value) of the card
     * @return the card's denomination
     */
    public int getDenomination() {
        return denomination;
    }
}
