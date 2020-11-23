package guiClasses;

import java.awt.Color;
import javax.swing.*;
import objects.*;

public class LabyrinthGUI extends JFrame{
	
	private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
	
	private static final Color LIGHT_GREY = new Color(35,35,35);
	
	private Tile[][] board = new Board().getBoard();
	
	private JLayeredPane mainPanel = new JLayeredPane();
	
	private JPanel gamePanel = new JPanel();
	private JPanel playerPanel = new JPanel();
	
	public LabyrinthGUI(int cards, Color[] playerColours, boolean continueGame) {
		
		createPanels();
		createFrame();
	}
	
	private void createPanels() {
		
		gamePanel.setLayout(null);
		gamePanel.setBounds(20, 20, 518, 518);
		gamePanel.setBackground(Color.GRAY);
		gamePanel.setVisible(true);
		
		for (int row = 1; row < 8; row++) {	
			for (int col = 1; col < 8; col++) {

				JLabel tileLabel = new JLabel("hello");
				tileLabel.setBounds((board[row][col].getX()-1) * (520/7), (board[row][col].getY()-1) * (520/7), 520/7, 520/7);
				tileLabel.setBackground(Color.RED);
				tileLabel.setOpaque(true);
				gamePanel.add(tileLabel);
				
			}
		}
		

		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.add(gamePanel);
		mainPanel.setVisible(true);
	}
	
	private void createFrame() {
		setLayout(null);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setTitle("Amazing Labyrinth");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(gamePanel);
		repaint();
	}
	
}
