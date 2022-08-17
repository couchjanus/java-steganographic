import javax.imageio.ImageIO;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class ImageScale {

	private BufferedImage image;
	
	public ImageScale(int w, int h) {
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}
	public ImageScale(BufferedImage image) {
		this.image = image;
	}
	
	public ImageScale(String name) {
		this(new File(name));
	}
	
	public ImageScale(File file) {
		try {
			image = ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not open file "+file);
		}
		if(image == null)
		{
			throw new RuntimeException("Invalid image file "+file);
		}
	}
	
	public static BufferedImage loadImage(File file) throws IOException {
		return ImageIO.read(new File(file.getPath()));
	}
	
	public static void drawRectangle(BufferedImage img) {
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLUE);
		g.drawRect(10, 10, img.getWidth() - 20, img.getHeight() - 20);
	}
	
	public JLabel drawImage(int w, int h)  {
		Image newImg;
		
		if(image.getWidth() > w) {
			newImg = image.getScaledInstance(w, -1, Image.SCALE_DEFAULT);
		}else if(image.getHeight() > h) {
			newImg = image.getScaledInstance(-1, h, Image.SCALE_DEFAULT);
		}else {
			newImg = image;
		}
		JLabel picLabel = new JLabel(new ImageIcon(newImg));
		return picLabel;
	}
	
}
