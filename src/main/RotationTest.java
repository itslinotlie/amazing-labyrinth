package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class RotationTest {

    public TestPanel panel = new TestPanel();

    public static void main(String[] args) { //someone deleted this - .-
        RotationTest test = new RotationTest();

        try( Scanner in = new Scanner(System.in)) { // try with resources to avoid memory leak

            int value = in.nextInt();

            while(value!=0) {
                test.rotate(value);
                value = in.nextInt();
            }
        }
    }

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
       
        BufferedImage image;
        BufferedImage imageCopy;
        String head = new File("").getAbsolutePath(); //amazing-labyrinth
        public double angle = 0;

        public TestPanel() {
            try {
                image = ImageIO.read(new File(head+"/res/github-icon.png"));
                imageCopy = rotateImage(image, 0.0);
            } catch(Exception e) {
                System.out.println("Something went wrong ):");
            }  repaint();
        }


        public void rotate(double ang) {
            for (int i = 0; i < ang; i++) {
                try {
                    Thread.sleep(10);
                    angle += 1;
                    imageCopy = rotateImage(image, angle);
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public BufferedImage rotateImage(BufferedImage image, double angle) {

            double rad = Math.toRadians(angle);
            double sin = Math.abs(Math.sin(rad));
            double cos = Math.abs(Math.cos(rad));
            
            int new_width = (int) Math.floor ( //width of the rotated image
                (image.getWidth() * cos) + 
                (image.getHeight() * sin)
            ); 

            int new_height = (int) Math.floor ( //height of the rotated image
                (image.getHeight() * cos) + 
                (image.getWidth() * sin)
            ); 

            BufferedImage rot = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rot.createGraphics();
            AffineTransform aTransform = new AffineTransform();

            aTransform.translate ( 
                ((new_width - image.getWidth()) / 2),
                ((new_height - image.getHeight()) / 2)
            );

            int xAxis = (image.getWidth() / 2);
            int yAxis = (image.getHeight() / 2); //center of rotation

            aTransform.rotate(rad, xAxis, yAxis);

            g2d.setTransform(aTransform);
            g2d.drawImage(image, 0, 0, this);
            g2d.dispose();

            return rot; //return the newly rotated BufferedImage
        }


        @Override
        public Dimension getPreferredSize() {
            return image == null ? new Dimension(200, 200):new Dimension(image.getWidth(), image.getHeight());
        }


        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            if(imageCopy != null) {
                Graphics2D g2d = (Graphics2D) graphic.create();
                int x = (getWidth() - imageCopy.getWidth())/2;
                int y = (getHeight() - imageCopy.getHeight())/2;
                g2d.drawImage(imageCopy, x, y, this);
                g2d.dispose();
            }
        }
    }
}
