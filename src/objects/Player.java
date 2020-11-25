package objects;

import java.util.Arrays;

public class Player {
	private Card[] hand;
	private int playerID;
	private int row, col;
	private boolean playing;
	
	public Player(int numCards, int playerID, int row, int col, boolean playing) {
		hand = new Card[numCards];
		for (int i = 0; i < numCards; i++) {
			hand[i] = Deck.getCard();
		}
		this.playerID = playerID;
		this.row = row;
		this.col = col;
		this.playing = playing;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card[] hand) {
		this.hand = hand;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
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
		return "Player [hand=" + Arrays.toString(hand) + ", playerID=" + playerID + ", row=" + row + ", col=" + col
				+ ", playing=" + playing + "]";
	}
	
}
