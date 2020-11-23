package objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tile extends JLabel {
	private String item, type, head = new File("").getAbsolutePath()+"/res/images/"; //item: bat, dragon etc | type: I, L, T
	private boolean moveable; //don't think we need, GUI will do the checking
	private int x, y;
	private double angle = 0;
	private BufferedImage image, copy;

	private boolean move[] = new boolean[4];

	//e.g. I/L/T, Monkey, false (might delete), 0/1/2/3, [1, 7], [1, 7]
	public Tile(String type, String item, boolean moveable, int orientation, int x, int y) {
		this.type = type;
		this.item = item;
		this.moveable = moveable;
		this.x = x; this.y = y;
		switch(type) {
			case "I":
				for (int i=0;i<=2;i+=2) {
					move[(orientation + i) % 4] = true;
				} break;
			case "L":
				for (int i=0;i<=1;i++) {
					move[(orientation + i) % 4] = true;
				} break;
			case "T":
				for (int i=1;i<=3;i++) {
					move[(orientation + i) % 4] = true;
				} break;
		}

		try { //loading the images (might need to change Fernande's naming convention (I think the numbers are redundant)
			image = ImageIO.read(new File(head+type+".png")); //i.e. amazing-labyrinth/res/images/Monkey.png
			copy = rotateImage(image, 0d);
		} catch(Exception e) {
			System.out.println("There was a problem loading the image");
		}
	}
	public void rotate(double ang) { //this might have to be transferred to the GUI class
		for (int i=0;i<ang;i++) {
			try {
				Thread.sleep(10);
				angle+=1;
				copy = rotateImage(image, angle);
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
		g2d.drawImage(image, 0, 0, this);
		g2d.dispose();
		return rot; //return the newly rotated BufferedImage
	}
	@Override
	public Dimension getPreferredSize() {
		return image==null? new Dimension(200, 200):new Dimension(image.getWidth(), image.getHeight());
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(copy!=null) {
			Graphics2D g2d = (Graphics2D) g.create();
			int x = (getWidth()-copy.getWidth())/2;
			int y = (getHeight()-copy.getHeight())/2;
			g2d.drawImage(copy, x, y, this);
			g2d.dispose();
		}
	}
	public String getType() {
		return type;
	}
	public String getItem() {
		return item;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		return "Tile [type=" + type + ", item=" + item + ", x=" + x + ", y=" + y + "]";
	}
}
