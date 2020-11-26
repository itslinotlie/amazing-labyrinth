package objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class Tile extends JLabel {
	private String item, type, head = new File("").getAbsolutePath()+"/res/tile-images/"; //item: bat, dragon etc | type: I, L, T
	private int x, y;
	private double angle = 0;
	private BufferedImage img, cpy;
	private boolean move[] = new boolean[4];

	//e.g. I/L/T, Monkey, false (might delete), 0/1/2/3, [1, 7], [1, 7]
	public Tile(String type, String item, int orientation, int x, int y) {
		this.type = type;
		this.item = item;
		this.x = x; this.y = y;
		try {
			img = ImageIO.read(new File(head+item+".png"));
			cpy = rotateImage(img, 0.0);
		} catch(Exception e) {
			System.out.println(item+" had a problem loading");
			System.out.println(head);
		}
		orientation = Math.max(0, orientation);

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
		for (int i=1;i<=ang;i++) {
			if(sum) angle++;
			else angle--;
			cpy = rotateImage(img, angle);
			repaint();
			if(change && i%90==0) {
				boolean tmp[] = new boolean[4];
				for (int j=0;j<4;j++) {
					if(move[j]) {
						tmp[(j+1)%4] = true;
					}
				}
				move = tmp.clone();
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

		at.rotate(rad, w/2, h/2); //center of rotation
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
	public String getName() {
		return item;
	}
	public BufferedImage getImg() {
		return cpy;
	}
	public boolean[] getMove() {
		return move;
	}
	public double getAngle() {
		return angle;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Tile [type=" + type + ", item=" + item + ", x=" + x + ", y=" + y + "]";
	}
}
