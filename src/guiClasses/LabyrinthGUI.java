package guiClasses;

import objects.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

/**
 * GUI for the Labyrinth game
 * Uses the Keyboard and mouse to interact with the game
 */

public class LabyrinthGUI extends JFrame implements KeyListener, ActionListener{
	
	//Dimensions for screen and each tile 
	private static final int SCREEN_WIDTH = 900, SCREEN_HEIGHT = 655, TILE_SIZE = 520/7; 
	
	//check whether a new game is being generated
	public static boolean continueGame;
	
	//number of cards per player
	private int numCards;
	
	//current turn and board size
	private int turn = 0;
	private int mxn = 7;
	
	//previous shiftTile button clicked
	private int prev = -1;

	//board object
	private Board boardObj;

	//array of each tile in boardObj
	private Tile[][] board;
	
	//main panel, adds all other panels
	private JLayeredPane mainPanel = new JLayeredPane();
	
	//panels for the game and player
	private JPanel gamePanel = new JPanel();
	private JPanel playerPanel = new JPanel();

	//colors for each player
	private Color[] playerColours;
	
	//tile rotate buttons
	private JButton rotateLeft = new JButton();
	private JButton rotateRight = new JButton();
	
	//next turn button
	private JButton endTurnButton = new JButton("End Turn");
	 
	//buttons to shift tiles
	private JButton[] shiftTilesButtons = new JButton[12];

	//indicate which player is playing 
	private JLabel playerTurnLabel = new JLabel("Player 1");
	
	//display players hand 
	private ArrayList<JButton> cards = new ArrayList<JButton>();
	
	//display players on the board
	private ArrayList<JLabel> playerLabels = new ArrayList<JLabel>();

	//keep track of winners
	private ArrayList<Player> winners = new ArrayList<Player>();
	
	//keep track of active players
	private ArrayList<Player> players = new ArrayList<Player>();
	
	//keep track of time (used for player animation)
	private int time = 0;
	
	//constructor
	public LabyrinthGUI(int cards, Color[] playerColours, boolean continueGame) {
		
		//sets the color of players and generates a board
		this.playerColours = playerColours;
		
		LabyrinthGUI.continueGame = continueGame;
		
		boardObj = new Board();
		
		if (!continueGame) numCards = cards;
		board = boardObj.getBoard();

		//create panels and framework
		createBoardPanels();
		createPlayerPanel();	
		createFrame();
	}

