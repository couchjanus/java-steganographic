import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.util.Iterator;
import java.io.*;
import java.net.URLConnection;
import java.net.FileNameMap;

public class ImageUtils {
	
	/**Creates an imnage from a 2D array of integers*/
    public static BufferedImage CreateImagefromIntArray(int[][] pixels){
        
        int i,j;
        i=0; j=0;
        
        short[] pixelshortArray = new short[pixels.length * pixels[0].length];
        int min = Utils.getMin(pixels);
        int max = Utils.getMax(pixels);
        int gray;

        System.out.println("rescaling output image: ");
            for (int x = 0; x < pixels[0].length; x++ ) {
                for (int y = 0; y < pixels.length; y++ ) {
                gray = pixels[x][y];  
                if (gray < 0){
                    pixelshortArray[x + y * pixels.length] = 0;
                } else{ 
                    pixelshortArray[x + y * pixels.length] = (short) gray;
                }
            }
        }
        // returns an 8-bit buffered image for display purposes
        BufferedImage img = getGrayBufferedImage(pixelshortArray, pixels.length,
        pixels[0].length, min, max);
        return img; 
    }
    
    public static void toGrayBytes(byte[] grayBytes, short[] shortBuffer, int imgMin, int imgMax) {
        final int displayMin = 0; // Black is zero
        final int displayMax = 255; //(2 << 7) - 1; // For 8 bits per sample (255)
        final float displayRatio = (float) (displayMax - displayMin) / (imgMax - imgMin);
        
        for (int i = 0; i < shortBuffer.length; ++i) {
            int in = shortBuffer[i];
            int out;
            if (in < imgMin)
                out = displayMin;
            else if (in > imgMax)
                out = displayMax;
            else
                out = (int) ((in - imgMin)* displayRatio);
            grayBytes[i] = (byte) out;
        }
    }
    
    public static BufferedImage getGrayBufferedImage(short[]buf, int width, int height, int imgMin, int imgMax) {
		if (buf.length != width*height)
			throw new IllegalArgumentException(width + " * " + height + " != " + buf.length);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		byte[] data = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		toGrayBytes(data, buf, imgMin, imgMax);
		return image;
   }
    
    public static BufferedImage loadImage(String fileName) {
    	BufferedImage buffImage = null;
    	
    	try {
    		File f = new File(fileName);
    		buffImage = ImageIO.read(f);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return buffImage;
    	
    }
    
    public static void imgChannel(BufferedImage src, String colorChannel) {
    	
    	int r, g, b, a;
    	
    	BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB) ;
    	Graphics graph = image.createGraphics();
    	graph.drawImage(src, 0, 0, null);
    	
    	for (int y = 0; y < image.getHeight(); y++) {
    		for (int x = 0; x < image.getWidth(); x++) {
    			int p = image.getRGB(x, y);
    			a = (p >> 24) & 0xff;
    			
    			if (colorChannel == "red") {
    				r = (p >> 16) & 0xff;
    				p = (a << 24) | (r << 16) | (0 << 8) | 0;
    			}
    			else if (colorChannel == "green") {
    				g = (p >> 8) & 0xff;
    				p = (a << 24) | (0 << 16) | (g << 8) | 0;
    			}
    			else if (colorChannel == "blue") {
    				b = p & 0xff;
    				p = (a << 24) | (0 << 16) | (0 << 8) | b;
    			}
    			image.setRGB(x, y, p);
    		}
    	}
    	
    	try {
    		File fout = new File(colorChannel + ".jpg");
    		ImageIO.write(image, "jpg", fout);
    	}catch(IOException ex) {
    		System.out.println(ex);
    	}
    }
    
    public static int[] getSplitRGB(BufferedImage image, int x, int y) {
    	int [] rgb = null;
    	if (image != null && x < image.getWidth() && y < image.getHeight()) {
    		rgb = new int[3];
    		int pixel = image.getRGB(x,  y);
    		rgb = getSplitRGB(pixel);
    	}
    	return rgb;
    }
    public static int[] getSplitRGB(int pixel) {
    	int [] rgbs = new int[3];
    	rgbs[0] = (pixel & 0xff0000) >> 16;
    	rgbs[1] = (pixel & 0xff00) >> 8;
    	rgbs[2] = (pixel & 0xff);
    	return rgbs;
    }
    
    public static String getMimeType(String file) throws java.io.IOException {
    	FileNameMap fileName = URLConnection.getFileNameMap();
    	String type = fileName.getContentTypeFor(file);
    	return type;
    }
}
