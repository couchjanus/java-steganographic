import java.awt.image.*;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class ChiSquareAttack {
	private final BufferedImage image;
	private final BufferedImage lsbImage;
	
	private PairOfValues pov = null;
	
	private double[] chiSquareValues;
	private int chunkSize = 128;
	
	private int numOfChunks = 0;
	
	private ChiSquareTest chi = new ChiSquareTest();
	
	public ChiSquareAttack(BufferedImage image, BufferedImage lsbImage) {
		this.image = image;
		this.lsbImage = lsbImage;
	}
	
	private void runTest(int bytesRead) {
		try {
			this.chiSquareValues[bytesRead / this.chunkSize -1] = 
			this.chi.chiSquareTest(this.pov.getExpected(), this.pov.getObserved());
			System.out.println(this.chiSquareValues[bytesRead / this.chunkSize -1]);
		}catch(Exception ex) {}
	}
	
	public BufferedImage execute() {
			try {
				this.pov = new PairOfValues();
				int w = this.image.getWidth();
				int h = this.image.getHeight();
				this.numOfChunks = (int) (Math.floor((w * h * 3 / this.chunkSize)) + 1.0D);
				this.chiSquareValues = new double[this.numOfChunks];
				
				int bytesRead = 0;
				
				for (int y = h - 1; y >= 0; y--) {
					for (int x = 0; x < w; x++) {
						int p = this.image.getRGB(x, y);
						int b = p >> 16 & 0xFF;
						int g = p >> 8 & 0xFF;
						int r = p & 0xFF;
						
						lsbAnalysis(x, y, r, g, b);
						
						this.pov.incPov(r);
						bytesRead++;
						if (bytesRead % this.chunkSize == 0) {
							runTest(bytesRead);
						}
						this.pov.incPov(g);
						bytesRead++;
						if (bytesRead % this.chunkSize == 0) {
							runTest(bytesRead);
						}
						this.pov.incPov(b);
						bytesRead++;
						if (bytesRead % this.chunkSize == 0) {
							runTest(bytesRead);
						}
					}
				}
				this.chiSquareValues[this.numOfChunks - 1] = this.chi.chiSquareTest(this.pov.getExpected(), this.pov.getObserved());
				return generateResult();
			} catch(Exception ex) {return null;}
		}
		private void lsbAnalysis(int x, int y, int r, int g, int b) {
			
			int p = -16777216;
			int temp = 0;
			
			if ((r & 0x1) == 1) p +=255;
			if ((g & 0x1) == 1) {
				temp = p;
				p = 65280;
				p +=temp;
			}
			if ((b & 0x1) == 1) {
				temp = p;
				p = 16711680;
				p +=temp;
			}
			this.lsbImage.setRGB(x, y, p);;
		}
		
		private BufferedImage generateResult() {
			BufferedImage outImg = null;
			return outImg;
		}
}
