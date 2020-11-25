package guiClasses;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import objects.*;

public class LabyrinthGUI extends JFrame implements ActionListener{
	
	private static final int SCREEN_WIDTH = 850, SCREEN_HEIGHT = 650;
	
	private static final Color LIGHT_GREY = new Color(35,35,35);

	private Board boardObj = new Board();

	private Tile[][] board = boardObj.getBoard();
	
	private JLayeredPane mainPanel = new JLayeredPane();
	
	private JPanel gamePanel = new JPanel();
	private JPanel playerPanel = new JPanel();

	private Color[] playerColours;
	
	private JButton rotateLeft = new JButton(), rotateRight = new JButton();
	
	private JLabel freeTile = new JLabel();
	
	//private Player[] players = new Player[playerColours.length];
	
	public LabyrinthGUI(int cards, Color[] playerColours, boolean continueGame) {
		
		this.playerColours = playerColours;
		
		createPlayerPanel();
		createPanels();
		createFrame();
	}

	private void createPlayerPanel() {
		
		freeTile.setBounds(150, 100, 520/7, 520/7);
		freeTile.setIcon(new ImageIcon(new ImageIcon(board[boardObj.getY()][boardObj.getX()].getImg()).getImage().getScaledInstance(520/7, 520/7, 0)));

		rotateLeft.setBounds(130, 200, 50, 50);
		rotateLeft.setBackground(Color.RED); rotateRight.setBackground(Color.GREEN);
		rotateLeft.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), false)); //lambdas op
		
		rotateRight.setBounds(180, 200, 50, 50);
		rotateRight.addActionListener(e -> rotate(boardObj.getY(), boardObj.getX(), true));
		
		playerPanel.setLayout(null);
		playerPanel.setBounds(540, 0, 300, SCREEN_HEIGHT);
		playerPanel.setBackground(Color.BLACK);
		playerPanel.add(freeTile);
		playerPanel.add(rotateLeft); 
		playerPanel.add(rotateRight);
		playerPanel.setVisible(true);
	}

	private void createPanels() {
		
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 620, 620);
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
	
	public void rotate(int y, int x, boolean right) {
		board[y][x].rotate(90, right, true);
		freeTile.setIcon(new ImageIcon(new ImageIcon(board[y][x].getImg()).getImage().getScaledInstance(520/7, 520/7, 0)));
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
