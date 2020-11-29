package guiClasses;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import objects.Player;

/**
 * GUI for the end screen
 * Places the top 3 players on a podium
 */
public class EndScreenGUI extends JFrame {
    private static final int SCREEN_WIDTH = 900, SCREEN_HEIGHT = 655;
    private JLabel gameSetupBackground = new JLabel();
    private JLabel podium = new JLabel(), decor = new JLabel();
    private JLabel player [] = new JLabel[3];

    private JPanel bg = new JPanel();

    private ArrayList<Player> winners;

    public EndScreenGUI(ArrayList<Player> winners) {
        this.winners = winners;
        createPanel();
        createFrame();
    }
    private void createPanel() {
        podium.setIcon(new ImageIcon(new ImageIcon("./res/gui-images/podium.png").getImage().getScaledInstance(800, 800, 0)));
        podium.setBounds(40, 0, 1000, 1000);

        gameSetupBackground.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        gameSetupBackground.setIcon(new ImageIcon("./res/gui-images/windows.jpg"));

        for (int i=0;i<Math.min(3, winners.size());i++) {
            player[i] = new JLabel();
            player[i].setIcon(new ImageIcon(new ImageIcon("./res/gui-images/player"+colorToInt(winners.get(i).getColour())+".png").getImage().getScaledInstance(200, 200, 0)));
        }
        
        for (int i=0;i<Math.min(3, winners.size());i++) {
            if(i==0) player[0].setBounds(360, 50, 500, 500);
            else if(i==1) player[1].setBounds(160, 130, 500, 500);
            else if(i==2) player[2].setBounds(560, 160, 500, 500);
        }


        decor.setIcon(new ImageIcon("./res/gui-images/confetti.png"));
        decor.setBounds(50, 0, 1000, 300);

        bg.add(decor);

        for(int i=0;i<Math.min(3, winners.size());i++) {
            bg.add(player[i]);
        }


        bg.add(podium);
        bg.add(gameSetupBackground);
        bg.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        bg.setLayout(null);
        add(bg);
    }
    private void createFrame() {
        setLayout(null);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTitle("Amazing Labyrinth");
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        repaint();
    }
    private int colorToInt(Color color) {
        if(color.getRed() == 255 && color.getBlue() == 0 && color.getGreen() == 0) return 0;
        else if(color.getRed() == 0 && color.getBlue() == 255 && color.getGreen() == 0) return 1;
        else if(color.getRed() == 255 && color.getBlue() == 0 && color.getGreen() == 255) return 2;
        else return 3;
    }
}
