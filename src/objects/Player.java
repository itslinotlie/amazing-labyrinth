package objects;

import java.util.Arrays;

public class Player {
	private int row, col;
	private boolean playing;
	private boolean[] found;
	private Card[] hand;
	
	
	public Player(int numCards, int row, int col, boolean playing) {
		hand = new Card[numCards];
		found = new boolean[numCards];
		for (int i = 0; i < numCards; i++) {
			hand[i] = Deck.getCard();
		}
		this.row = row;
		this.col = col;
		this.playing = playing;
	}

	public boolean[] getFound() {
		return found;
	}

	public void setFound(boolean[] found) {
		this.found = found;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card[] hand) {
		this.hand = hand;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	@Override
	public String toString() {
		return "Player [row=" + row + ", col=" + col + ", playing=" + playing + ", found=" + Arrays.toString(found)
				+ ", hand=" + Arrays.toString(hand) + "]";
	}
}
