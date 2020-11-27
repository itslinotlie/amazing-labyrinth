package objects;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Card {
	private String head = new File("").getAbsolutePath()+"/res/card-images/";
	private String item;
	private BufferedImage image;
	
	public Card(String item) {
		this.item = item;
		try {
			image = ImageIO.read(new File(head + item + ".png"));
		} catch(Exception e) {
			System.out.println(item + " had a problem loading");
		}
	}
	
	public String getItem() {
		return item;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "Card [item=" + item + "]";
	}
}
