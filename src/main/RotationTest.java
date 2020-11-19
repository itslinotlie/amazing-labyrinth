package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class RotationTest {
    public static void main (String[] args) {
        RotationTest x = new RotationTest();
        //testing the feature
        Scanner in = new Scanner(System.in);
        int val = in.nextInt(); //deg to rotate CW (increments by that amount) in degree
        while(val!=0) {
            x.rotate(val);
            val = in.nextInt();
        }
    }
    public TestPanel panel = new TestPanel();
    public RotationTest() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void rotate(double ang) {
        panel.rotate(ang);
    }
    public class TestPanel extends JPanel {
        BufferedImage img, cpy;
        String head = new File("").getAbsolutePath(); ///amazing-labyrinth
        public double angle = 0;
        public TestPanel() {
            try {
                img = ImageIO.read(new File(head+"/res/github-icon.png"));
                cpy = rotateImage(img, 0.0);
            } catch(Exception e) {
                System.out.println("Something went wrong ):");
            } repaint();
        }
        public void rotate(double ang) {
            for (int i=0;i<ang;i++) {
                try {
                    Thread.sleep(10);
                    angle+=1;
                    cpy = rotateImage(img, angle);
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public BufferedImage rotateImage(BufferedImage img, double angle) {
            double rad = Math.toRadians(angle), sin = Math.abs(Math.sin(rad)), cos = Math.abs(Math.cos(rad));
            int w = img.getWidth(), h = img.getHeight();
            //new width and height of the rotated image
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
            return rot; //return the newly rotated BufferedImage
        }
        @Override
        public Dimension getPreferredSize() {
            return img==null? new Dimension(200, 200):new Dimension(img.getWidth(), img.getHeight());
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(cpy!=null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = (getWidth()-cpy.getWidth())/2;
                int y = (getHeight()-cpy.getHeight())/2;
                g2d.drawImage(cpy, x, y, this);
                g2d.dispose();
            }
        }
    }
}
