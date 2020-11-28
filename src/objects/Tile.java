package objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tile extends JLabel {
	private String item, type, head = new File("").getAbsolutePath()+"/res/tile-images/"; //item: bat, dragon etc | type: I, L, T
	private int left, down;
	private double angle = 0;
	private BufferedImage image, copy;
	private boolean move[] = new boolean[4];

	//e.g. I/L/T, Monkey, false (might delete), 0/1/2/3, [1, 7], [1, 7]
	public Tile(String type, String item, int orientation, int left, int down) {
		this.type = type;
		this.item = item;
		this.left = left;
		this.down = down;
		try {
			image = ImageIO.read(new File(head+item+".png"));
			copy = rotateImage(image, 0.0);
		} catch(Exception e) {
			System.out.println(item+" had a problem loading");
		}

		orientation = Math.max(0, orientation); //this is because non-movable tiles default at -1 for orientation
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
	}
	public void rotate(double ang, boolean sum, boolean change) {
		if(sum) angle+=ang;
		else angle-=ang;
		copy = rotateImage(image, angle);
		repaint();

		for (int i=90;i<=ang && change;i+=90) {
			boolean temporary[] = new boolean[4];
			for (int j=0;j<4;j++) {
				if(move[j]) {
					if(sum) temporary[(j+1)%4] = true;
					else temporary[(j-1+4)%4] = true;
				}
			}
			move = temporary.clone();
		}
	}
	public BufferedImage rotateImage(BufferedImage image, double angle) {
		double rad = Math.toRadians(angle), sin = Math.abs(Math.sin(rad)), cos = Math.abs(Math.cos(rad));
		int w = image.getWidth(), h = image.getHeight();
		int nw = (int)Math.floor(w*cos+h*sin), nh = (int)Math.floor(h*cos+w*sin); //new width and height of the rotated image
		BufferedImage rot = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);

		AffineTransform at = new AffineTransform(); //this creates the transform
		at.translate((nw-w)/2, (nh-h)/2); //translate the transform to the center
		at.rotate(rad, w/2, h/2); //do the actual rotation

		Graphics2D g2d = rot.createGraphics();
		g2d.setTransform(at);
		g2d.drawImage(image, 0, 0, this);
		g2d.dispose();
		return rot; //return the newly rotated BufferedImage
	}
	public String getName() {
		return item;
	}
	public BufferedImage getImage() {
		return copy;
	}
	public boolean[] getMove() {
		return move;
	}
	public double getAngle() {
		return angle;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public String getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Tile [type=" + type + ", item=" + item + ", x=" + left + ", y=" + down + "]";
	}
}
