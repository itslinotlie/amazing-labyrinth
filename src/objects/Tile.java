package objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tile extends JLabel {
	private String item, type, head = new File("").getAbsolutePath()+"/res/images/"; //item: bat, dragon etc | type: I, L, T
	private int x, y;
	private double angle = 0;
	private BufferedImage img, cpy;
	private boolean move[] = new boolean[4];

	//e.g. I/L/T, Monkey, false (might delete), 0/1/2/3, [1, 7], [1, 7]
	public Tile(String type, String item, int orientation, int x, int y) {
		this.type = type;
		this.item = item;
		this.x = x; this.y = y;
		if(type.equals("I")) {
			for (int i=0;i<=2;i+=2) {
				move[(orientation+i)%4] = true;
			}
		} else if(type.equals("L")) {
			for (int i=0;i<=1;i++) {
				move[(orientation+i)%4] = true;
			}
		} else if(type.equals("T")) {
			for (int i=1;i<=3;i++) {
				move[(orientation+i)%4] = true;
			}
		}

		try {
			img = ImageIO.read(new File(head+"/res/github-icon.png"));
			cpy = rotateImage(img, 0.0);
		} catch(Exception e) {
			System.out.println("StackOverflow wants you to search why file no load");
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
