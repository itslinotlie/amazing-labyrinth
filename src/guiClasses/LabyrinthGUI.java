package guiClasses;

import java.awt.Color;
import javax.swing.*;
import objects.*;

public class LabyrinthGUI extends JFrame{
	
	private static final int SCREEN_WIDTH = 850, SCREEN_HEIGHT = 650;
	
	private static final Color LIGHT_GREY = new Color(35,35,35);
	
	private Tile[][] board = new Board().getBoard();
	
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

		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.add(gamePanel);
		mainPanel.add(playerPanel);
		mainPanel.setVisible(true);
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
