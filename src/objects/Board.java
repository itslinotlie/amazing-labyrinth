package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import guiClasses.LabyrinthGUI;

/**
 * Models the board that stores all the tile images and related logic
 */
public class Board {
	private int x, y, maxN = 7, m[][] = {{-1, 0},{0, 1},{1, 0},{0, -1}};
	private boolean vis[][] = new boolean[maxN+2][maxN+2];

	private Tile tile50, board[][] = new Tile[maxN+2][maxN+2];

	//creates the board with randomly orientated tiles
	public Board(boolean continueGame) {
        Stack<Tile> tiles = new Stack<Tile>();
        Tile tile;
        Scanner in;
        try {

            if (!LabyrinthGUI.continueGame) {

                in = new Scanner(new File("res/Tiles.txt"));
                in.useDelimiter(",");

                for (int i=0;i<50;i++) {
                    String letter = in.next().replaceAll("\n", "").replaceAll("\r", "");
                    String name = in.next();
                    int rot = in.nextInt(), y = in.nextInt(), x = in.nextInt();
                    tile = new Tile(letter, name, rot, x, y);
                    if (rot != -1) {
                        tile.rotate(rot*90, true, false);
                        board[y][x] = tile;
                    }
                    else {
                        tiles.add(tile);
                    }
                }
                tile50 = tiles.pop();
                Collections.shuffle(tiles);
            } else {

                in = new Scanner(new File("saveGame.txt"));
                in.useDelimiter(",");

                for (int i=0;i<50;i++) {
                    String letter = in.next().replaceAll("\n", "").replaceAll("\r", "");

                    String name = in.next();
                    int rot = in.nextInt(), y = in.nextInt(), x = in.nextInt();
                    tile = new Tile(letter, name, rot, x, y);

                    if (i == 49) {
                        tile50 = new Tile(letter, name, rot, x, y);
                        tile50.rotate(rot*90, true, false);
                    } else if (rot != -1) {
                        tile.rotate(rot*90, true, false);
                        board[y][x] = tile;
                    }
                    else {
                        tiles.add(tile);
                    }

                }

            }

            for (int i=1;i<=maxN;i++) {
                for (int j=1;j<=maxN;j++) {
                    if(board[i][j]==null) {
                        int rot = (int) (Math.random() * 4 + 1);
                        tile = tiles.pop();
                        tile.setLeft(j); tile.setDown(i); tile.rotate(rot*90, true, true);
                        board[i][j] = tile;
                    }
                }
            }
            y = x = maxN+1;
            tile50.setDown(y); tile50.setLeft(x);
            board[y][x] = tile50;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. File not found.");
        }
	}
	//shifts the tile based on given parameters
	public void shiftTile(char parity, char letter, int position) {
		if(parity=='-') { //push to the left or up
			for (int i=0;i<maxN;i++) {
				if(letter=='r') swap(position, i, position, i+1);
				else swap(i, position, i+1, position);
			}
			if(letter=='r') {
				swap(position, maxN);
				y = position; x = 0;
			} else {
				swap(maxN, position);
				y = 0; x = position;
			}
		} else { //push to the right or down
			for (int i=maxN+1;i>=2;i--) {
				if(letter=='r') swap(position, i, position, i-1);
				else swap(i, position, i-1, position);
			}
			if(letter=='r') {
				swap(position, 1);
				y = position; x = maxN+1;
			} else {
				swap(1, position);
				y = maxN+1; x = position;
			}
		}
		tile50 = board[y][x];
	}

	//places the free tile onto the board
	private void swap(int y1, int x1) {
		board[y1][x1] = tile50;
		board[y1][x1].setDown(y1);
		board[y1][x1].setLeft(x1);
	}

	//point (y1, x1) takes the value of point (y2, x2)
	private void swap(int y1, int x1, int y2, int x2) {
		board[y1][x1] = board[y2][x2];
		board[y1][x1].setDown(y1);
		board[y1][x1].setLeft(x1);
	}

	//checks if one tile can be traversed to another
	public boolean canMove(int y1, int x1, int y2, int x2, int direction) {
		return board[y1][x1].getMove()[direction] && board[y2][x2].getMove()[(direction+2)%4];
	}

	//returns all the tiles that can be traversed from a given tile
	public boolean[][] getPath(int y, int x) {
		for (int i=0;i<=maxN+1;i++) {
			Arrays.fill(vis[i], false);
		}
		dfs(y, x);
		return vis;
	}

	//performs a depth-first serach on the board
	private void dfs(int y, int x) {
		vis[y][x] = true;
		for (int i=0;i<4;i++) {
			int r = y+m[i][0], c = x+m[i][1];
			if(r<=0 || r>maxN || c<=0 || c>maxN || vis[r][c]) continue;
			if(!board[y][x].getMove()[i] || !board[r][c].getMove()[(i+2)%4]) continue;
			dfs(r, c);
		}
	}

	public Tile[][] getBoard() {
		return board;
	}

	public Tile getFreeTile() {
		return board[y][x];
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
