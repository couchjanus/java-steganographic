import java.awt.image.BufferedImage;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.awt.Color;

public class AvarageLsb {
	
	private final BufferedImage image;
	
	private int numChunks = 0;
	private double[] avarege;
	
	private int width;
	private int height;
	
	private int[] values;

	public AvarageLsb(BufferedImage image) {
		this.image = image;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public double[] attackTopToBottom(int chunkSize) {
		
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		avarege = new double[numChunks];
		for (int i=0; i<avarege.length; i++) {
			avarege[i] = 0;
		}
		
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				
				if(block < avarege.length) {
					red = (new Color(image.getRGB(i, j))).getRed() & 0x01;
					avarege[block]+= (double) red;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				if(block < avarege.length) {
					green = (new Color(image.getRGB(i, j))).getGreen() & 0x01;
					avarege[block]+= (double) green;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < avarege.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue() & 0x01;
					avarege[block]+=(double) blue;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return avarege;
	}
	
	public double[] attackBottomToTop(int chunkSize) {
		
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		avarege = new double[numChunks];
		for (int i=0; i<avarege.length; i++) {
			avarege[i] = 0;
		}
		
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int j = height -1; j >=0 ; j--) {
			for(int i = width -1; i >=0 ; i--) {
				
				if(block < avarege.length) {
					red = (new Color(image.getRGB(i, j))).getRed() & 0x01;
					avarege[block]+= (double) red;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				if(block < avarege.length) {
					green = (new Color(image.getRGB(i, j))).getGreen() & 0x01;
					avarege[block]+= (double) green;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < avarege.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue() & 0x01;
					avarege[block]+=(double) blue;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return avarege;
	}
	
	public double[] attackLeftToRight(int chunkSize) {
		
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		avarege = new double[numChunks];
		for (int i=0; i<avarege.length; i++) {
			avarege[i] = 0;
		}
		
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int i = 0; i < width; i++	) {
			for(int j = 0; j < height; j++) {
				
				if(block < avarege.length) {
					red = (new Color(image.getRGB(i, j))).getRed() & 0x01;
					avarege[block]+= (double) red;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				if(block < avarege.length) {
					green = (new Color(image.getRGB(i, j))).getGreen() & 0x01;
					avarege[block]+= (double) green;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < avarege.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue() & 0x01;
					avarege[block]+=(double) blue;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return avarege;
	}

	public double[] attackRightToLeft(int chunkSize) {
		
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		avarege = new double[numChunks];
		for (int i=0; i<avarege.length; i++) {
			avarege[i] = 0;
		}
		
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int i = width-1; i >=0; i--	) {
			for(int j = 0; j < height; j++) {
				
				if(block < avarege.length) {
					red = (new Color(image.getRGB(i, j))).getRed() & 0x01;
					avarege[block]+= (double) red;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				if(block < avarege.length) {
					green = (new Color(image.getRGB(i, j))).getGreen() & 0x01;
					avarege[block]+= (double) green;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < avarege.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue() & 0x01;
					avarege[block]+=(double) blue;
					nbBytes++;
					if(nbBytes > chunkSize) {
						avarege[block] /= (double) chunkSize;
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return avarege;
	}
}
