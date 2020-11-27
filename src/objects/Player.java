package objects;

import java.awt.Color;
import java.util.Arrays;

public class Player {
	private Color colour;
	private boolean playing, found[];
	private Card[] hand;
	
	
	public Player(int numCards, Color colour, boolean playing) {
		Deck deck = new Deck();
		hand = new Card[numCards];
		found = new boolean[numCards];
		for (int i = 0; i < numCards; i++) {
			hand[i] = deck.getCard();
		}
		this.colour = colour;
		this.playing = playing;
	}

	public boolean[] getFound() {
		return found;
	}
	public void setFound(boolean[] found) {
		this.found = found;
	}
	public Color getColour() {
		return colour;
	}
	public Card[] getHand() {
		return hand;
	}
	public void setHand(Card[] hand) {
		this.hand = hand;
	}
	public boolean isPlaying() {
		return playing;
	}
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	@Override
	public String toString() {
		return "Player [colour=" + colour + ", playing=" + playing + ", found=" + Arrays.toString(found) + ", hand="
				+ Arrays.toString(hand) + "]";
	}
}
