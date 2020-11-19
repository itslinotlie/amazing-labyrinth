package objects;

public class Card {
	private String item;
	
	public Card(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "Card [item=" + item + "]";
	}
	
}
