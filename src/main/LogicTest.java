package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class LogicTest {
    public static void main (String[] args) {
        new LogicTest();
    }
    public LogicTest() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TestPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public class TestPanel extends JPanel {
        BufferedImage img, cpy;
        String head = new File("").getAbsolutePath(); //amazing-labyrinth
        public TestPanel() {
            try {
                img = ImageIO.read(new File(head+"/res/github-icon.png"));
                cpy = rotateImage(img, 0.0);
            } catch(Exception e) {
                System.out.println("Something went wrong ):");
            }
            Timer timer = new Timer(5, new ActionListener() {
                private double angle = 0, delta = 1;
                @Override
                public void actionPerformed(ActionEvent e) {
//                    Scanner in = new Scanner(System.in);
//                    int x = in.nextInt();
//                    while(x!=0) {
//                        System.out.println("Yo"+" "+x);
//                        if(x==90) cpy = rotateImage(img, 90);
//                        else if(x==-90) cpy = rotateImage(img, -90);
//                        repaint();
//                        x = in.nextInt();
//                    }
                    angle+=delta;
                    cpy = rotateImage(img, angle);
                    repaint();
                }
            });
            timer.start();
        }
        public Dimension getPreferredSize() {
            return img==null? new Dimension(200, 200):new Dimension(img.getWidth(), img.getHeight());
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);;
            if(cpy!=null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = (getWidth()-cpy.getWidth())/2;
                int y = (getHeight()-cpy.getHeight())/2;
                g2d.drawImage(cpy, x, y, this);
                g2d.dispose();
            }
        }
        public BufferedImage rotateImage(BufferedImage img, double angle) {
            double rad = Math.toRadians(angle), sin = Math.abs(Math.sin(rad)), cos = Math.abs(Math.cos(rad));
            int w = img.getWidth(), h = img.getHeight();
            int nw = (int)Math.floor(w*cos+h*sin), nh = (int)Math.floor(h*cos+w*sin); //implementation is left for the readers

            BufferedImage rot = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rot.createGraphics();
            AffineTransform at = new AffineTransform();
            at.translate((nw-w)/2, (nh-h)/2);

            int x = w/2, y = h/2; //center of rotation
            at.rotate(rad, x, y);
            g2d.setTransform(at);
            g2d.drawImage(img, 0, 0, this);
            g2d.dispose();
            return rot;
        }
    }

}
