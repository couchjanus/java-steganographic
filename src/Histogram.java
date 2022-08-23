
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Histogram {
    private BufferedImage image;
    public HPanel histpanel;
    
    public Histogram( BufferedImage image) {
        setImage(image);
        histpanel = new HPanel();
    }
    
    public Histogram( int[][] data ) {
        BufferedImage image = ImageUtils.CreateImagefromIntArray(data);
        setImage(image);
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    public int[][] getPixels(BufferedImage image) {
        int iw = image.getWidth(null);
        int ih = image.getHeight(null);
        int[][] pixels = new int[iw][ih];
        
        DataBufferByte db = (DataBufferByte)image.getRaster().getDataBuffer();
            
        byte[] pixelarray = db.getData();
        
        for (int x = 0; x < iw; x++ ) {
           for (int y = 0; y < ih; y++ ) {
               pixels[x][y] = pixelarray[x + y * iw] &0xFF;
           }
        }
        
        return pixels;
    }
    
    public int [] getHistogram() {
        int [] histogram;
        histogram = new int[256];
        int[][] pixels = getPixels(image);
        
        //find the histogram
        
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                histogram[pixels[x][y]]++;
            }
        }
        return histogram;
    }
    
    public double [] getNormHistogram() {
        int [] histogram = getHistogram();
        double [] normHistogram;
        //The sum of the histogram equals the number of pixels in the image
        int sum = image.getHeight() * image.getWidth();
        
        normHistogram = new double[256];
        //Find the normalized histogram by dividing each component of the histogram by sum
        for( int n = 0; n < 256; n++ )
           normHistogram[n] = (double)histogram[n]/sum;
        
        return normHistogram;
    }
    
    
    public BufferedImage makeHistEqualizedImg() {
        double [] normHistogram = getNormHistogram();
        
        int [][] data = getPixels(image);
        int [] sum;
        double s = 0;
        sum = new int[256];

        for (int v = 0; v < 256; v++) {
                s += normHistogram[v];
                sum[v] = (int)(s*255+0.5);
        }

        int [][] data2 = data;
        for( int x = 0; x < data.length; x++ )
            for( int y = 0; y < data[0].length; y++ )
                data2[x][y] = sum[data[x][y]];
        
        BufferedImage image = ImageUtils.CreateImagefromIntArray(data2);
        return image;
    }
    
    
    public void equalizeHistogram() {
        setImage(makeHistEqualizedImg());
    }
    
    /**subclass to paint the histogram in a panel*/
    
    @SuppressWarnings("serial")
    public class HPanel extends JPanel{
        
	     public HPanel(){
	       setPreferredSize(new Dimension(356, 200));
	       setBackground(new Color(241,244, 180));
	       setBorder(BorderFactory.createLineBorder(Color.black));
	       
	     }
	     
	     public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        double [] normHistogram = getNormHistogram();
	        int x, y, height, space;
	        height = this.getHeight()*3/4;
	        x = 20; y = 20;
	        space = 0; int len;
	        len = 255;
	
	        //find max value in histogram
	        double max = 0;
	        for( int j = 0; j < len; j++ )
	            if( normHistogram[j] > max )
	                max = normHistogram[j];
	        
	        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
	        
	        //draw vertical axis
	        g.drawString(""+((double)((int)(max*100))/10)+"%", x, y+7);
	        x += 30;
	        g.drawLine(x-2, y+height+2, x-2, y);
	        g.drawLine(x-2, y, x-4, y);
	        
	        //draw horizontal axis
	        g.drawLine(x-2, y+height, x+(space+1)*257-1, y+height);
	        g.drawLine(x+(space+1)*256, y+height+2, x+(space+1)*256, y+height);
	        g.drawString(Integer.toString(len), x+(space+1)*256-8, y+height+11);
	        g.drawLine(x+(space+1)*128, y+height+2, x+(space+1)*128, y+height);
	        g.drawString(Integer.toString(len/2), x+(space+1)*128-8, y+height+11);
	        
	        //draw bars
	        Color shade = new Color(10, 10, 180);
	        g.setColor(shade);
	        
	        for (int i = 0; i < len; i++)
	                g.drawLine(x+(i+1)*(space+1), y+height, x+(i+1)*(space+1),
	                        y+(int)((height-(normHistogram[i]/max)*height)));
	     }
    }
  
}
