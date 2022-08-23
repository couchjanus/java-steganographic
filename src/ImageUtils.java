import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.util.Iterator;
import java.io.*;

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
    
}
