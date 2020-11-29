package guiClasses;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * GUI for the start screen
 * Allows players to create a new game or continue from the latest game
 */
@SuppressWarnings("serial")
public class StartScreenGUI extends JFrame implements ActionListener{
	
	//dimensions for screen
	private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
	
	//frequently used custom color
	private static final Color LIGHT_GREY = new Color(35,35,35);
	
	//array of all player colors
	private final Color[] PLAYER_COLOURS = {Color.WHITE, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
	
	//font for all labels
	private final Font labelFont = new Font("Times New Roman", Font.BOLD, 26);
	
	//image icons used
	private static final ImageIcon LABYRINTH_TEXT = new ImageIcon(new ImageIcon("./res/labyrinth-text.png").getImage().getScaledInstance(SCREEN_WIDTH, 200, 0));
	private static final ImageIcon LABYRINTH_TEXT2 = new ImageIcon(new ImageIcon("./res/labyrinth-text-darker.png").getImage().getScaledInstance(SCREEN_WIDTH, 200, 0));
	private static final ImageIcon SETUP_BACKGROUND = new ImageIcon("./res/gui-images/startup-background.png");
	
	//array for players choice of colors
	private ArrayList<Color> playerColours = new ArrayList<Color>();
	
	//create layered panes
	private JLayeredPane mainPanel = new JLayeredPane();
	private JLayeredPane customizeGamePanel = new JLayeredPane();
	
	//create panels
	private JPanel startGamePanel = new JPanel();
	private JPanel rulesPanel = new JPanel();
	
	//buttons for the startup menu
	private JButton playGameButton = new JButton("Play");
	private JButton continueGameButton = new JButton("Continue");
	private JButton quitGameButton = new JButton("Quit");
	private JButton rulesBackButton = new JButton("Go Back");
	
	//buttons for customize game panel
	private JButton startGameButton = new JButton("Start Game");
	private JButton goBackButton = new JButton("Go Back");
	private JButton rulesButton = new JButton("How to play");
	private JButton addPlayerButton = new JButton();
	private JButton removePlayerButton = new JButton();
	private JButton addCardButton = new JButton();
	private JButton removeCardButton = new JButton();
	
	//initialize all labels
	private JLabel gameStartupTitle = new JLabel();
	private JLabel gameSetupTitle = new JLabel();
	private JLabel rulesLabel = new JLabel();
	private JLabel gameSetupBackground = new JLabel(SETUP_BACKGROUND);
	private JLabel numPlayersLabel = new JLabel("Players");
	private JLabel numCardsLabel = new JLabel("Cards (Per Player)");
	private JLabel numCardsText = new JLabel("2");
	
	//create an array of panels for each player
	private ArrayList<JPanel> playersArray = new ArrayList<JPanel>();
	
	//constructor
	public StartScreenGUI() {
		createPanels();
		createFrame();
	}

	
	private void createPanels() {
		
		createStartupPanel();
		createGameOptionsPanel();
		
		//create the main panel and add the other panels
		mainPanel.setLayout(null);
		mainPanel.add(startGamePanel, new Integer(1)); 
		mainPanel.add(customizeGamePanel, new Integer(0));
		mainPanel.add(rulesPanel, new Integer(0));
		mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainPanel.setVisible(true);
	}

	//add all items in the startup panel
	private void createStartupPanel() {
		
		//add title label
		gameStartupTitle.setIcon(LABYRINTH_TEXT);
		gameStartupTitle.setBounds(0, 0, LABYRINTH_TEXT.getIconWidth(), LABYRINTH_TEXT.getIconHeight());
		
		//add play game button
		playGameButton.setBackground(Color.BLACK);
		playGameButton.setForeground(Color.WHITE);
		playGameButton.setFocusPainted(false);
		playGameButton.setFont(labelFont);
		playGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		playGameButton.setBounds(SCREEN_WIDTH/2 - 175, 250, 350, 40);
		playGameButton.addActionListener(this);
		
		//add continue game button
		continueGameButton.setBackground(Color.BLACK);
		continueGameButton.setForeground(Color.WHITE);
		continueGameButton.setBounds(SCREEN_WIDTH/2 - 175, 300, 350, 40);
		continueGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		continueGameButton.setFocusPainted(false);
		continueGameButton.setFont(labelFont);
		continueGameButton.addActionListener(this);
		
		//add quit game button
		quitGameButton.setBackground(Color.BLACK);
		quitGameButton.setForeground(Color.WHITE);
		quitGameButton.setBounds(SCREEN_WIDTH/2 - 175, 350, 350, 40);
		quitGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		quitGameButton.setFocusPainted(false);
		quitGameButton.setFont(labelFont);
		quitGameButton.addActionListener(this);
		
		//create start game panel and add all components
		startGamePanel.setLayout(null);
		startGamePanel.setBackground(LIGHT_GREY);
		startGamePanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		startGamePanel.add(gameStartupTitle);
		startGamePanel.add(playGameButton);
		startGamePanel.add(continueGameButton);
		startGamePanel.add(quitGameButton);	
	}

	//set up game options panel
	private void createGameOptionsPanel() {
		
		//create rules label
		rulesLabel.setBounds(30, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		rulesLabel.setIcon(new ImageIcon("./res/gui-images/rules.png"));
		rulesLabel.setBackground(Color.BLACK);
		rulesLabel.setOpaque(true);
		
		//create button to exit rules page
		rulesBackButton.setBounds(SCREEN_WIDTH/2-75, 360, 150, 40);
		rulesBackButton.setBackground(Color.BLACK);
		rulesBackButton.setForeground(Color.GREEN);
		rulesBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rulesBackButton.setFocusPainted(false);
		rulesBackButton.setFont(labelFont);
		rulesBackButton.addActionListener(this);
		
		//create panel for rules label and add rules label buttons
		rulesPanel.setBounds(0, -20, SCREEN_WIDTH, SCREEN_HEIGHT);
		rulesPanel.setBackground(Color.BLACK);
		rulesPanel.setLayout(null);
		rulesPanel.add(rulesBackButton);
		rulesPanel.add(rulesLabel);
		
		//create title label
		gameSetupTitle.setIcon(LABYRINTH_TEXT2);
		gameSetupTitle.setBounds(0, 0, LABYRINTH_TEXT.getIconWidth(), LABYRINTH_TEXT.getIconHeight());
		
		//create background image
		gameSetupBackground.setIcon(SETUP_BACKGROUND);
		gameSetupBackground.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//create player number header
		numPlayersLabel.setBounds(SCREEN_WIDTH/2-300, 170, 200, 40);
		numPlayersLabel.setFont(labelFont);
		numPlayersLabel.setForeground(Color.WHITE);
		
		//add 2 players (default value)
		for (int i = 0; i < 2; i++) 
			addPlayer();
		
		for (int i = 0; i < 5; i++) 
			playerColours.add(PLAYER_COLOURS[i]);
	
		//create button to add more players
		addPlayerButton.setBounds(SCREEN_WIDTH/2-300, 320, 40, 40);
		addPlayerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addPlayerButton.setIcon(new ImageIcon(new ImageIcon("./res/gui-images/addPlayer.png").getImage().getScaledInstance(40, 40, 0)));
		addPlayerButton.setBorder(null);
		addPlayerButton.setContentAreaFilled(false);
		addPlayerButton.setFocusPainted(false);
		addPlayerButton.addActionListener(this);
		
		//create button to remove players 
		removePlayerButton.setBounds(SCREEN_WIDTH/2-250, 370, 40, 40);
		removePlayerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removePlayerButton.setIcon(new ImageIcon(new ImageIcon("./res/gui-images/removePlayer.png").getImage().getScaledInstance(40, 40, 0)));
		removePlayerButton.setBorder(null);
		removePlayerButton.setVisible(false);
		removePlayerButton.setContentAreaFilled(false);
		removePlayerButton.setFocusPainted(false);
		removePlayerButton.addActionListener(this);
		
		//create number of cards header 
		numCardsLabel.setBounds(SCREEN_WIDTH/2+80, 170, 250, 40);
		numCardsLabel.setFont(labelFont);
		numCardsLabel.setForeground(Color.WHITE);
		
		//button to add cards
		addCardButton.setBounds(SCREEN_WIDTH/2+130, 220, 40, 40);
		addCardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addCardButton.setIcon(new ImageIcon("./res/gui-images/upArrow.png"));
		addCardButton.setContentAreaFilled(false);
		addCardButton.setFocusPainted(false);
		addCardButton.addActionListener(this);
		
		//button to remove cards
		removeCardButton.setBounds(SCREEN_WIDTH/2+130, 270, 40, 40);
		removeCardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeCardButton.setIcon(new ImageIcon("./res/gui-images/downArrow.png"));
		removeCardButton.setContentAreaFilled(false);
		removeCardButton.setFocusPainted(false);
		removeCardButton.addActionListener(this);
		
		//display the number of cards
		numCardsText.setBounds(SCREEN_WIDTH/2+180, 230, 80, 60);
		numCardsText.setFont(new Font("Times New Roman", Font.BOLD, 60));
		numCardsText.setForeground(Color.WHITE);
		
		//create rules page button
		rulesButton.setBounds(SCREEN_WIDTH/2-100, 420, 200, 40);
		rulesButton.setBackground(Color.BLACK);
		rulesButton.setForeground(Color.WHITE);
		rulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rulesButton.setFocusPainted(false);
		rulesButton.setFont(labelFont);
		rulesButton.addActionListener(this);
		
		//button to start game
		startGameButton.setBounds(SCREEN_WIDTH/2-305, 500, 300, 40);
		startGameButton.setBackground(Color.BLACK);
		startGameButton.setForeground(Color.WHITE);
		startGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startGameButton.setFocusPainted(false);
		startGameButton.setFont(labelFont);
		startGameButton.addActionListener(this);
		
		//button to go back to start screen
		goBackButton.setBounds(SCREEN_WIDTH/2+5, 500, 300, 40);
		goBackButton.setBackground(Color.BLACK);
		goBackButton.setForeground(Color.WHITE);
		goBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		goBackButton.setFocusPainted(false);
		goBackButton.setFont(labelFont);
		goBackButton.addActionListener(this);
		
		//add all elements to the panel
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
		
		//check the number of players to make sure it is going to be between 2 and 4
		if (playersArray.size() >= 3) {
			addPlayerButton.setVisible(false);
			removePlayerButton.setBounds(addPlayerButton.getBounds());
		} else if (playersArray.size() == 2) {
			removePlayerButton.setVisible(true);
		}
		
		//add a new label for the player
		JLabel playerLabel = new JLabel("Player " + (playersArray.size() + 1));
		
		playerLabel.setBounds(0, 0, 150, 40);
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setFont(labelFont);
		playerLabel.setBorder(new LineBorder(Color.WHITE, 2));
		
		//add a new button for the player
		JButton playerColour = new JButton();
		
		playerColour.setBounds(160, 0, 40, 40);
		playerColour.setBackground(Color.WHITE);
		playerColour.setBorder(null);
		playerColour.addActionListener(this);
		
		//create a panel to contain the components
		JPanel playerPanel = new JPanel();
		playerPanel.setBounds(SCREEN_WIDTH/2-300, 220 + playersArray.size() * 50, 200, 50);
		playerPanel.setOpaque(false);
		playerPanel.setLayout(null);
		playerPanel.setVisible(true);
		playerPanel.add(playerLabel);
		playerPanel.add(playerColour);
		
		//add the panel to the array and screen
		playersArray.add(playerPanel);
		customizeGamePanel.add(playerPanel, new Integer(1));
		
	}
	
	//remove a player
	private void removePlayer() {
		
		//find the last player added to remove
		JLabel player = (JLabel) playersArray.get(playersArray.size()-1).getComponent(0);
		
		//allow other players to use the color of the current player being removed
		for (int i = 0; i < 5; i++) {
			if (((LineBorder) player.getBorder()).getLineColor() == PLAYER_COLOURS[i]) {
				playerColours.set(i, PLAYER_COLOURS[i]);
			}
		}
		
		//check if the number of players will be between 2-4 and remove the option to add/remove players if so
		if (playersArray.size() == 4) {
			removePlayerButton.setBounds(SCREEN_WIDTH/2-250, 370, 40, 40);
			addPlayerButton.setBounds(SCREEN_WIDTH/2-300, 370, 40, 40);
			addPlayerButton.setVisible(true);
		} else {
			removePlayerButton.setVisible(false);
			addPlayerButton.setBounds(SCREEN_WIDTH/2-300, 320, 40, 40);
		}
		
		//remove the player from the array and screen
		customizeGamePanel.remove(playersArray.get(playersArray.size()-1));
		playersArray.remove(playersArray.size()-1);	
	}
	
	//changes the players color
	private void changePlayerColour(JPanel player) {

		//finds the player label
		JLabel playerLabel = (JLabel) player.getComponent(0);
		
		int switchedIndex = -1;

		boolean lastColour = false;
		
		//adds the players current color back into the array of colors
		//switch index indicates where the last color the player had was to prevent them from constantly getting the same color
		for (int i = 0; i < playerColours.size(); i++) {
			if (((LineBorder) playerLabel.getBorder()).getLineColor() == PLAYER_COLOURS[i]) {
				playerColours.set(i, PLAYER_COLOURS[i]);
				switchedIndex = i;
				break;
			}
		}
		
		//check if the player has already been through all the colors in the array
		//if so, they are able to change their color to the first color in the array
		for (int i = 4; i >= 0; i--) {
			if (playerColours.get(i) != null) {
				if (((LineBorder) playerLabel.getBorder()).getLineColor() == playerColours.get(i)) {
					lastColour = true;
				} else {
					lastColour = false;
				} break;
			}
		}

		//set the color of the player and indicate it by highlighting the label
		for (int i = 0; i < playerColours.size(); i++) {
			if (playerColours.get(i) != null && (i > switchedIndex || lastColour)) {
				playerLabel.setBorder(new LineBorder(playerColours.get(i), 2));
				if (i != 0)
					playerColours.set(i, null);
				break;
			} 	
		}
		
		//change the color of the button to the same as the player's border
		JButton playerButton = (JButton) player.getComponent(1);
		playerButton.setBackground(((LineBorder) playerLabel.getBorder()).getLineColor());
	}

	//create the frame and add the main panel
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
		
		//confirm with the user and remove all previous saved games
		//open the customize game panel screen
		if (event.getSource() == playGameButton) {
			
			int option = JOptionPane.showConfirmDialog(playGameButton, "Creating a new game will delete all previous data."
					+ "\nDo you wish to continue", "warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				
			try {
				PrintWriter saveWriter = new PrintWriter(new FileWriter("saveGame.txt", false));
				saveWriter.write(""); // default new game values for the player
				saveWriter.close();

			} catch (IOException e) {
				e.printStackTrace();
				}
			
			startGamePanel.setVisible(false);
			customizeGamePanel.setVisible(true);
			mainPanel.setLayer(customizeGamePanel, new Integer(2));
			} 
				
		//check if there is a saved game and load it 
		} else if (event.getSource() == continueGameButton) {

			try {
				Scanner input = new Scanner(new File("saveGame.txt"));
				
				if (input.hasNext()) {
					dispose();
					new LabyrinthGUI(0, null, true);
				} else {
					JOptionPane.showConfirmDialog(playGameButton, "You do not have a game currently saved",
							"No Game Found", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				
				input.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		//exits the program
		} else if (event.getSource() == quitGameButton){
			System.exit(0);
		
		//hide the customize game panel and display the startup panel
		} else if (event.getSource() == goBackButton) {
			
			startGamePanel.setVisible(true);
			customizeGamePanel.setVisible(false);
			mainPanel.setLayer(customizeGamePanel, 0);
		
		} else if (event.getSource() == addPlayerButton) { //add a player
			
			addPlayerButton.setBounds(addPlayerButton.getX(), addPlayerButton.getY() + 50, addPlayerButton.getWidth(), addPlayerButton.getHeight());
			addPlayer();
			
		} else if (event.getSource() == removePlayerButton) { //remove a player		
			removePlayer();
			
		} else if (event.getSource() == addCardButton) { //add a card
			
			if (!numCardsText.getText().equals("6")) 
			numCardsText.setText(Integer.toString((Integer.parseInt(numCardsText.getText())+1)));	
		
		} else if (event.getSource() == removeCardButton) { //remove a card
			
			if (!numCardsText.getText().equals("2")) 
			numCardsText.setText(Integer.toString((Integer.parseInt(numCardsText.getText())-1)));	
		
		} else if (event.getSource() == rulesButton){ //open the rules panel
			
			mainPanel.setLayer(customizeGamePanel, new Integer(1));
			mainPanel.setLayer(rulesPanel, new Integer(2));
			
			customizeGamePanel.setVisible(false);
			rulesPanel.setVisible(true);
			
		} else if (event.getSource() == rulesBackButton) { //go back to customize game panel
			
			mainPanel.setLayer(rulesPanel, new Integer(1));
			mainPanel.setLayer(customizeGamePanel, new Integer(2));
			
			customizeGamePanel.setVisible(true);
			rulesPanel.setVisible(false);
			
		} else if (event.getSource() == startGameButton) { //start a new game if all players selected a color
			
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
		
		//check if a player is trying to change their color
		for (JPanel player : playersArray) {
			if (event.getSource() == player.getComponent(1)) {
				changePlayerColour(player);
			} 
		}	
		repaint();
	}
}
