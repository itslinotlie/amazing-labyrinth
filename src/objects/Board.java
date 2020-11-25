package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Board {
	private Tile free, board[][] = new Tile[9][9];
	private int x, y, mxn = 7;
	public Board() {
		int row = 1, col = 1, rot = 0;
		Stack<Tile> tiles = new Stack<Tile>();
		Tile tile;
		Scanner in;
		
		try {
			in = new Scanner(new File("res/Tiles.txt"));
			in.useDelimiter(",");

			for (int i=0;i<50;i++) {
				tile = new Tile(in.next().replaceAll("\n", "").replaceAll("\r", ""), in.next(), in.nextInt(), in.nextInt(), in.nextInt());
				if(i<16) board[tile.getX()][tile.getY()] = tile;
				else tiles.add(tile);
			}
			Collections.shuffle(tiles);

			while(tiles.size() != 1) { // randomly assigning orientation and position to movable tiles, and adding them to board
				while(true) { //bash till it something works
					row = (int) (Math.random() * 7 + 1);
					col = (int) (Math.random() * 7 + 1);
					rot = (int) (Math.random() * 4 + 1);
					if (board[row][col] == null) {
						tile = tiles.pop();
						tile.setX(row); tile.setY(col); tile.rotate(rot*90, false);
						board[row][col] = tile;
						break;
					}
				}
			}
			free = tiles.pop();
			x = free.getX(); y = free.getY(); board[x][y] = free;
		} catch (FileNotFoundException e) {
			System.out.println("ERROR. File not found.");
		}
	}
	//p: anything for right/down, - to go left/up
	//c: r to push row, c = to push column
	//n: row/col number to be pushed
	//Sample i/o:
	//+ R 1 --> shifts 1st row to the right
	//- C 2 --> shifts 2nd col up
	//+ R 8 --> shifts 8th row to the right (didn't include error checking)
	public void shiftTile(char p, char c, int n) {
		if(p=='-') { //push to the left or up
			for (int i=0;i<mxn;i++) {
				if(c=='R') board[n][i] = board[n][i+1];
				else board[i][n] = board[i+1][n];
			}
			if(c=='R') {
				board[n][mxn] = free;
				x = n; y = 0; free = board[x][y];
			} else {
				board[mxn][n] = free;
				x = 0; y = n; free = board[x][y];
			}
		} else { //push to the right or down
			for (int i=mxn+1;i>=2;i--) {
				if(c=='R') board[n][i] = board[n][i-1];
				else board[i][n] = board[i-1][n];
			}
			if(c=='R') {
				board[n][1] = free;
				x = n; y = mxn+1; free = board[x][y];
			} else {
				board[1][n] = free;
				x = mxn+1; y = n; free = board[x][y];
			}
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
