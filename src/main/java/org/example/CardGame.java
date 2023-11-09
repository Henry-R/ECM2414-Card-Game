package org.example;

public class CardGame
{
    public static void main( String[] args )
    {
        var card = new Card(5);
        var deck = new Deck(1);
        var player = new Player(1, deck, deck, null);

        System.out.println("Hello, world!");
        System.out.println("Card: " + card + ", Player: " + player);
    }
}
