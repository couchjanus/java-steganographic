import java.awt.image.BufferedImage;

public class SimplePair {
	private BufferedImage image;
	private int w, h;
	private int pair[] = new int[2];
	private int u, v;
	private long P, X, Y, Z, W;
	
	public SimplePair(BufferedImage image) {
		this.image = image;
		this.w = this.image.getWidth();
		this.h = this.image.getHeight();
	}
	
	public double analysis(int color) {
		P = X = Y = Z = W = 0;
		for(int y = 0; y < h; y++) {
			for (int x = 0; x < w; x = x + 2) {
				pair[0] = image.getRGB(x, y);
				pair[1] = image.getRGB(x + 1, y);
				
				u = getColorPixel(pair[0], color);
				v = getColorPixel(pair[1], color);
				
				if((u>>1==v>>1) && ((v&0x1) != (u&0x1))) 
					W++;
				if (u == v)
					Z++;
				if ((v == (v>>1)<<1) && (u < v) || (v != (v>>1)<<1) && (u > v))
					X++;
				if ((v == (v>>1)<<1) && (u > v) || (v != (v>>1)<<1) && (u < v))
					Y++;
				    P++;
			}
		}
		System.out.println("W = " + W);
		
		for(int y = 0; y < h; y = y + 2) {
			for (int x = 0; x < w; x++) {
				pair[0] = image.getRGB(x, y);
				pair[1] = image.getRGB(x, y+1);
				
				u = getColorPixel(pair[0], color);
				v = getColorPixel(pair[1], color);
				
				if((u>>1 == v>>1) && ((v & 0x1) != (u & 0x1))) 
					W++;
				if (u == v)
					Z++;
				if ((v == (v>>1)<<1) && (u < v) || (v != (v>>1)<<1) && (u > v))
					X++;
				if ((v == (v>>1)<<1) && (u > v) || (v != (v>>1)<<1) && (u < v))
					Y++;
					
				    P++;
			}
		}
		
		System.out.println("W = " + W);
		// ax^2 +bx + c = 0
		
		double a = 0.5 * (W + Z);
		double b = 2 * X - P;
		double c = Y - X;
//		System.out.println("a = " + a + "b = " + b + "c = " + c);
		double result;
		
		if (a == 0)
			result = c / b;
		double discr = Math.pow(b, 2) - 4*a*c;
		
		if(discr >= 0) {
			double pos = (-b + Math.sqrt(discr)) / 2*a;
			double neg = (-b - Math.sqrt(discr)) / 2*a;
			
			if (Math.abs(pos) <= Math.abs(neg))
				result = pos;
			else
				result = neg;
		}else {
			result = c / b;
		}
		
		if (result == 0)
			result = c / b;
		
		return result;
	}
	
	private int getColorPixel(int pixel, int color) {
		if(color == 0)
			return getRed(pixel);
		else if(color == 1)
			return getGreen(pixel);
		else if(color == 2)
			return getBlue(pixel);
		else 
			return 0;
	}
	
	private int getRed(int pixel) {
		return (pixel >> 16) & 0xff;	
	}

	private int getGreen(int pixel) {
		return (pixel >> 8) & 0xff;	
	}
	private int getBlue(int pixel) {
		return (pixel & 0xff);	
	}

}
