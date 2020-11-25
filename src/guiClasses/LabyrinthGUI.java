package guiClasses;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import objects.*;

public class LabyrinthGUI extends JFrame{
	
	private static final int SCREEN_WIDTH = 850, SCREEN_HEIGHT = 650;
	
	private static final Color LIGHT_GREY = new Color(35,35,35);

	private Board boardObj = new Board();

	private Tile[][] board = boardObj.getBoard();
	
	private JLayeredPane mainPanel = new JLayeredPane();
	
	private JPanel gamePanel = new JPanel();
	private JPanel playerPanel = new JPanel();

	private Color[] playerColours;
	
	//private Player[] players = new Player[playerColours.length];
	
	public LabyrinthGUI(int cards, Color[] playerColours, boolean continueGame) {
		
		this.playerColours = playerColours;
		
		createPlayerPanel();
		createPanels();
		createFrame();
	}
	
	private void createPlayerPanel() {
		
		playerPanel.setLayout(null);
		playerPanel.setBounds(540, 0, 300, SCREEN_HEIGHT);
		playerPanel.setBackground(Color.BLACK);
		playerPanel.setVisible(true);
	}

	private void createPanels() {
		
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 620, 620);
		//gamePanel.setBackground(Color.GRAY);
		gamePanel.setVisible(true);
		
		for (int row = 1; row < 8; row++) {	
			for (int col = 1; col < 8; col++) {
				JLabel tileLabel = new JLabel("hello");
				tileLabel.setBounds((board[row][col].getX()-1) * (520/7)+30, (board[row][col].getY()-1) * (520/7) + 30, 520/7, 520/7);
				tileLabel.setIcon(new ImageIcon(new ImageIcon(board[row][col].getImg()).getImage().getScaledInstance(520/7, 520/7, 0)));
				tileLabel.setBackground(Color.RED);
				tileLabel.setOpaque(true);
				gamePanel.add(tileLabel);
			}
		}
		//this adds the left and right rotate thing
		JLabel free = new JLabel();
		free.setBounds(600, 150, 520/7, 520/7);
		free.setIcon(new ImageIcon(new ImageIcon(board[boardObj.getY()][boardObj.getX()].getImg()).getImage().getScaledInstance(520/7, 520/7, 0)));
		free.setOpaque(true);
		mainPanel.add(free);
		JButton left = new JButton(), right = new JButton();
		left.setBounds(700, 200, 50, 50);
		right.setBounds(760, 200, 50, 50);
		left.setBackground(Color.RED); right.setBackground(Color.GREEN);
		left.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), false)); //lambdas op
		right.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), true));
		mainPanel.add(left); mainPanel.add(right);

		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.add(gamePanel);
		mainPanel.add(playerPanel);
		mainPanel.setVisible(true);
	}
	public void rotate(int y, int x, boolean right) {
		board[y][x].rotate(90, right);
		JLabel tile = new JLabel();
		tile.setBounds(600, 150, 520/7, 520/7);
		tile.setIcon(new ImageIcon(new ImageIcon(board[y][x].getImg()).getImage().getScaledInstance(520/7, 520/7, 0)));
		tile.setOpaque(true);
		mainPanel.add(tile);
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
	
}
