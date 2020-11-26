package guiClasses;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import objects.*;

public class LabyrinthGUI extends JFrame implements ActionListener{
	
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
	
	private JLabel freeTile = new JLabel();
	private JLabel playerTurnLabel = new JLabel("Player 1");
	
	private ArrayList<JLabel> cards = new ArrayList<JLabel>();
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public LabyrinthGUI(int cards, Color[] playerColours, boolean continueGame) {
		
		this.playerColours = playerColours;
		numCards = cards;
		
		createPlayerPanel();
		createBoardPanels();
		createFrame();
	}

	private void createPlayerPanel() {
		
		freeTile.setBounds(150, 150, 130, 130);
		freeTile.setIcon(new ImageIcon(new ImageIcon(board[boardObj.getY()][boardObj.getX()].getImg()).getImage().getScaledInstance(130, 130, 0)));

		rotateLeft.setBounds(125, 300, 50, 50);
		rotateLeft.setBackground(Color.RED);
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
		
		addPlayers();
		nextTurn();
		
		playerTurnLabel.setBounds(125, 50, 180, 40);
		playerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerTurnLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		
		playerPanel.setLayout(null);
		playerPanel.setBounds(540, 0, 400, SCREEN_HEIGHT);
		playerPanel.setBackground(Color.BLACK);
		playerPanel.add(endTurnButton);
		playerPanel.add(playerTurnLabel);
		playerPanel.add(freeTile);
		playerPanel.add(rotateLeft); 
		playerPanel.add(rotateRight);
		playerPanel.setVisible(true);
	}

	private void addPlayers() {
		
		for (int i = 0; i < playerColours.length; i++) {
			players.add(new Player(numCards, playerColours[i], true));
		}
	}
	
	private void nextTurn() {
		
		turn ++;
		
		if (turn > players.size()) 
			turn = 1;
		
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setIcon(new ImageIcon(new ImageIcon(players.get(turn-1).getHand()[i].getImg()).getImage().getScaledInstance(50, 80, 0)));
		}
		playerTurnLabel.setForeground(players.get(turn-1).getColour());
		playerTurnLabel.setText("Player " + turn);
	}

	private void createBoardPanels() {
		
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 620, 620);
		gamePanel.setBackground(Color.DARK_GRAY);
		gamePanel.setVisible(true);
		
		for (int row = 1; row < 8; row++) {	
			for (int col = 1; col < 8; col++) {
				JLabel tileLabel = new JLabel("hello");
				tileLabel.setBounds((board[row][col].getX()-1) * (TILE_SIZE)+50, (board[row][col].getY()-1) * (TILE_SIZE) + 50, TILE_SIZE, TILE_SIZE);
				tileLabel.setIcon(new ImageIcon(new ImageIcon(board[row][col].getImg()).getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, 0)));
				tileLabel.setBackground(Color.RED);
				tileLabel.setOpaque(true);
				gamePanel.add(tileLabel);
			}
		}
		
		for (int i = 0; i < 12; i++) {
			shiftTilesButtons[i] = new JButton();
			shiftTilesButtons[i].setFocusPainted(false);
			shiftTilesButtons[i].setContentAreaFilled(false);
			shiftTilesButtons[i].setOpaque(false);
			shiftTilesButtons[i].setIcon(new ImageIcon("./res/images/arrow" + (i/3) + ".png"));
			switch (i/3) { //top right bottom left
				case 0:
					shiftTilesButtons[i].setBounds(TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2	, 0, 50, 50);
					break;
				case 1:
					shiftTilesButtons[i].setBounds(570,TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 50, 50);
					break;
				case 2:
					shiftTilesButtons[i].setBounds(0,TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 50, 50);
					break;
				case 3:
					shiftTilesButtons[i].setBounds(TILE_SIZE + 65 +(i%3) * TILE_SIZE * 2, 570, 50, 50);
					break;
			}
			final int x = i;
			shiftTilesButtons[i].addActionListener(e -> shift((x/3)%3, (x/3)%2, 2*(x%3)+2));
			gamePanel.add(shiftTilesButtons[i]);
		}

		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.add(gamePanel);
		mainPanel.add(playerPanel);
		mainPanel.setVisible(true);
	}
	public void shift(int parity, int letter, int magnitude) {
		char p, n, d;
		if(parity==0) p = '+';
		else p = '-';
		if(letter==0) n = 'c';
		else n = 'r';
		boardObj.shiftTile(p, n, magnitude);
		for (int i=1;i<=7;i++) {
			for (int j=1;j<=7;j++) {
				System.out.print(boardObj.getBoard()[i][j].getName()+" ");
			} System.out.println();
		}
	}
	public void rotate(int y, int x, boolean right) {
		board[y][x].rotate(90, right, true);
		freeTile.setIcon(new ImageIcon(new ImageIcon(board[y][x].getImg()).getImage().getScaledInstance(135, 135, 0)));
	}
	
	private void createFrame() {
		setLayout(null);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setTitle("Amazing Labyrinth");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(mainPanel);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == endTurnButton) {
			nextTurn();
		}
		
	}
	
}
