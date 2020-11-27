package guiClasses;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

import objects.*;

public class LabyrinthGUI extends JFrame implements KeyListener, ActionListener{
	
	private static final int SCREEN_WIDTH = 900, SCREEN_HEIGHT = 655, TILE_SIZE = 520/7;
	
	private int numCards;
	private int turn = 0;

	private Board boardObj = new Board();

	private Tile[][] board = boardObj.getBoard();
	
	private JLayeredPane mainPanel = new JLayeredPane();
	
	private JPanel gamePanel = new JPanel();
	private JPanel playerPanel = new JPanel();

	private Color[] playerColours;
	
	private JButton rotateLeft = new JButton();
	private JButton rotateRight = new JButton();
	private JButton endTurnButton = new JButton("End Turn");
	 
	private JButton[] shiftTilesButtons = new JButton[12];

	private JLabel playerTurnLabel = new JLabel("Player 1");
	
	private ArrayList<JLabel> cards = new ArrayList<JLabel>();
	private ArrayList<JLabel> playerLabels = new ArrayList<JLabel>();

	private ArrayList<Player> players = new ArrayList<Player>();
	
	private Timer movementTimer = new Timer(1000, this);
	
	public LabyrinthGUI(int cards, Color[] playerColours, boolean continueGame) {
		
		this.playerColours = playerColours;
		numCards = cards;
		
		createBoardPanels();
		createPlayerPanel();
		
		createFrame();
	}

