package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Board {
	
	private Tile[][] board = new Tile[9][9];
	private Tile freeTile;
	
	public Board() {
		int orientation;
		int row = 1;
		int col = 1;
		Stack<Tile> tiles = new Stack<Tile>();
		Tile tile;
		Scanner input;
		
		try {
			input = new Scanner(new File("res/Tiles.txt"));
			input.useDelimiter(",");
			
			for (int i = 0; i < 16; i++) { 									// adding non movable tiles to board
				tile = new Tile(input.next().replaceAll("\n", "").replaceAll("\r", ""), input.next(), input.nextBoolean(), input.nextInt(), input.nextInt(), input.nextInt());
				board[tile.getX()][tile.getY()] = tile;
			}
			
			for (int i = 0; i < 34; i++) { 						// adding all the movable tiles to a queue so that they can be randomly added to the board
				tiles.add(new Tile(input.next().replaceAll("\n", "").replaceAll("\r", ""), input.next(), input.nextBoolean(), input.nextInt(), input.nextInt(), input.nextInt()));
			}
			Collections.shuffle(tiles);

			while(tiles.size() != 1) { 				// randomly assigning orientation and position to movable tiles, and adding them to board
				while(true) {
					orientation = (int) (Math.random() * 4);
					row = (int) (Math.random() * 7 + 1);
					col = (int) (Math.random() * 7 + 1);
					
					if (board[row][col] == null) {
						tile = tiles.pop();
						board[row][col] = new Tile(tile.getType(), tile.getItem(), tile.isMoveable(), orientation, row, col);
						break;
					}
				}
			}
			freeTile = tiles.pop();
	
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
