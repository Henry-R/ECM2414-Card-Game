package player.card;

public class Card {
    private final int denomination;

    /**
     * Constructs the card with a given denominator
     * @param d The denominator of the card
     */
    public Card(int d) {
        denomination = d;
    }

    /**
     * Returns the denomination (value) of the card
     * @return the card's denomination
     */
    public int getDenomination() {
        return denomination;
    }
}