	private void createPlayerPanel() {
		//create free tile label
		boardObj.getFreeTile().setBounds(150, 150, 130, 130);
		boardObj.getFreeTile().setBackground(Color.RED);
		boardObj.getFreeTile().setOpaque(true);

		//rotate left and right button
		rotateLeft.setBounds(245, 300, 50, 50);
		rotateLeft.setFocusable(false);
		rotateLeft.setIcon(new ImageIcon(new ImageIcon("./res/gui-images/rotateLeft.png").getImage().getScaledInstance(50, 50, 0)));
		rotateLeft.setBackground(Color.CYAN);
		rotateLeft.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), false)); //lambdas op
		
		rotateRight.setBounds(125, 300, 50, 50);
		rotateRight.setFocusable(false);
		rotateRight.setIcon(new ImageIcon(new ImageIcon("./res/gui-images/rotateRight.png").getImage().getScaledInstance(50, 50, 0)));
		rotateRight.setBackground(Color.CYAN);
        rotateRight.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), true)); //lambdas op
		
        //end turn button
		endTurnButton.setBounds(125, 570, 180, 40);
		endTurnButton.setForeground(Color.RED);
		endTurnButton.setBackground(Color.DARK_GRAY.darker());
		endTurnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		endTurnButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
		endTurnButton.setFocusPainted(false);
		endTurnButton.addActionListener(this);
		
		//add players hand to active player
		for (int i = 0; i < numCards; i++) {
			JButton card = new JButton();
			card.setForeground(Color.WHITE);
			card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			card.setBounds(125 + (i%3) * 60, 360 + (int) (i/3 * 110), 50, 80);
			card.addActionListener(this);
			card.setFocusable(false);
			cards.add(card);
			playerPanel.add(card);
		}
		
		//start a new turn
		nextTurn();
		
		//indicate the players turn
		playerTurnLabel.setBounds(125, 50, 180, 40);
		playerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerTurnLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		
		//add elements to playerPanel
		playerPanel.setLayout(null);
		playerPanel.setBounds(540, 0, 400, SCREEN_HEIGHT);
		playerPanel.setBackground(Color.BLACK);
		playerPanel.add(endTurnButton);
		playerPanel.add(playerTurnLabel);
		playerPanel.add(boardObj.getFreeTile());
		playerPanel.add(rotateLeft); 
		playerPanel.add(rotateRight);
		playerPanel.setVisible(true);
	}
	
	//adds all active players to the game
	private void addPlayers() {
		
		//reads the players data if user chooses to continue game
		if (LabyrinthGUI.continueGame) {
			
			Scanner input;
			
			try {
				input = new Scanner(new File("saveGame.txt"));
				input.useDelimiter(",");
				for (int i = 0; i < 50; i++) input.nextLine();
				turn = input.nextInt()-1;
				input.nextLine();
				while (input.hasNext()) {
					
					Color colour = new Color(input.nextInt(), input.nextInt(), input.nextInt());
					
					int cardCount = input.nextInt();
					
					if (cardCount != 0){
						Card[] cards = new Card[cardCount];
						
						numCards = cards.length;
						
						for (int i = 0; i < cards.length; i++) {
							cards[i] = new Card(input.next().replaceAll("\n", "").replaceAll("\r", ""));
						}
						players.add(new Player(0, colour, input.nextBoolean(), input.nextInt(), input.nextInt()));
						players.get(players.size()-1).setHand(cards);
					} else {
						winners.add(new Player(numCards, colour, false, 8, 8));
					}
					input.nextLine();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//creates a label for each of the players and sets their color to the color they had chosen
			for (int i = 0; i < players.size(); i++) {
				
				playerLabels.add(new JLabel());
				
				Color colour = players.get(i).getColour();
				
				if (colour.getRed() == 255 && colour.getBlue() == 0 && colour.getGreen() == 0) {
					playerLabels.get(i).setBounds((players.get(i).getX()-1)*TILE_SIZE+70,(players.get(i).getY()-1)*TILE_SIZE+65, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player0.png").getImage().getScaledInstance(30, 30, 0)));
				}
				else if (colour.getRed() == 0 && colour.getBlue() == 255 && colour.getGreen() == 0) {
					playerLabels.get(i).setBounds((players.get(i).getX()-1)*TILE_SIZE+70,(players.get(i).getY()-1)*TILE_SIZE+65, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player1.png").getImage().getScaledInstance(30, 30, 0)));
				}
					
				else if (colour.getRed() == 255 && colour.getBlue() == 0 && colour.getGreen() == 255) {
					playerLabels.get(i).setBounds((players.get(i).getX()-1)*TILE_SIZE+70,(players.get(i).getY()-1)*TILE_SIZE+65, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player2.png").getImage().getScaledInstance(30, 30, 0)));
				}
				else if (colour.getRed() == 0 && colour.getBlue() == 0 && colour.getGreen() == 255) {
					playerLabels.get(i).setBounds((players.get(i).getX()-1)*TILE_SIZE+70,(players.get(i).getY()-1)*TILE_SIZE+65, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player3.png").getImage().getScaledInstance(30, 30, 0)));
				}
				
				//add the label to the screen
				gamePanel.add(playerLabels.get(i));
			}
			
		} else {
			
			//creates new players and adds their color chosen
			for (int i = 0; i < playerColours.length; i++) {
				
				players.add(new Player(numCards, playerColours[i], true, 0, 0));
				playerLabels.add(new JLabel());
				
				if (playerColours[i] == Color.RED) {
					playerLabels.get(i).setBounds(74, 65, 50, 50);
					players.get(i).setX(1);
					players.get(i).setY(1);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player0.png").getImage().getScaledInstance(30, 30, 0)));
				}
				else if (playerColours[i] == Color.BLUE) {
					players.get(i).setX(7);
					players.get(i).setY(7);
					playerLabels.get(i).setBounds(514, 506, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player1.png").getImage().getScaledInstance(30, 30, 0)));
				}
					
				else if (playerColours[i] == Color.YELLOW) {
					players.get(i).setX(7);
					players.get(i).setY(1);
					playerLabels.get(i).setBounds(514, 65, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player2.png").getImage().getScaledInstance(30, 30, 0)));
				}
				else if (playerColours[i] == Color.GREEN) {
					players.get(i).setX(1);
					players.get(i).setY(7);
					playerLabels.get(i).setBounds(75, 506, 50, 50);
					playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player3.png").getImage().getScaledInstance(30, 30, 0)));
				}
	
				//add the label to the screen
				gamePanel.add(playerLabels.get(i));
			}
		}
	}
	
	//updates the players turn
	private void nextTurn() {

		turn ++;
		
		if (turn > players.size()) 
			turn = 1;
		
		//change the players hand
		for (int i = 0; i < cards.size(); i++) {
			if (players.get(turn-1).getHand()[i].getItem().equals("None")) {
				cards.get(i).setIcon(new ImageIcon(new ImageIcon("./res/card-images/completed.png").getImage().getScaledInstance(50, 80, 0)));
			} else {
				cards.get(i).setIcon(new ImageIcon(new ImageIcon(players.get(turn-1).getHand()[i].getImage()).getImage().getScaledInstance(50, 80, 0)));
			}
		}
		
		//disable the button opposite to the shift button the player used last turn
		for (JButton shift : shiftTilesButtons)
			shift.setEnabled(true);
		if(prev!=-1) shiftTilesButtons[prev].setEnabled(false);
		
		//disable end turn button until player shifts the board
		endTurnButton.setEnabled(false);
		
		//highlight available moves
		highlightBoard();
		
		//change the visual display of the player label
		playerTurnLabel.setForeground(players.get(turn-1).getColour());
		playerTurnLabel.setText("Player " + turn);
		
		//save the game
		saveGame();
	}

	private void saveGame() {
		
		//create a PrintWriter to write down all components of the game
		try {
			PrintWriter saveWriter = new PrintWriter(new FileWriter("saveGame.txt", false));
			
			String tiles = "";
			
			//add the current status of the board
			for (int row = 1; row < 8; row++) {
				for (int col = 1; col < 8; col++) {
					tiles += board[row][col].getType() + "," + board[row][col].getName() + "," + Integer.toString((int)(board[row][col].getAngle()/90)) + 
							"," + board[row][col].getDown() + ","+ board[row][col].getLeft() + ",\n";
				}
				
			}
			
			//add the free tile
			tiles += boardObj.getFreeTile().getType() + "," + boardObj.getFreeTile().getName() + "," +
			Integer.toString((int)(boardObj.getFreeTile().getAngle()/90)) + "," + boardObj.getFreeTile().getDown() + "," + boardObj.getFreeTile().getLeft() + ",\n";
			
			//add the current turn
			tiles += turn + ",\n";
			
			//add each player and it's variables to the save game file
			for (int i = 0; i < players.size(); i++) {
				
				tiles += players.get(i).getColour().getRed() + "," + players.get(i).getColour().getGreen() + "," + players.get(i).getColour().getBlue() + ","; 
				tiles += numCards + ",";
				for (Card card : players.get(i).getHand()) {
					tiles += card.getItem() + ",";
				}
				
				tiles += "false," + players.get(i).getX() + "," + players.get(i).getY();
				tiles += ",\n";
			
			}
			
			//add each player that has won the game to the save game file
			for (int i = 0; i < winners.size(); i++) {
				tiles += winners.get(i).getColour().getRed() + "," + winners.get(i).getColour().getGreen() + "," + winners.get(i).getColour().getBlue() + ",";
				tiles += "0,\n";
			}
			
			//write the information on the file 
			saveWriter.write(tiles);
			
			//close the PrintWriter
			saveWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void createBoardPanels() {
		
		//panel for the board
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 620, 620);
		gamePanel.setBackground(Color.DARK_GRAY);
		gamePanel.setVisible(true);
		
		//repaint the board with all the tiles
		paintBoard();
		
		//add all players to the game
		addPlayers();
		
		//add all tiles to the board
		for (int row = 1; row < 8; row++) {	
			for (int col = 1; col < 8; col++) {	
				board[row][col].setOpaque(true);
				gamePanel.add(board[row][col]);
			}	
		}

		//create shift tile buttons 
		for (int i = 0; i < 12; i++) {
			shiftTilesButtons[i] = new JButton();
			shiftTilesButtons[i].setFocusable(false);
			shiftTilesButtons[i].setFocusPainted(false);
			shiftTilesButtons[i].setContentAreaFilled(false);
			shiftTilesButtons[i].setOpaque(false);
			shiftTilesButtons[i].setIcon(new ImageIcon("./res/gui-images/arrow" + (i/3) + ".png"));
			
			//add images to each shift tile button depending on the orientation 
			switch (i/3) {
				case 0: //top
					shiftTilesButtons[i].setBounds(TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 0, 50, 50);
					break;
				case 1: //right
					shiftTilesButtons[i].setBounds(570,TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 50, 50);
					break;
				case 2: //bottom
					shiftTilesButtons[i].setBounds(TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 570, 50, 50);
					break;
				case 3: //left
					shiftTilesButtons[i].setBounds(0,TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 50, 50);
					break;
			}
			shiftTilesButtons[i].addActionListener(this);
			
			//add the buttons to the panel
			gamePanel.add(shiftTilesButtons[i]);
		}
		
		//create the main panel and add all other panels onto it
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.add(gamePanel);
		mainPanel.add(playerPanel);
		mainPanel.setVisible(true);
	}

	//shifting tiles
	public void shift(int parity, int letter, int magnitude) {

		//remove the free tile from the player panel and add it to the board
		playerPanel.remove(boardObj.getFreeTile());
		gamePanel.add(boardObj.getFreeTile());

		//check which button clicked
		char p, n;
		if(parity==0) p = '+';
		else p = '-';
		if(letter==0) n = 'c';
		else n = 'r';

		//shift the tile in the board class
		boardObj.shiftTile(p, n, magnitude);

		//if a player is in the way of a tile shift, move the player
		for (int i=0;i<players.size();i++) {
		    if(letter==0 && players.get(i).getX()==magnitude) {
                if(parity==0) movePlayer(2, i);
                else movePlayer(0, i);
            }
		    else if(letter!=0 && players.get(i).getY()==magnitude) {
		        if(parity==0) movePlayer(1, i);
		        else movePlayer(3, i);
            }
        }
		
		//repaint the board
		paintBoard();
		repaint();
	}
	private void paintBoard() {
		
		//repaint all elements on the board
		for (int row=1;row<=7;row++) {
			for (int col=1;col<=7;col++) {
				if (board[row][col] != null) {
					board[row][col].setBounds((board[row][col].getLeft()-1) * (TILE_SIZE)+50, (board[row][col].getDown()-1) * (TILE_SIZE) + 50, TILE_SIZE, TILE_SIZE);
					board[row][col].setIcon(new ImageIcon(new ImageIcon(board[row][col].getImage()).getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, 0)));
				}
			}
		}
		
		//switch the newly created free tile from the board to the player panel
		gamePanel.remove(boardObj.getFreeTile());
		boardObj.getFreeTile().setIcon(new ImageIcon(new ImageIcon(boardObj.getFreeTile().getImage()).getImage().getScaledInstance(130, 130, 0)));
		boardObj.getFreeTile().setBounds(150,150,130,130);
		playerPanel.add(boardObj.getFreeTile());

	}
	
	//highlights the available tiles for the player to move
	private void highlightBoard() {
		
		//create a 2d boolean array to see which tiles can be moved to
		boolean[][] moveableTiles = boardObj.getPath(players.get(turn-1).getY(), players.get(turn-1).getX());
		
		//highlight each tile that can be moved to 
		for (int row = 1; row < 8; row++) {	
			for (int col = 1; col < 8; col++) {	
				if (moveableTiles[row][col]) {
					board[row][col].setBorder(new LineBorder(players.get(turn-1).getColour(), 2));
				} else {
					board[row][col].setBorder(null);
				}
			}	
		}
		
		//remove highlight on the free tile 
		boardObj.getFreeTile().setBorder(null);
	}
	
	//method to rotate tiles
	public void rotate(int y, int x, boolean right) {
		board[y][x].rotate(90, right, true);
		boardObj.getFreeTile().setIcon(new ImageIcon(new ImageIcon(boardObj.getFreeTile().getImage()).getImage().getScaledInstance(130, 130, 0)));
	}
	
	private void highlightCard(int card) {
		//loops through all tiles to check for the card and highlight it
		for (int row = 1; row < 8; row++) {
			for (int col = 1; col < 8; col++) {
				if (board[row][col].getName().equals(players.get(turn-1).getHand()[card].getItem())){
					board[row][col].setBorder(new LineBorder(Color.BLACK, 5));				
				}
			}
		}
		
	}
	
	//create the frame and add the main panel
	private void createFrame() {
		setLayout(null);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setTitle("Amazing Labyrinth");
		addKeyListener(this);
		setFocusable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(mainPanel);
		repaint();
	}
	
	//move the player
	private void movePlayer(int direction, int turny) {
		
		//array to change the x and y of player depending on the direction
        int move[][] = {
            {-TILE_SIZE/10, 0},
            {0, TILE_SIZE/10},
            {TILE_SIZE/10, 0},
            {0, -TILE_SIZE/10},
        };

        //reset the time
		time = 0;
		
		//switch statement to determine where the player is moving to, and wrap around the board if needed
		switch (direction) {
            case (0):
                players.get(turny).setY(players.get(turny).getY()-1);
                if(players.get(turny).getY()==0) {
                    players.get(turny).setY(7);
                }
                break;
            case (1):
                players.get(turny).setX(players.get(turny).getX()+1);
                if(players.get(turny).getX()==8) {
                    players.get(turny).setX(1);
                }
                break;
            case (2):
                players.get(turny).setY(players.get(turny).getY()+1);
                if(players.get(turny).getY()==8) {
                    players.get(turny).setY(1);
                }
                break;
            case (3):
                players.get(turny).setX(players.get(turny).getX()-1);
                if(players.get(turny).getX()==0) {
                    players.get(turny).setX(7);
                }
                break;
		}
			
		//create a new timer and start it
		Timer timer = new Timer(20, this);
		timer.start();
		
		//smooth transitions to move the player 
		timer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				time ++;
				
                int y = playerLabels.get(turny).getY()+move[direction][0];
				int x = playerLabels.get(turny).getX()+move[direction][1];
				playerLabels.get(turny).setBounds(x, y, playerLabels.get(turny).getWidth(), playerLabels.get(turny).getHeight());
				repaint();

				if (time >= 10) {
					playerLabels.get(turny).setBounds((players.get(turny).getX()-1)*TILE_SIZE+70,(players.get(turny).getY()-1)*TILE_SIZE+65, 50, 50);
					timer.stop();
					time = 0;
					
					//check if the player encounters a treasure in their hand
					checkTreasures();
				}
			}
		});
	}
	
	private void checkTreasures() {
		
		//loop through each card in the players hand and check if the player is currently on that card
		//if so, check if the player has collected all of their cards
		for (int i = 0; i < cards.size(); i++) {
			if (players.get(turn-1).getHand()[i].getItem().equals(board[players.get(turn-1).getY()][players.get(turn-1).getX()].getName())) {
				players.get(turn-1).getHand()[i].setItem("None");
				cards.get(i).setIcon(new ImageIcon(new ImageIcon("./res/card-images/completed.png").getImage().getScaledInstance(50, 80, 0)));
				checkWin(i);
			}
		}
	}

	//check if a player wins the game
	private void checkWin(int player) {
		
		boolean win = true;
		
		//see if all cards have been collected
		for (Card card : players.get(turn-1).getHand()) {
			if (!card.getItem().equals("None")) {
				win = false;
			}
		}
		
		//executes when the player wins
		if (win) {
			
			//remove the player from the game
			winners.add(players.get(turn-1));
			players.remove(turn-1);
			gamePanel.remove(playerLabels.get(turn-1));
			playerLabels.remove(turn-1);
			repaint();
			
			//check if the game is over
			if (players.size() == 1) {
				
				//clear the save file as the game is over
				try {
					PrintWriter clearSave = new PrintWriter(new FileWriter("saveGame.txt", false));
					clearSave.write("");
					clearSave.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//add the final player to the winners array
				winners.add(players.get(0));
				
				//close the screen
				dispose();
				
				//create the end screen
				new EndScreenGUI(winners);
				
				//stop all key listeners in this class
				Thread.currentThread().stop();
			} else {
				//if the game is not over, continue playing
				turn--;
				nextTurn();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//create new turn when end turn button is clicked
		if (event.getSource() == endTurnButton) {
			nextTurn();
			repaint();
		}
		
		//check if shift tiles buttons are clicked
		for (int i = 0; i < shiftTilesButtons.length; i++) {
			if (event.getSource() == shiftTilesButtons[i]) {
				shift((i/3)%3, (i/3)%2, 2*(i%3)+2);
				prev = (i+6)%shiftTilesButtons.length;
				for (JButton shift : shiftTilesButtons)
					shift.setEnabled(false);
				endTurnButton.setEnabled(true);
				highlightBoard();
			}	
		}
		
		//check if a card was clicked to display where it is on the board
		for (int i = 0; i < cards.size(); i++) {
			if (event.getSource() == cards.get(i)) {
				highlightCard(i);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		
		//create an index for key events 
        int keys[] = {
            KeyEvent.VK_W, KeyEvent.VK_UP,
            KeyEvent.VK_D, KeyEvent.VK_RIGHT,
            KeyEvent.VK_S, KeyEvent.VK_DOWN,
            KeyEvent.VK_A, KeyEvent.VK_LEFT
        };
        
        //array to indicate where the player is moving 
        int move[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        //check which key is pressed and call the move player method passing in the direction 
		for (int i = 0; i < 8; i++) {
		    int y1 = players.get(turn-1).getY(), y2 = y1 + move[i/2][0];
		    int x1 = players.get(turn-1).getX(), x2 = x1 + move[i/2][1];
		    if(y2<=0 || y2>mxn || x2<=0 || x2>mxn) continue;
		    if(key.getKeyCode()==keys[i] && time==0 && boardObj.canMove(y1, x1, y2, x2, i/2) && endTurnButton.isEnabled()) {
                movePlayer(i/2, turn-1);
            }
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent key) {}
}
