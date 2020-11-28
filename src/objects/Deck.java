package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Deck {
	private static Stack<Card> deck = new Stack<Card>();
	public Deck() {
		Scanner input;
		try {
			input = new Scanner(new File("res/Treasures.txt"));
			for (int i = 0; i < 24; i++) {
				deck.add(new Card(input.next()));
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR. Card file(s) not found.");
		}
		Collections.shuffle(deck);
	}
	public Card getCard() {
		return deck.pop();
	}
	public Stack<Card> getDeck() {
		return deck;
	}
	@Override
	public String toString() {
		return "Deck [deck=" + deck + "]";
	}
}
