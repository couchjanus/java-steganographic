
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.image.*;

/* Utils.java is used by FileChooserDemo2.java. */
public class Utils {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public static BufferedImage CreateImageFromIntArray(int [][] pixels) {
    	
    	short[] pixelShortArray = new short[pixels.length * pixels[0].length];
    	
    	int min = getMin(pixels);
    	int max = getMax(pixels);
    	
    	int res;
    	
    	for (int x = 0; x < pixels[0].length; x++) {
    		for (int y = 0; y < pixels.length; y++) {
    			res = pixels[x][y];
    			if (res < 0) {
    				pixelShortArray[x + y*pixels.length] = 0;
    			}else {
    				pixelShortArray[x + y*pixels.length] = (short) res;
    			}
    		}
    	}
    	
    	BufferedImage image = getBufferedImage(pixelShortArray, pixels.length, pixels[0].length, min, max);
    	
    	return image;
    }
    
    public static BufferedImage getBufferedImage(short buffer[], int w, int h, int iMin, int iMax) {
    	BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
    	byte[] data = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
    	toGrayBytes(data, buffer, iMin, iMax);
    	return image;
    }
    
    public static void toGrayBytes(byte[] grayByte, short[] shortBuffer, int iMin, int iMax) {
    	final int dMin = 0;
    	final int dMax = 255;
    	
    	final float aR = (float) (dMax - dMin) / (iMax - iMin);
    	
    	for (int i = 0; i < shortBuffer.length; ++i) {
    		int in = shortBuffer[i];
    		int out;
    		if (in < iMin) {
    			out = dMin;
    		} else if (in > iMax) {
    			out = dMax;
    		}else {
    			out = (int) ((in - iMin)*aR);
    		}
    		grayByte[i] = (byte) out;
    	}
    }
    
    public static int getMin(int data [][]) {
    	int min = data[0][0];
    	for (int i = 0; i < data.length; i++) {
    		for (int j = 0; j < data[0].length; j++) {
    			if (data[i][j] < min) min = data[i][j];
    		}
    	}
    	return min;
    }
    
    public static int getMax(int data [][]) {
    	int max = data[0][0];
    	for (int i = 0; i < data.length; i++) {
    		for (int j = 0; j < data[0].length; j++) {
    			if (data[i][j] > max) max = data[i][j];
    		}
    	}
    	return max;
    }
}