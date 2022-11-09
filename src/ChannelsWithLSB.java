import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.*;
import java.awt.*;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class ChannelsWithLSB extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int r, g, b, a;
	String message;
	
	private JTextArea textArea;
//	int colors[][] = {{255,0,0}, {0,255,0}, {0,0,255}};
//	String suffixes[] = {"red", "green", "blue"};
	
//	for(int k=1; k<4; k++) {
//		
//	}
	public ChannelsWithLSB(BufferedImage src, String colorChannel) {
		super();
		
		setLayout(new BorderLayout());
		setSize(300, 400);
		BufferedImage image = getChannel(src, colorChannel);
		add(new JScrollPane(new ImageComponent(getChannel(src, colorChannel))), BorderLayout.NORTH);
		
		textArea = new JTextArea(5,20);	
		textArea.setLineWrap(true);
		message = getMessage(image);
//		System.out.println("Secret Message: "+message);
		textArea.setText(message);
		
		
		add(new JScrollPane(textArea), BorderLayout.SOUTH);
	}

	private BufferedImage getChannel(BufferedImage src, String colorChannel) {
		BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(), src.getType()) ;
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
		
		
//		int bitArray[] = new int[8];
//		
//		
//		for (int i=0; i<8; i++) {
//			bitArray[i] = 1;
//		}
		
		
//		System.out.println("Secret Message: "+message);
//		DataBufferInt dbi = (DataBufferInt) image.getRaster().getDataBuffer();
//		
//		int[] data = dbi.getData();
//		
//		for (int i = 1; i < data.length; i = i + 2) {
//		for (int j = 1; j < data.length; j = j + 2) {
//			int bit = data[j-1];
//			if (bit == 1) {
//				
//			}
//			
//		}
//	}
		
//		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
//		result.getGraphics().drawImage(image, 0, 0, null);
//		
//		WritableRaster raster = result.getRaster();
//
//		int[] tmp = new int[4];
////		
////		int[] pixel;
//		for(int i = 0; i < raster.getHeight(); i++) {
//
//			for(int j = 0; j < raster.getWidth(); j++) {
//				int[] pixel = raster.getPixel(i, j, tmp);
////				System.out.println(pixel.length);
////				if (pixel[0] == 1) {
////					pixel[0]=255;
////				}else {
////					pixel[0]=0;
////				}
//				for(int k = 0; k < pixel.length; k++) {
//					if (pixel[k] == 1) {
//						pixel[k]=255;
//					}else {
//						pixel[k]=0;
//					}
//				}
//				raster.setPixel(j, i, pixel);
//			}
//
//		}
		
//		int[] pixels = new int[image.getWidth()];
//		for(int y = 0; y < image.getHeight(); y++) {
//			
//			raster.getPixels(0, y, image.getWidth(), 1, pixels);
//			
//			for(int i = 0; i<pixels.length; i++) {
//				
//				if(pixels[i] < 1) {
//					pixels[i] = 0;
//				}else {
//					pixels[i]=255;
//				}
//			}
//			raster.setPixels(0, y, image.getWidth(), 1, pixels);
//		}
//		
		
		
		return image;
	}
	
	public String getMessage(BufferedImage image) {
		int bitArray[] = {1,0,0,0,0,0,0,0};

		BufferedImage currentImage = convertImage(image);
		
		message = decodeText(currentImage, bitArray);
//		System.out.println("Secret Message: "+message);
		return message;
	}
	
	public byte[] getByteData(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}
	
	public BufferedImage convertImage(BufferedImage image) {
		
		int newImageType = image.getType();
		
		if(newImageType == BufferedImage.TYPE_INT_RGB || newImageType == BufferedImage.TYPE_3BYTE_BGR)
			newImageType = BufferedImage.TYPE_3BYTE_BGR;
		else if(newImageType == BufferedImage.TYPE_INT_ARGB || newImageType == BufferedImage.TYPE_CUSTOM)
			newImageType = BufferedImage.TYPE_4BYTE_ABGR;
		else if(newImageType == BufferedImage.TYPE_INT_ARGB_PRE)
			newImageType = BufferedImage.TYPE_INT_ARGB_PRE;
		else return null;
		
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), newImageType); 
		Graphics g = newImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}
	
	public String decodeText(BufferedImage currentImage, int bitArray[]) {
		byte image[] = getByteData(currentImage);
		
		int offset = 0;
		int imageLength = image.length;
		
		int count = 0;
		for (int i = 0; i<bitArray.length; i++) {
			if (bitArray[i]==1) count++;
		}
		
		boolean data[] = new boolean[imageLength*count];
		
		for(int i = 0; i<imageLength; i++) {
			for (int j = 7; j>=0; j--) {
				if(bitArray[j]==1) {
					int singleBit = (image[i] >> j)&1;
					if(singleBit==1) {
						data[offset++] = true;
					}else {
						data[offset++] = false;
					}
				}
			}
		}
		
		int secretMessageLength = (imageLength*count)/8;
		
		byte secretMessage[] = new byte[secretMessageLength];
		for (int i=0; i<secretMessageLength; i++) {
			for(int bit=0; bit<8;bit++) {
				if(data[i*8+bit]) {
					secretMessage[i] |= (128>>bit);
				}
			}
		}
		try {
			return new String(secretMessage, "ASCII");
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	
}
