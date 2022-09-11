import java.awt.image.BufferedImage;

public class SPA {
	private BufferedImage image;
	private int w, h;
	
	private int[] rgbLeft = new int[3];
	private int[] rgbRight = new int[3];
	
	int x, y, k;
	
	public SPA(BufferedImage image) {
		this.image = image;
		this.w = this.image.getWidth();
		this.h = this.image.getHeight();
	}
	
	public double [] analysis() {
		double [] results = new double[3];
		
		for (int ch = 0; ch < 3; ch++) {
			x = y = k = 0;
			for (int j = 0; j < h; j++) {
				for (int i = 0; i < w - 1; i++) {
					rgbLeft = ImageUtils.getSplitRGB(image, i, j);
					rgbRight = ImageUtils.getSplitRGB(image, i + 1, j);
					
					int lPixel = rgbLeft[ch];
					int rPixel = rgbRight[ch];
					
					if ((rPixel%2==0 && lPixel<rPixel) || (rPixel%2==1 && lPixel>rPixel))
		                x+=1;
		            if ((rPixel%2==0 && lPixel>rPixel) || (rPixel%2==1 && lPixel<rPixel))
		                y+=1;
		            if (Math.round(rPixel/2)==Math.round(lPixel/2))
		                k+=1;
				}
			}
			
			if (k==0) {
				System.out.println("It's deselected");
				Exception ex = new Exception();
				ex.printStackTrace();
			}
		    
			double a, b, c, bp, bm, beta;
			
			a=2*k;
			b=2*(2*x-w*(h-1));
			c=y-x;

			bp=(-b+Math.sqrt(Math.pow(b, 2)-4*a*c))/(2*a);
			bm=(-b-Math.sqrt(Math.pow(b, 2)-4*a*c))/(2*a);

			beta=Math.min(bp, bm);
			
			if (beta > 0.05)
				results[ch]=beta;
			else
				results[ch]=0;
		        
		}
		
		return results;
	}

}