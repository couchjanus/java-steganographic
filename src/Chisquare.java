import java.awt.image.BufferedImage;
import java.awt.Color;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class ChiSquare {
	
	private final BufferedImage image;
	
	private int numChunks = 0;
	private double[] chiSquareValues;
	private int width;
	private int height;
	private int[] values;

	public ChiSquare(BufferedImage image) {
		this.image = image;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	private int[] getValues(int[] values) {
		for(int i = 0; i < values.length; i++) {
			values[i] = 1;
		}
		return values;
	}
	
	private double[] getExpected(int[] values) {
		double[] result = new double[values.length / 2];
		for (int i = 0; i < result.length; i++) {
			double avg = ((values[2 * i] + values[2 * i +1]) / 2L);
			result[i] = avg;
		}
		return result;
	}

	private long[] getObserved(int[] values) {
		long[] result = new long[values.length / 2];
		for (int i = 0; i < result.length; i++) {	
			result[i] = values[2 * i + 1];
		}
		return result;
	}
	
	public double[] attackTopToBottom(int chunkSize) {
		
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		values = new int[numChunks];
		values = getValues(values);
		
		this.chiSquareValues = new double[this.numChunks];
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				
				if(block < this.chiSquareValues.length) {
					red = (new Color(image.getRGB(i, j))).getRed();
					values[red]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				if(block < this.chiSquareValues.length) {
					green = (new Color(image.getRGB(i, j))).getGreen();
					values[green]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < this.chiSquareValues.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue();
					values[blue]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return this.chiSquareValues;
	}
	
	public double[] attackLeftToRigth(int chunkSize) {
		values = getValues(values);
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		this.chiSquareValues = new double[this.numChunks];
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for(int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				
				if(block < this.chiSquareValues.length) {
					red = (new Color(image.getRGB(i, j))).getRed();
					values[red]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				if(block < this.chiSquareValues.length) {
					green = (new Color(image.getRGB(i, j))).getGreen();
					values[green]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < this.chiSquareValues.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue();
					values[blue]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return this.chiSquareValues;
	}
	
	public double[] attackBottomToTop(int chunkSize) {
		values = getValues(values);
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		this.chiSquareValues = new double[this.numChunks];
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int j = height - 1; j >= 0; j--) {
			for(int i = width -1; i >= 0; i--) {
				
				if(block < this.chiSquareValues.length) {
					red = (new Color(image.getRGB(i, j))).getRed();
					values[red]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				if(block < this.chiSquareValues.length) {
					green = (new Color(image.getRGB(i, j))).getGreen();
					values[green]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < this.chiSquareValues.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue();
					values[blue]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return this.chiSquareValues;
	}
	
	public double[] attackRightToLeft(int chunkSize) {
		values = getValues(values);
		this.numChunks = (int) (Math.floor((width * height * 3 / chunkSize)) + 1.0D);
		this.chiSquareValues = new double[this.numChunks];
		
		int block = 0;
		int nbBytes = 1;
		int red, green, blue;
		
		for (int i = width - 1; i >= 0; i--) {
			for (int j = height - 1; j >= 0; j--) {
				
				if(block < this.chiSquareValues.length) {
					red = (new Color(image.getRGB(i, j))).getRed();
					values[red]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				if(block < this.chiSquareValues.length) {
					green = (new Color(image.getRGB(i, j))).getGreen();
					values[green]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
				
				if(block < this.chiSquareValues.length) {
					blue = (new Color(image.getRGB(i, j))).getBlue();
					values[blue]++;
					nbBytes++;
					if(nbBytes > chunkSize) {
						this.chiSquareValues[block] = new ChiSquareTest().chiSquareTest(this.getExpected(values), this.getObserved(values));
						block++;
						nbBytes = 1;
					}
				}
			}
		}
		return this.chiSquareValues;
	}
	
	
}
