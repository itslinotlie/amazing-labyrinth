package objects;

import java.util.Arrays;

public class Board {
	private Tile[][] tiles = new Tile[7][7];

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	@Override
	public String toString() {
		return "Board [tiles=" + Arrays.toString(tiles) + "]";
	}
}
