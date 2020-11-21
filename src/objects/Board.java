package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Board {	
	private Tile[][] board = new Tile[9][9];
	private Tile freeTile;
	
	public Board() {
		int orientation;
		int row = 1;
		int col = 1;
		boolean[][] taken = new boolean[9][9];
		Queue<Tile> tiles = new LinkedList<Tile>();
		Tile tile;
		Scanner input;
		
		try {
			input = new Scanner(new File("res/Tiles.txt"));
			input.useDelimiter(",");
			
			for (int i = 0; i < 16; i++) { 									// adding non movable tiles to board
				tile = new Tile(input.next(), input.next(), input.nextBoolean(), input.nextInt(), input.nextInt(), input.nextInt());
				board[tile.getX()][tile.getY()] = tile;
				taken[tile.getX()][tile.getY()] = true;
			}
			
			for (int i = 0; i < 34; i++) { // adding all the movable tiles to a queue so that they can be randomly added to the board
				tiles.add(new Tile(input.next(), input.next(), input.nextBoolean(), input.nextInt(), input.nextInt(), input.nextInt()));
			}
			Collections.shuffle(Arrays.asList(tiles));
			
			for (int i = 0; i < 34; i++) { // randomly assigning orientation and position of tile, and adding to board
				while(tiles.size() != 1) {
					orientation = (int) ((Math.random() * (3 - 0)) + 0);
					while(!taken[row][col]) {
						row = (int) ((Math.random() * (7 - 1)) + 1);
						col = (int) ((Math.random() * (7 - 1)) + 1);
					}
					tile = tiles.poll();
					board[row][col] = new Tile(tile.getType(), tile.getItem(), tile.isMoveable(), orientation, row, col);
				}
			}
			freeTile = tiles.poll();
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR. File not found.");
		}
	}

	public Tile getFreeTile() {
		return freeTile;
	}

	public void setFreeTile(Tile freeTile) {
		this.freeTile = freeTile;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}
	
	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + "]";
	}
}