	private void createPlayerPanel() {
		
		boardObj.getFreeTile().setBounds(150, 150, 130, 130);
		boardObj.getFreeTile().setBackground(Color.RED);
		boardObj.getFreeTile().setOpaque(true);
		System.out.println(boardObj.getFreeTile());

		rotateLeft.setBounds(125, 300, 50, 50);
		rotateLeft.setIcon(new ImageIcon(new ImageIcon("./res/images/player0.png").getImage().getScaledInstance(50, 50, 0)));
		rotateLeft.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), false)); //lambdas op
		
		rotateRight.setBounds(245, 300, 50, 50);
		rotateRight.setBackground(Color.GREEN);
		rotateRight.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), true));
		
		endTurnButton.setBounds(125, 570, 180, 40);
		endTurnButton.setForeground(Color.RED);
		endTurnButton.setBackground(Color.DARK_GRAY.darker());
		endTurnButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
		endTurnButton.setFocusPainted(false);
		endTurnButton.addActionListener(this);
		
		for (int i = 0; i < numCards; i++) {
			JLabel card = new JLabel("Potato");
			card.setForeground(Color.WHITE);
			card.setBounds(125 + (i%3) * 60, 360 + (int) (i/3 * 110), 50, 100);
			cards.add(card);
			playerPanel.add(card);
		}
		
		nextTurn();
		
		playerTurnLabel.setBounds(125, 50, 180, 40);
		playerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerTurnLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		
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
	
	private void addPlayers() {
		
		for (int i = 0; i < playerColours.length; i++) {
			players.add(new Player(numCards, playerColours[i], true, 0, 0));
			playerLabels.add(new JLabel());
			
			if (playerColours[i] == Color.RED) {
				playerLabels.get(i).setBounds(74, 64, 50, 50);
				playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/images/player0.png").getImage().getScaledInstance(30, 30, 0)));
			}
			else if (playerColours[i] == Color.BLUE) {
				playerLabels.get(i).setBounds(514, 504, 50, 50);
				playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/images/player1.png").getImage().getScaledInstance(30, 30, 0)));
			}
				
			else if (playerColours[i] == Color.YELLOW) {
				playerLabels.get(i).setBounds(74, 504, 50, 50);
				playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/images/player2.png").getImage().getScaledInstance(30, 30, 0)));
			}
			else if (playerColours[i] == Color.GREEN) {
				playerLabels.get(i).setBounds(514, 64, 50, 50);
				playerLabels.get(i).setIcon(new ImageIcon(new ImageIcon("./res/images/player3.png").getImage().getScaledInstance(30, 30, 0)));
			}
			
			gamePanel.add(playerLabels.get(i));
		}
		
	}
	
	private void nextTurn() {
		
		turn ++;
		
		if (turn > players.size()) 
			turn = 1;
		
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setIcon(new ImageIcon(new ImageIcon(players.get(turn-1).getHand()[i].getImage()).getImage().getScaledInstance(50, 80, 0)));
		}
		
		for (JButton shift : shiftTilesButtons)
			shift.setEnabled(true);
		
		endTurnButton.setEnabled(false);
		
		playerTurnLabel.setForeground(players.get(turn-1).getColour());
		playerTurnLabel.setText("Player " + turn);
	}

	private void createBoardPanels() {
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 620, 620);
		gamePanel.setBackground(Color.DARK_GRAY);
		gamePanel.setVisible(true);
		paintBoard();
		
		addPlayers();
		
		for (int row = 1; row < 8; row++) {	
			for (int col = 1; col < 8; col++) {	
				board[row][col].setOpaque(true);
				gamePanel.add(board[row][col]);
			}	
		}

		for (int i = 0; i < 12; i++) {
			shiftTilesButtons[i] = new JButton();
			shiftTilesButtons[i].setFocusPainted(false);
			shiftTilesButtons[i].setContentAreaFilled(false);
			shiftTilesButtons[i].setOpaque(false);
			shiftTilesButtons[i].setIcon(new ImageIcon("./res/images/arrow" + (i/3) + ".png"));
			
			switch (i/3) {
				case 0: //top
					shiftTilesButtons[i].setBounds(TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2	, 0, 50, 50);
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
			gamePanel.add(shiftTilesButtons[i]);
		}
		
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.add(gamePanel);
		mainPanel.add(playerPanel);
		mainPanel.setVisible(true);
	}
	public void shift(int parity, int letter, int magnitude) {
		playerPanel.remove(boardObj.getFreeTile());
		gamePanel.add(boardObj.getFreeTile());
		char p, n;
		if(parity==0) p = '+';
		else p = '-';
		if(letter==0) n = 'c';
		else n = 'r';

		boardObj.shiftTile(p, n, magnitude);
		paintBoard();
		repaint();
		debug();
	}
	private void paintBoard() {
		for (int row=1;row<=7;row++) {
			for (int col=1;col<=7;col++) {
				if (board[row][col] != null) {
					board[row][col].setBounds((board[row][col].getLeft()-1) * (TILE_SIZE)+50, (board[row][col].getDown()-1) * (TILE_SIZE) + 50, TILE_SIZE, TILE_SIZE);
					board[row][col].setIcon(new ImageIcon(new ImageIcon(board[row][col].getImage()).getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, 0)));
				}
			}
		}
		gamePanel.remove(boardObj.getFreeTile());
		boardObj.getFreeTile().setIcon(new ImageIcon(new ImageIcon(boardObj.getFreeTile().getImage()).getImage().getScaledInstance(130, 130, 0)));
		boardObj.getFreeTile().setBounds(150,150,130,130);
		playerPanel.add(boardObj.getFreeTile());

	}
	
	public void debug() {
		String s = "x";
		for (int i=0;i<=8;i++) {
			for (int j=0;j<=8;j++) {
				if(board[i][j]==null) System.out.printf("%15s ", s);
				else System.out.printf("%15s ", board[i][j].getName());
			} System.out.println();
		}
	}
	
	public void rotate(int y, int x, boolean right) {
		board[y][x].rotate(90, right, true);
		boardObj.getFreeTile().setIcon(new ImageIcon(new ImageIcon(boardObj.getFreeTile().getImage()).getImage().getScaledInstance(130, 130, 0)));
	}
	
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
	
	private void movePlayer(int direction) throws InterruptedException {
		int move[][] = {
			{-TILE_SIZE/10, 0},
			{TILE_SIZE/10, 0},
			{0, -TILE_SIZE/10},
			{0, TILE_SIZE/10}
		};
		for (int i=0;i<10;i++) {
			Thread.sleep(100);
			int x = playerLabels.get(turn-1).getX()+move[direction][0];
			int y = playerLabels.get(turn-1).getY()+move[direction][1];
			int w = playerLabels.get(turn-1).getWidth();
			int h = playerLabels.get(turn-1).getHeight();
			playerLabels.get(turn-1).setBounds(x, y, w, h);
			repaint();
			System.out.println(playerLabels.get(turn-1).getBounds());
		}

		switch (direction) {
		
		case (0):
			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);
				playerLabels.get(turn-1).setBounds(playerLabels.get(turn-1).getX() - TILE_SIZE/10, playerLabels.get(turn-1).getY(),
					playerLabels.get(turn-1).getWidth(), playerLabels.get(turn-1).getHeight());
				repaint();
				System.out.println(playerLabels.get(turn-1).getBounds());
			}
			break;
		
		case (1):
			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);
				playerLabels.get(turn-1).setBounds(playerLabels.get(turn-1).getX() + TILE_SIZE/10, playerLabels.get(turn-1).getY(),
					playerLabels.get(turn-1).getWidth(), playerLabels.get(turn-1).getHeight());
				repaint();
				System.out.println(playerLabels.get(turn-1).getBounds());
			}
			break;
		
		case (2):
			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);
				playerLabels.get(turn-1).setBounds(playerLabels.get(turn-1).getX(), playerLabels.get(turn-1).getY() - TILE_SIZE/10,
					playerLabels.get(turn-1).getWidth(), playerLabels.get(turn-1).getHeight());
				repaint();
				System.out.println(playerLabels.get(turn-1).getBounds());
			}
			break;
		
		case (3):
			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);
				playerLabels.get(turn-1).setBounds(playerLabels.get(turn-1).getX(), playerLabels.get(turn-1).getY() + TILE_SIZE/10,
					playerLabels.get(turn-1).getWidth(), playerLabels.get(turn-1).getHeight());
				repaint();
				System.out.println(playerLabels.get(turn-1).getBounds());
			}
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == endTurnButton) {
			nextTurn();
		}
		
		for (int i = 0; i < shiftTilesButtons.length; i++) {
			if (event.getSource() == shiftTilesButtons[i]) {
				shift((i/3)%3, (i/3)%2, 2*(i%3)+2);
				
				for (JButton shift : shiftTilesButtons)
					shift.setEnabled(false);
				
				endTurnButton.setEnabled(true);
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent key) {
		
	}
	@Override
	public void keyPressed(KeyEvent key) {
		
		int[] keys = {KeyEvent.VK_A, KeyEvent.VK_LEFT, KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_S, KeyEvent.VK_DOWN};
		
		for (int i = 0; i < 8; i++) {
			if (key.getKeyCode() == keys[i]) {
				try {
					movePlayer(i/2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
