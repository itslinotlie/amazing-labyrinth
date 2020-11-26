package guiClasses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class StartScreenGUI extends JFrame implements ActionListener{
	
	private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
	
	private static final Color LIGHT_GREY = new Color(35,35,35);
	private final Color[] PLAYER_COLOURS = {Color.WHITE, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
	private final Font labelFont = new Font("Times New Roman", Font.BOLD, 26);
	
	private static final ImageIcon LABYRINTH_TEXT = new ImageIcon(new ImageIcon("./res/labyrinth-text.png").getImage().getScaledInstance(SCREEN_WIDTH, 200, 0));
	private static final ImageIcon LABYRINTH_TEXT2 = new ImageIcon(new ImageIcon("./res/labyrinth-text-darker.png").getImage().getScaledInstance(SCREEN_WIDTH, 200, 0));
	private static final ImageIcon SETUP_BACKGROUND = new ImageIcon("./res/images/startup-background.png");
	
	private ArrayList<Color> playerColours = new ArrayList<Color>();
	
	private JLayeredPane mainPanel = new JLayeredPane();
	private JLayeredPane customizeGamePanel = new JLayeredPane();
	
	private JPanel startGamePanel = new JPanel();
	
	private JButton playGameButton = new JButton("Play");
	private JButton continueGameButton = new JButton("Continue");
	private JButton quitGameButton = new JButton("Quit");
	
	private JButton startGameButton = new JButton("Start Game");
	private JButton goBackButton = new JButton("Go Back");
	private JButton rulesButton = new JButton("How to play");
	private JButton addPlayerButton = new JButton();
	private JButton removePlayerButton = new JButton();
	private JButton addCardButton = new JButton();
	private JButton removeCardButton = new JButton();
	
	private JLabel gameStartupTitle = new JLabel();
	private JLabel gameSetupTitle = new JLabel();
	private JLabel gameSetupBackground = new JLabel(SETUP_BACKGROUND);
	private JLabel numPlayersLabel = new JLabel("Players");
	private JLabel numCardsLabel = new JLabel("Cards (Per Player)");
	private JLabel numCardsText = new JLabel("2");
	
	private ArrayList<JPanel> playersArray = new ArrayList<JPanel>();
	
	public StartScreenGUI() {
		createPanels();
		createFrame();
	}

	private void createPanels() {
		
		createStartupPanel();
		createGameOptionsPanel();
		
		mainPanel.setLayout(null);
		mainPanel.add(startGamePanel, new Integer(1)); 
		mainPanel.add(customizeGamePanel, new Integer(0));
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.setVisible(true);
	}

	private void createStartupPanel() {
		gameStartupTitle.setIcon(LABYRINTH_TEXT);
		gameStartupTitle.setBounds(0, 0, LABYRINTH_TEXT.getIconWidth(), LABYRINTH_TEXT.getIconHeight());
		
		playGameButton.setBackground(Color.BLACK);
		playGameButton.setForeground(Color.WHITE);
		playGameButton.setFocusPainted(false);
		playGameButton.setFont(labelFont);
		playGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		playGameButton.setBounds(SCREEN_WIDTH/2 - 175, 250, 350, 40);
		playGameButton.addActionListener(this);
		
		continueGameButton.setBackground(Color.BLACK);
		continueGameButton.setForeground(Color.WHITE);
		continueGameButton.setBounds(SCREEN_WIDTH/2 - 175, 300, 350, 40);
		continueGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		continueGameButton.setFocusPainted(false);
		continueGameButton.setFont(labelFont);
		continueGameButton.addActionListener(this);
		
		quitGameButton.setBackground(Color.BLACK);
		quitGameButton.setForeground(Color.WHITE);
		quitGameButton.setBounds(SCREEN_WIDTH/2 - 175, 350, 350, 40);
		quitGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		quitGameButton.setFocusPainted(false);
		quitGameButton.setFont(labelFont);
		quitGameButton.addActionListener(this);
		
		startGamePanel.setLayout(null);
		startGamePanel.setBackground(LIGHT_GREY);
		startGamePanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		startGamePanel.add(gameStartupTitle);
		startGamePanel.add(playGameButton);
		startGamePanel.add(continueGameButton);
		startGamePanel.add(quitGameButton);	
	}

	private void createGameOptionsPanel() {
		
		gameSetupTitle.setIcon(LABYRINTH_TEXT2);
		gameSetupTitle.setBounds(0, 0, LABYRINTH_TEXT.getIconWidth(), LABYRINTH_TEXT.getIconHeight());
		
		gameSetupBackground.setIcon(SETUP_BACKGROUND);
		gameSetupBackground.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		numPlayersLabel.setBounds(SCREEN_WIDTH/2-300, 170, 200, 40);
		numPlayersLabel.setFont(labelFont);
		numPlayersLabel.setForeground(Color.WHITE);
		
		for (int i = 0; i < 2; i++) 
			addPlayer();
		
		for (int i = 0; i < 5; i++) 
			playerColours.add(PLAYER_COLOURS[i]);
	
		
		addPlayerButton.setBounds(SCREEN_WIDTH/2-300, 320, 40, 40);
		addPlayerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addPlayerButton.setIcon(new ImageIcon(new ImageIcon("./res/images/addPlayer.png").getImage().getScaledInstance(40, 40, 0)));
		addPlayerButton.setBorder(null);
		addPlayerButton.setContentAreaFilled(false);
		addPlayerButton.setFocusPainted(false);
		addPlayerButton.addActionListener(this);
		
		removePlayerButton.setBounds(SCREEN_WIDTH/2-250, 370, 40, 40);
		removePlayerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removePlayerButton.setIcon(new ImageIcon(new ImageIcon("./res/images/removePlayer.png").getImage().getScaledInstance(40, 40, 0)));
		removePlayerButton.setBorder(null);
		removePlayerButton.setVisible(false);
		removePlayerButton.setContentAreaFilled(false);
		removePlayerButton.setFocusPainted(false);
		removePlayerButton.addActionListener(this);
		
		numCardsLabel.setBounds(SCREEN_WIDTH/2+80, 170, 250, 40);
		numCardsLabel.setFont(labelFont);
		numCardsLabel.setForeground(Color.WHITE);
		
		addCardButton.setBounds(SCREEN_WIDTH/2+130, 220, 40, 40);
		addCardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addCardButton.setIcon(new ImageIcon("./res/images/upArrow.png"));
		addCardButton.setContentAreaFilled(false);
		addCardButton.setFocusPainted(false);
		addCardButton.addActionListener(this);
		
		removeCardButton.setBounds(SCREEN_WIDTH/2+130, 270, 40, 40);
		removeCardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeCardButton.setIcon(new ImageIcon("./res/images/downArrow.png"));
		removeCardButton.setContentAreaFilled(false);
		removeCardButton.setFocusPainted(false);
		removeCardButton.addActionListener(this);
		
		numCardsText.setBounds(SCREEN_WIDTH/2+180, 230, 80, 60);
		numCardsText.setFont(new Font("Times New Roman", Font.BOLD, 60));
		numCardsText.setForeground(Color.WHITE);
		
		rulesButton.setBounds(SCREEN_WIDTH/2-100, 420, 200, 40);
		rulesButton.setBackground(Color.BLACK);
		rulesButton.setForeground(Color.WHITE);
		rulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rulesButton.setFocusPainted(false);
		rulesButton.setFont(labelFont);
		rulesButton.addActionListener(this);
		
		startGameButton.setBounds(SCREEN_WIDTH/2-305, 500, 300, 40);
		startGameButton.setBackground(Color.BLACK);
		startGameButton.setForeground(Color.WHITE);
		startGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startGameButton.setFocusPainted(false);
		startGameButton.setFont(labelFont);
		startGameButton.addActionListener(this);
		
		goBackButton.setBounds(SCREEN_WIDTH/2+5, 500, 300, 40);
		goBackButton.setBackground(Color.BLACK);
		goBackButton.setForeground(Color.WHITE);
		goBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		goBackButton.setFocusPainted(false);
		goBackButton.setFont(labelFont);
		goBackButton.addActionListener(this);
		
		customizeGamePanel.setLayout(null);
		customizeGamePanel.setBackground(LIGHT_GREY);
		customizeGamePanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		customizeGamePanel.add(numPlayersLabel);
		customizeGamePanel.add(addPlayerButton);
		customizeGamePanel.add(numCardsLabel);
		customizeGamePanel.add(addCardButton);
		customizeGamePanel.add(removeCardButton);
		customizeGamePanel.add(numCardsText);
		customizeGamePanel.add(removePlayerButton);
		customizeGamePanel.add(rulesButton);
		customizeGamePanel.add(startGameButton);
		customizeGamePanel.add(goBackButton);
		customizeGamePanel.add(gameSetupTitle);
		customizeGamePanel.add(gameSetupBackground);
		customizeGamePanel.setEnabled(false);
			
	}
	
	private void addPlayer() {
		
		if (playersArray.size() >= 3) {
			addPlayerButton.setVisible(false);
			removePlayerButton.setBounds(addPlayerButton.getBounds());
		} else if (playersArray.size() == 2) {
			removePlayerButton.setVisible(true);
		}
		
		JLabel playerLabel = new JLabel("Player " + (playersArray.size() + 1));
		
		playerLabel.setBounds(0, 0, 150, 40);
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setFont(labelFont);
		playerLabel.setBorder(new LineBorder(Color.WHITE, 2));
		
		JButton playerColour = new JButton();
		
		playerColour.setBounds(160, 0, 40, 40);
		playerColour.setBackground(Color.WHITE);
		playerColour.setBorder(null);
		playerColour.addActionListener(this);
		
		JPanel playerPanel = new JPanel();
		playerPanel.setBounds(SCREEN_WIDTH/2-300, 220 + playersArray.size() * 50, 200, 50);
		playerPanel.setOpaque(false);
		playerPanel.setLayout(null);
		playerPanel.setVisible(true);
		playerPanel.add(playerLabel);
		playerPanel.add(playerColour);
		
		playersArray.add(playerPanel);
		customizeGamePanel.add(playerPanel, new Integer(1));
		
	}
	
	private void removePlayer() {
		
		JLabel player = (JLabel) playersArray.get(playersArray.size()-1).getComponent(0);
		
		for (int i = 0; i < 5; i++) {
			if (((LineBorder) player.getBorder()).getLineColor() == PLAYER_COLOURS[i]) {
				playerColours.set(i, PLAYER_COLOURS[i]);
			}
		}
		
		if (playersArray.size() == 4) {
			removePlayerButton.setBounds(SCREEN_WIDTH/2-250, 370, 40, 40);
			addPlayerButton.setBounds(SCREEN_WIDTH/2-300, 370, 40, 40);
			addPlayerButton.setVisible(true);
		} else {
			removePlayerButton.setVisible(false);
			addPlayerButton.setBounds(SCREEN_WIDTH/2-300, 320, 40, 40);
		}
		
		customizeGamePanel.remove(playersArray.get(playersArray.size()-1));
		playersArray.remove(playersArray.size()-1);	
	}
	
	private void changePlayerColour(JPanel player) {

		JLabel playerLabel = (JLabel) player.getComponent(0);
		
		int switchedIndex = -1;
		
		boolean lastColour = false;
		
		for (int i = 0; i < playerColours.size(); i++) {
			if (((LineBorder) playerLabel.getBorder()).getLineColor() == PLAYER_COLOURS[i]) {
				playerColours.set(i, PLAYER_COLOURS[i]);
				switchedIndex = i;
				break;
			}
		}
		
		for (int i = 4; i >= 0; i--) {
			if (playerColours.get(i) != null) {
				if (((LineBorder) playerLabel.getBorder()).getLineColor() == playerColours.get(i)) {
					lastColour = true;
				} else {
					lastColour = false;
				} break;
			}
		}

		for (int i = 0; i < playerColours.size(); i++) {
			if (playerColours.get(i) != null && (i > switchedIndex || lastColour)) {
				playerLabel.setBorder(new LineBorder(playerColours.get(i), 2));
				if (i != 0)
					playerColours.set(i, null);
				break;
			} 	
		}
		
		JButton playerButton = (JButton) player.getComponent(1);
		playerButton.setBackground(((LineBorder) playerLabel.getBorder()).getLineColor());
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
		
		if (event.getSource() == playGameButton) {
			startGamePanel.setVisible(false);
			customizeGamePanel.setVisible(true);
			mainPanel.setLayer(customizeGamePanel, new Integer(2));
		} else if (event.getSource() == continueGameButton) {
			System.out.println("continue");
		} else if (event.getSource() == quitGameButton){
			System.exit(0);
		} else if (event.getSource() == goBackButton) {
			startGamePanel.setVisible(true);
			customizeGamePanel.setVisible(false);
			mainPanel.setLayer(customizeGamePanel, 0);
		} else if (event.getSource() == addPlayerButton) {
			addPlayerButton.setBounds(addPlayerButton.getX(), addPlayerButton.getY() + 50, addPlayerButton.getWidth(), addPlayerButton.getHeight());
			addPlayer();
		} else if (event.getSource() == removePlayerButton) {
			removePlayer();
		} else if (event.getSource() == addCardButton) {
			if (!numCardsText.getText().equals("6")) 
			numCardsText.setText(Integer.toString((Integer.parseInt(numCardsText.getText())+1)));	
		} else if (event.getSource() == removeCardButton) {
			if (!numCardsText.getText().equals("2")) 
			numCardsText.setText(Integer.toString((Integer.parseInt(numCardsText.getText())-1)));	
		} else if (event.getSource() == startGameButton) {
			
			Color[] playerColours = new Color[playersArray.size()];
			
			for (int i = 0; i < playersArray.size(); i++) {
				if (playersArray.get(i).getComponent(1).getBackground() == PLAYER_COLOURS[0])
					return;
				else
					playerColours[i] = playersArray.get(i).getComponent(1).getBackground();
			}
		
			
			dispose();
			new LabyrinthGUI(Integer.parseInt(numCardsText.getText()), playerColours, false);
			
		}
		
		for (JPanel player : playersArray) {
			if (event.getSource() == player.getComponent(1)) {
				changePlayerColour(player);
			}
		}
		repaint();
	}
}
