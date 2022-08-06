import javax.imageio.ImageIO;
import java.io.*;
import java.io.IOException;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class ImageScale {

	public static BufferedImage loadImage(File file) throws IOException {
		return ImageIO.read(new File(file.getPath()));
	}
	
	public static void drawRectangle(BufferedImage img) {
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLUE);
		g.drawRect(10, 10, img.getWidth() - 20, img.getHeight() - 20);
	}
	
	public static JLabel drawImage(BufferedImage img)  {
		Image newImg;
		
		if(img.getWidth() > 400) {
			newImg = img.getScaledInstance(300, -1, Image.SCALE_DEFAULT);
		}else if(img.getHeight() > 200) {
			newImg = img.getScaledInstance(-1, 300, Image.SCALE_DEFAULT);
		}else {
			newImg = img;
		}
		JLabel picLabel = new JLabel(new ImageIcon(newImg));
		return picLabel;
	}
	
}
