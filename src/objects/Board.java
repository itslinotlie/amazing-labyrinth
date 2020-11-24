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
		int row = 1;
		int col = 1;
		Stack<Tile> tiles = new Stack<Tile>();
		Tile tile;
		Scanner in;
		
		try {
			in = new Scanner(new File("res/Tiles.txt"));
			in.useDelimiter(",");
			
			for (int i = 0; i < 16; i++) { // adding non movable tiles to board
				tile = new Tile(in.next().replaceAll("\n", "").replaceAll("\r", ""), in.next(), in.nextInt(), in.nextInt(), in.nextInt());
				board[tile.getX()][tile.getY()] = tile;
			}
			
			for (int i = 0; i < 34; i++) { // adding all the movable tiles to a queue so that they can be randomly added to the board
				tiles.add(new Tile(in.next().replaceAll("\n", "").replaceAll("\r", ""), in.next(), in.nextInt(), in.nextInt(), in.nextInt()));
			}
			Collections.shuffle(tiles);

			while(tiles.size() != 1) { // randomly assigning orientation and position to movable tiles, and adding them to board
				while(true) { //bash till it something works
					row = (int) (Math.random() * 7 + 1);
					col = (int) (Math.random() * 7 + 1);
					
					if (board[row][col] == null) {
						board[row][col] = tiles.pop();
						break;
					}
				}
			}
			freeTile = tiles.pop();
	
		} catch (FileNotFoundException e) {
			System.out.println("ERROR. File not found.");
		}
	}

	public Tile[][] getBoard() {
		return board;
	}
	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + "]";
	}
}
