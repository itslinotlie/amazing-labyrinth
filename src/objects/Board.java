package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board {
	private int x, y, mxn = 7, m[][] = {{-1, 0},{0, 1},{1, 0},{0, -1}};;
	private boolean vis[][] = new boolean[mxn+2][mxn+2];
	private Tile free, board[][] = new Tile[mxn+2][mxn+2];
	public Board() {
		Stack<Tile> tiles = new Stack<Tile>();
		Tile tile;
		Scanner in;
		try {
			in = new Scanner(new File("res/Tiles.txt"));
			in.useDelimiter(",");

			for (int i=0;i<50;i++) {
				String letter = in.next().replaceAll("\n", "").replaceAll("\r", "");
				String name = in.next();
				int rot = in.nextInt(), x = in.nextInt(), y = in.nextInt();
				tile = new Tile(letter, name, rot, y, x);
				if(i<16) { //the x and y are flipped in the txt file...
					tile.rotate(rot*90, true, false);
					board[x][y] = tile;
				}
				else tiles.add(tile);
			}
			Collections.shuffle(tiles);

			for (int i=1;i<=mxn;i++) {
				for (int j=1;j<=mxn;j++) {
					if(board[i][j]==null) {
						int rot = (int) (Math.random() * 4 + 1);
						tile = tiles.pop();
						tile.setX(j); tile.setY(i); tile.rotate(rot*90, true, true);
						board[i][j] = tile;
					}
				}
			}
			free = tiles.pop();
			free.setY(mxn+1); free.setX(mxn+1);
			x = y = mxn+1; board[y][x] = free;
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
	//send y and x coords and it'll return a 2d array of the tiles that can be visited from (y, x)
	public boolean[][] getPath(int y, int x) {
		for (int i=0;i<=mxn+1;i++) Arrays.fill(vis[i], false);
		dfs(y, x);
		return vis;
	}
	public void dfs(int y, int x) {
		vis[y][x] = true;
		for (int i=0;i<4;i++) {
			int r = y+m[i][0], c = x+m[i][1];
			if(r<=0 || r>mxn || c<=0 || c>mxn || vis[r][c] || !board[y][x].getMove()[i] || !board[r][c].getMove()[(i+2)%4]) continue;
			dfs(r, c);
		}
	}
	public Tile[][] getBoard() {
		return board;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + "]";
	}
}
