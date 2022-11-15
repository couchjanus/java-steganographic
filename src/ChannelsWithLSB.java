import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class ChannelsWithLSB extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int r, g, b, a;
	String message = "";
	String result = "";
	int len = 0;
	
	private JTextArea textArea;
	
	private JCheckBox bit0, bit1, bit2, bit3, bit4, bit5, bit6, bit7;

	private JCheckBox bitsArray[] = new JCheckBox[8];
	private JButton extractText;
	
	private JPanel bitsPanel;
	private BufferedImage image;
	
	public ChannelsWithLSB(BufferedImage src, String colorChannel) {
		super();
		
		
		bit0 = new JCheckBox();
		bit1 = new JCheckBox();
		bit2 = new JCheckBox(); 
		bit3 = new JCheckBox(); 
		bit4 = new JCheckBox(); 
		bit5 = new JCheckBox(); 
		bit6 = new JCheckBox(); 
		bit7 = new JCheckBox();
		
		bit0.setText("0"); 
		bit1.setText("1"); 
		bit2.setText("2"); 
		bit3.setText("3"); 
		bit4.setText("4"); 
		bit5.setText("5"); 
		bit6.setText("6"); 
		bit7.setText("0");
		
		bitsArray[0] = bit0;
		bitsArray[1] = bit1;
		bitsArray[2] = bit2;
		bitsArray[3] = bit3;
		bitsArray[4] = bit4;
		bitsArray[5] = bit5;
		bitsArray[6] = bit6;
		bitsArray[7] = bit7;
		
		extractText = new JButton("Extract Text");
		extractText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extractTextPerformed(e);
			}
		});
		bitsPanel = new JPanel(new GridLayout(3,3));
		
		
		bitsPanel.add(bit0);
		
		bitsPanel.add(bit1);
		bitsPanel.add(bit2);
		bitsPanel.add(bit3);
		bitsPanel.add(bit4);
		bitsPanel.add(bit5);
		bitsPanel.add(bit6);
		bitsPanel.add(bit7);
		bitsPanel.add(extractText);
		
		setLayout(new BorderLayout());
		setSize(300, 400);
		add(bitsPanel, BorderLayout.NORTH);
		image = getChannel(src, colorChannel);
		add(new JScrollPane(new ImageComponent(getChannel(src, colorChannel))), BorderLayout.CENTER);
		
		textArea = new JTextArea(5,20);	
		textArea.setLineWrap(true);
		
//		message = decodeSecretMessage(image, colorChannel);
//		for(int i=0; i<len*8; i=i+8) {
//			String sub = message.substring(i, i+8);
//			int m = Integer.parseInt(sub, 2);
//			char ch = (char) m;
//			result+=ch;
//		}
//		message = getMessage(image);
//		System.out.println("Secret Message: "+message);
//		textArea.setText(result);
		add(new JScrollPane(textArea), BorderLayout.SOUTH);
	}
	
	private void extractTextPerformed(ActionEvent e) {
		if (image == null) return;
		int bitArray[] = new int[8];
		
		for (int i=0; i<8;i++) {
			if(bitsArray[i].isSelected()) {
				bitArray[i] = 1;
			}else {
				bitArray[i] = 0;
			}
			String message = decodeText(image, bitArray);
			textArea.setText(message);
		}
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
	
	public String decodeSecretMessage(BufferedImage image, String colorChannel) {
		String secretMessage = "";
		String notSecretMessage = "";
		int currentBit = 0;
		String msg = "";
		
		for (int x = 0; x<image.getWidth(); x++) {
			for(int y=0; y<image.getHeight(); y++) {
				if(x==0 && y<8) {
					int currentPixel = image.getRGB(x, y);
					
					if (colorChannel == "red") {
						int red = (currentPixel >> 16) & 0xff;
						msg = Integer.toBinaryString(red);
					}
					else if (colorChannel == "green") {
						int green = (currentPixel >> 8) & 0xff;
						msg = Integer.toBinaryString(green);
					}
					else if (colorChannel == "blue") {
						int blue = currentPixel & 0xff;
						msg = Integer.toBinaryString(blue);
					}
					
					notSecretMessage+=msg.charAt(msg.length()-1);
					len = Integer.parseInt(notSecretMessage, 2);
					
				}
				else if(currentBit<len*8) {
					int currentPixel = image.getRGB(x, y);
					
					if (colorChannel == "red") {
						int red = (currentPixel >> 16) & 0xff;
						msg = Integer.toBinaryString(red);
					}
					else if (colorChannel == "green") {
						int green = (currentPixel >> 8) & 0xff;
						msg = Integer.toBinaryString(green);
					}
					else if (colorChannel == "blue") {
						int blue = currentPixel & 0xff;
						msg = Integer.toBinaryString(blue);
					}
					secretMessage+=msg.charAt(msg.length()-1);
					currentPixel++;
				}
			}
		}
		return secretMessage;
	}
	
	
}
