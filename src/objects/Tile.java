package objects;

public class Tile {
	private String type; // I, L, T
	private String item;
	private boolean moveable;
	private int orientation; // 0 is orientation in the way the letters I, L, T, 1 is 90 rotation clockwise, 2 is 180 rotation clockwise, 3 is 270 rotation clockwise, 4 is 360 rotation clockwise.
	private int x, y;
	
	public Tile(String type, String item, boolean moveable, int orientation, int x, int y) {
		this.type = type;
		this.item = item;
		this.moveable = moveable;
		this.orientation = orientation;
		this.x = x;
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
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
		return "Tile [type=" + type + ", item=" + item + ", moveable=" + moveable + ", orientation=" + orientation
				+ ", x=" + x + ", y=" + y + "]";
	}
}
