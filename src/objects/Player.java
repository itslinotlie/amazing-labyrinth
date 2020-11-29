package objects;

import java.awt.Color;
import java.util.Arrays;

public class Player {
	private Color colour;
	private boolean playing, found[];
	private Card[] hand;
	private int x;
	private int y;
	
	
	public Player(int numCards, Color colour, boolean playing, int x, int y) {
		Deck deck = new Deck();
		hand = new Card[numCards];
		found = new boolean[numCards];
		for (int i = 0; i < numCards; i++) {
			hand[i] = deck.getCard();
		}
		this.colour = colour;
		this.setX(x);
		this.setY(y);
		this.playing = playing;
		System.out.println(Arrays.toString(hand));
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Player [colour=" + colour + ", playing=" + playing + ", found=" + Arrays.toString(found) + ", hand="
				+ Arrays.toString(hand) + "]";
	}
}
