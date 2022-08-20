import java.awt.image.*;
import java.io.File;
import java.awt.*;
import javax.imageio.*;

public class Histogram {

	private BufferedImage image;
	
	public Histogram(BufferedImage image) {
		setImage(image);
	}
	
	public Histogram(int [][] data) {
		BufferedImage image = Utils.CreateImageFromIntArray(data);
		setImage(image);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public int [] getHistogram() {
		int [] histogram;
		if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
			histogram = new int[256];
		}else {
			histogram = new int[2024];
		}
		int [][] pixels = getPixels(image);
		
		for (int x = 0; x < pixels.length; i++) {
			for (int y = 0; y< pixels[0].length; y++) {
				histogram[pixels[x][y]]++;
			}
		}
		return histogram;
	}
	
	public int[][] getPixels(BufferedImage image){
		int w = image.getWidth();
		int h = image.getHeight();
		int [][] pixels = new int[w][h];
		
		
		return pixels;
	}
	
	

}
