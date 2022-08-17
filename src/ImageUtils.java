import java.awt.image.*;
import java.io.File;
import java.awt.*;
import javax.imageio.*;

public class ImageUtils {

//	public ImageUtils() {
//		// TODO Auto-generated constructor stub
//	}
	
	public BufferedImage convertImage(BufferedImage srcImage) {
		int targetType = srcImage.getType();
		
		switch (targetType) {
		case BufferedImage.TYPE_INT_BGR:
		case BufferedImage.TYPE_INT_RGB:	
			targetType = BufferedImage.TYPE_3BYTE_BGR;
			break;
		case BufferedImage.TYPE_INT_ARGB:
		case BufferedImage.TYPE_CUSTOM:
			targetType = BufferedImage.TYPE_4BYTE_ABGR;
			break;
		case BufferedImage.TYPE_INT_ARGB_PRE:
			targetType = BufferedImage.TYPE_4BYTE_ABGR_PRE;
			break;
		default: throw new RuntimeException("Error: unknown TYPE " + targetType);
		}
		BufferedImage targetImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), targetType);
		Graphics g = targetImage.getGraphics();
		g.drawImage(srcImage, 0, 0, null);
		g.dispose();
		return targetImage;
	}
	
	public byte[] getByteCode(BufferedImage srcImage) {
		WritableRaster raster = srcImage.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}
	
	public BufferedImage copyImage(BufferedImage srcImage) {
		ColorModel colorModel = srcImage.getColorModel();
		boolean isAlphaPremultiplied = srcImage.isAlphaPremultiplied();
		WritableRaster raster = srcImage.copyData(null);
		BufferedImage targetImage = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
		return targetImage;
	}
	
	public BufferedImage thresholdImage(BufferedImage srcImage, int threshold) {
		BufferedImage result = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		result.getGraphics().drawImage(srcImage, 0, 0, null);
		WritableRaster raster = srcImage.getRaster();
		int[] pixels = new int[srcImage.getWidth()];
		for (int y = 0; y < srcImage.getHeight(); y++) {
			raster.getPixels(0, y, srcImage.getWidth(), 1, pixels);
			for (int i = 0; i < pixels.length; i++) {
				if (pixels[i] < threshold) {
					pixels[i] = 0;		
				}else {
					pixels[i] = 255;
				}
			}
			raster.setPixels(0, y, srcImage.getWidth(), 1, pixels);
		}
		return result;
	}
	
	public BufferedImage cropImage(BufferedImage srcImage, int w, int h) {
		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = result.getGraphics();
		g.drawImage(srcImage, 0, 0, null);
		g.dispose();
		return result;
	}
	
	public void saveImage(BufferedImage image, File file) {
		String name = file.getName();
		String ext = name.substring(name.lastIndexOf('.') + 1);
		try {
			ImageIO.write(image, ext, file);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void converToGrayscvale(BufferedImage image) {
		int h = image.getHeight();
		int w = image.getWidth();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int pixel = image.getRGB(x, y);
				int a = (pixel >> 24) & 0xff;
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				int avg = (r + b + g) / 3;
				pixel = (a << 24) | (avg << 16) | (avg << 8) | avg;
				image.setRGB(x, y, pixel);
			}
		}
	}
	
	
	
	public void lsbDecode(BufferedImage image) {
		int h = image.getHeight();
		int w = image.getWidth();
		int currentBit = 0;
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int pixel = image.getRGB(x, y);
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				
//				int avg = (r + b + g) / 3;
				pixel = (a << 24) | (avg << 16) | (avg << 8) | avg;
				image.setRGB(x, y, pixel);
			}
		}
	}
}
