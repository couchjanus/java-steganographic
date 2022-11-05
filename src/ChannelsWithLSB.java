import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.DataBufferInt;

public class ChannelsWithLSB extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int r, g, b, a;
//	int colors[][] = {{255,0,0}, {0,255,0}, {0,0,255}};
//	String suffixes[] = {"red", "green", "blue"};
	
//	for(int k=1; k<4; k++) {
//		
//	}
	public ChannelsWithLSB(BufferedImage src, String colorChannel) {
		super();
		
		setLayout(new BorderLayout());
		setSize(300, 400);
		
		add(new JScrollPane(new ImageComponent(getChannel(src, colorChannel))));
	}

	private BufferedImage getChannel(BufferedImage src, String colorChannel) {
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
		

		
		DataBufferInt dbi = (DataBufferInt) image.getRaster().getDataBuffer();
		
		int[] data = dbi.getData();
		
		for (int i = 1; i < data.length; i = i + 2) {
		for (int j = 1; j < data.length; j = j + 2) {
			int bit = data[j-1];
			if (bit == 1) {
				
			}
			
		}
	}
		
		
		return image;
	}
	
}
