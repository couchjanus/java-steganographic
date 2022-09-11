import java.awt.image.BufferedImage;

public class SimplePair {

	private BufferedImage image;
	private int w, h;
	private int pair[] = new int[2];
	private int u, v;
	private long P, X, Y, Z, W;
	
	public static final int ANALYSIS_COLOR_RED = 0;
	
	/**
	 * Denotes analysis to be done with green.
	 */
	public static final int ANALYSIS_COLOR_GREEN = 1;
	
	/**
	 * Denotes analysis to be done with blue.
	 */
	public static final int ANALYSIS_COLOR_BLUE = 2;
	
	
	public SimplePair(BufferedImage image) {
		this.image = image;
		//get the images sizes
		w = this.image.getWidth();
		h = this.image.getHeight();
		
	}
	
	public double analysis(int color) {
		P = X = Y = Z = W = 0;
		// pairs across the image
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x = x + 2) {
				// get the block of data (2 pixels)
				pair[0] = image.getRGB(x, y);
				pair[1] = image.getRGB(x + 1, y);
	
				u = getPixelColour(pair[0], color);
				v = getPixelColour(pair[1], color);
				
				// if the 7 msb are the same, but the 1 lsb are different
				if( (u>>1 == v>>1) && ((v & 0x1) != (u & 0x1)))
					W++;
				// if the pixels are the same
				if( u == v )
					Z++;
				// if lsb(v) = 0 & u < v OR lsb(v) = 1 & u > v
				if( (v==(v>>1)<<1) && (u<v) || (v!=(v>>1)<<1) && (u>v))
					X++;
				// vice versa
				if( (v==(v>>1)<<1) && (u>v) || (v!=(v>>1)<<1) && (u<v))
					Y++;
					P++;
			}
		}
		System.out.println("W: " + W);
		//pairs down the image
		for(int y = 0; y < h; y = y + 2){
			for(int x = 0; x < w; x++){
						
				//get the block of data (2 pixels)
				pair[0] = image.getRGB(x, y);
				pair[1] = image.getRGB(x, y + 1);
			
				u = getPixelColour(pair[0], color);
				v = getPixelColour(pair[1], color);
						
				//if the 7 msb are the same, but the 1 lsb are different
				if( (u>>1 == v>>1) && ((v & 0x1) != (u & 0x1)))
					W++;
				//the pixels are the same
				if( u==v )
					Z++;
				//if lsb(v) = 0 & u < v OR lsb(v) = 1 & u > v
				if( (v==(v>>1)<<1) && (u<v) || (v!=(v>>1)<<1) && (u>v))
					X++;
				//vice versa
				if( (v==(v>>1)<<1) && (u>v) || (v!=(v>>1)<<1) && (u<v))
					Y++;
					P++;
				}			
			}
		System.out.println("W: " + W);
 		    //solve the quadratic equation
			//in the form ax^2 + bx + c = 0
			double a = 0.5 * ( W + Z );
			double b = 2 * X - P;
			double c = Y - X;		
			System.out.println("a: " + a + " b = " + b + " c = " + c);	
			//the result
			double result;
				
			//straight line
			if(a == 0)
				result = c / b;
				
			//take it as a curve
			double discriminant = Math.pow(b, 2) - (4 * a * c);
				
			if(discriminant >= 0){
				
				double rootpos = ((-1 * b) + Math.sqrt(discriminant)) / (2 * a);
				double rootneg = ((-1 * b) - Math.sqrt(discriminant)) / (2 * a);
				
				// return the root with the smallest absolute value (as per paper)
				if(Math.abs(rootpos) <= Math.abs(rootneg))
					result = rootpos;
				else
					result = rootneg;
			}else{
				result = c / b;
			}
				
			if(result == 0){
				//let's assume straight lines again, something is probably wrong
				result = c / b;
			}
				
			return result;
				
	}
	
	private int getPixelColour(int pixel, int color){
		if(color == ANALYSIS_COLOR_RED)
			return getRed(pixel);
		else if (color == ANALYSIS_COLOR_GREEN)
			return getGreen(pixel);
		else if (color == ANALYSIS_COLOR_BLUE)
			return getBlue(pixel);
		else
			return 0;		
	}
	
	private int getRed(int pixel){
		return ((pixel >> 16) & 0xff);
	}
	
	/**
	 * Gets the green content of a pixel.
	 *
	 * @param pixel The pixel to get the green content of.
	 * @return The green content of the pixel.
	 */
	private int getGreen(int pixel){
		return ((pixel >> 8) & 0xff);
	}
	
	/**
	 * Gets the blue content of a pixel.
	 *
	 * @param pixel The pixel to get the blue content of.
	 * @return The blue content of the pixel.
	 */
	private int getBlue(int pixel){
		return (pixel & 0xff);
	}	
		
}
