import java.awt.image.BufferedImage;
public class RSA {
	private BufferedImage image;
	private int w, h;
	
	private int[][] mask;
	private int M, N;
	
	private double 
		positiveRegularGroups,
		negativeRegularGroups,
		positiveSingularGroups,
		negativeSingularGroups,
		positiveUnusableGroups,
		negativeUnusableGroups;
	
	
	
	public RSA(BufferedImage image) {
		this.image = image;
		this.w = this.image.getWidth();
		this.h = this.image.getHeight();
		int mM, nM; 
		mM = nM = 2;
		mask = new int[2][mM * nM];
		
		int k = 0;
		
		for(int i = 0; i < nM; i++) {
			for (int j=0; j<mM; j++) {
				if( (j%2 == 0 && i%2==0) || (j%2==1 && i%2==1) ) {
					mask[0][k] = 1;
					mask[1][k] = 0;
				}else {
					mask[0][k] = 0;
					mask[1][k] = 1;
				}
				k++;
			}
		}
		M = mM;
		N = nM;
		
	}
	
	public double[] analysis(int color, boolean overlap) {
		int block[] = new int[M*N];
		
		positiveRegularGroups=
		negativeRegularGroups=
		positiveSingularGroups=
		negativeSingularGroups=
		positiveUnusableGroups=
		negativeUnusableGroups=0;
		
		double varB, varP, varN;
		
		int x=0, y=0;
		while (x < w && y < h) {
			for(int m=0; m<M; m++) {
				int k = 0;
				for(int i=0; i< N; i++) {
					for(int j=0; j<M; j++) {
						block[k] = image.getRGB(x + j, y + i);
						k++;
					}
				}
				
				varB = getVariation(block, color);
				block = flipBlock(block, mask[m]);
				
				varP = getVariation(block, color);
				block = flipBlock(block, mask[m]);
				
				mask[m] = this.invertMask(mask[m]);
				varN = getNegativeVariation(block, color, mask[m]);
				mask[m] = this.invertMask(mask[m]);
				
				if(varP > varB)
					positiveRegularGroups++;
				if(varP < varB)
					positiveSingularGroups++;
				if (varP == varB)
					positiveUnusableGroups++;
				
				if(varN > varB)
					negativeRegularGroups++;
				if(varN < varB)
					negativeSingularGroups++;
				if (varN == varB)
					negativeUnusableGroups++;
			}
				if(overlap)
					x += 1;
				else
					x += M;
				if(x >= (w-1)) {
					x = 0;
					if(overlap)
						y += 1;
					else
						y += N;
				}
				if(y>=(h-1))
					break;
			}
		
		double allpixels[] = getAllPixelFlip(color, overlap);
		double xRS = getXRS(positiveRegularGroups, negativeRegularGroups, allpixels[0], allpixels[2], positiveSingularGroups, negativeSingularGroups, allpixels[1], allpixels[3]);
		
		double ep, ml;
		
		if(2*(xRS - 1)==0)
			ep = 0;
		else
			ep = Math.abs(xRS/(2*(xRS-1)));
		
		if(xRS - 0.5 == 0)
			ml = 0;
		else
			ml = Math.abs(xRS/(xRS-0.5));
		double [] result = new double[2];
		result[0] = ep;
		result[1] = ml;
		return result;
	}
	
	
	private double[] getAllPixelFlip(int color, boolean overlap) {
		int [] allmask = new int[M*N];
		for (int i = 0; i < allmask.length; i++)
			allmask[i] = 1;
		
		int block[] = new int[M*N];
		
		positiveRegularGroups=
		negativeRegularGroups=
		positiveSingularGroups=
		negativeSingularGroups=
		positiveUnusableGroups=
		negativeUnusableGroups=0;
		
		double varB, varP, varN;
		
		int x=0, y=0;
		
		while (x < w && y < h) {
			
			for(int m=0; m<M; m++) {
				int k = 0;
				for(int i=0; i< N; i++) {
					for(int j=0; j<M; j++) {
						block[k] = image.getRGB(x + j, y + i);
						k++;
					}
				}
				
				block = flipBlock(block, allmask);
				varB = getVariation(block, color);
				
				block = flipBlock(block, mask[m]);
				varP = getVariation(block, color);
				block = flipBlock(block, mask[m]);
				
				mask[m] = this.invertMask(mask[m]);
				varN = getNegativeVariation(block, color, mask[m]);
				mask[m] = this.invertMask(mask[m]);
				
				if(varP > varB)
					positiveRegularGroups++;
				if(varP < varB)
					positiveSingularGroups++;
				if (varP == varB)
					positiveUnusableGroups++;
				
				if(varN > varB)
					negativeRegularGroups++;
				if(varN < varB)
					negativeSingularGroups++;
				if (varN == varB)
					negativeUnusableGroups++;
				
			}
				if(overlap)
					x += 1;
				else
					x += M;
				if(x >= (w-1)) {
					x = 0;
					if(overlap)
						y += 1;
					else
						y += N;
				}
				if(y>=(h-1))
					break;
			}
			double[] result = new double[4];
			
			result[0] = positiveRegularGroups;
			
			result[1] = positiveRegularGroups;
			result[2] = negativeRegularGroups;
			result[3] = negativeSingularGroups;
			return result;
			
	}
	
	
	private int[] flipBlock(int[] block, int[] mask){
		//if the mask is true, negate every LSB
		for(int i = 0; i < block.length; i++){
			if( (mask[i] == 1)){
				//get the colour
				int red = getRed(block[i]), green = getGreen(block[i]),
				blue = getBlue(block[i]);
				
				//negate their LSBs
				red = negateLSB(red);
				green = negateLSB(green);
				blue = negateLSB(blue);
				
				//build a new pixel
				int newpixel = (0xff << 24) | ((red  & 0xff) << 16)
				| ((green & 0xff) << 8) | ((blue & 0xff));
				
				//change the block pixel
				block[i] = newpixel;
			}else if (mask[i] == -1){
				//get the colour
				int red = getRed(block[i]), green = getGreen(block[i]),
				blue = getBlue(block[i]);
				
				//negate their LSBs
				red = invertLSB(red);
				green = invertLSB(green);
				blue = invertLSB(blue);
				
				//build a new pixel
				int newpixel = (0xff << 24) | ((red  & 0xff) << 16)
				| ((green & 0xff) << 8) | ((blue & 0xff));
				
				//change the block pixel
				block[i] = newpixel;
			}
		}
		return block;
	}

	private int negateLSB(int _byte){
		int temp = _byte & 0xfe;
		if(temp == _byte)
			return _byte | 0x1;
		else
			return temp;
	}

	private int invertLSB(int _byte){
		if(_byte == 255)
			return 256;
		if(_byte == 256)
			return 255;
		return (negateLSB(_byte + 1) - 1);
	}

	private int[] invertMask(int[] mask){
		for(int i = 0; i < mask.length; i++){
			mask[i] = mask[i] * -1;
		}
		return mask;
	}

	private double getVariation(int[] block, int color){
		double var = 0;
		int colour1, colour2;
		for(int i = 0; i < block.length; i = i + 4){
			colour1 = getPixelColour(block[0 + i], color);
			colour2 = getPixelColour(block[1 + i], color);
			var += Math.abs(colour1 - colour2);
			colour1 = getPixelColour(block[3 + i], color);
			colour2 = getPixelColour(block[2 + i], color);
			var += Math.abs(colour1 - colour2);
			colour1 = getPixelColour(block[1 + i], color);
			colour2 = getPixelColour(block[3 + i], color);
			var += Math.abs(colour1 - colour2);
			colour1 = getPixelColour(block[2 + i], color);
			colour2 = getPixelColour(block[0 + i], color);
			var += Math.abs(colour1 - colour2);
		}
		return var;
	}
	
	public int getPixelColour(int pixel, int color){
		if(color == 0)
			return getRed(pixel);
		else if (color == 1)
			return getGreen(pixel);
		else if (color == 2)
			return getBlue(pixel);
		else
			return 0;		
	}
	private double getNegativeVariation(int[] block, int color, int[] mask){
		double var = 0;
		int colour1, colour2;
		for(int i = 0; i < block.length; i = i + 4){
			colour1 = getPixelColour(block[0 + i], color);
			colour2 = getPixelColour(block[1 + i], color);
			if(mask[0 + i] == -1)
				colour1 = invertLSB(colour1);
			if(mask[1 + i] == -1)
				colour2 = invertLSB(colour2);
			var += Math.abs(colour1 - colour2);
			
			colour1 = getPixelColour(block[1 + i], color);
			colour2 = getPixelColour(block[3 + i], color);
			if(mask[1 + i] == -1)
				colour1 = invertLSB(colour1);
			if(mask[3 + i] == -1)
				colour2 = invertLSB(colour2);
			var += Math.abs(colour1 - colour2);
			
			colour1 = getPixelColour(block[3 + i], color);
			colour2 = getPixelColour(block[2 + i], color);
			if(mask[3 + i] == -1)
				colour1 = invertLSB(colour1);
			if(mask[2 + i] == -1)
				colour2 = invertLSB(colour2);
			var += Math.abs(colour1 - colour2);
			
			colour1 = getPixelColour(block[2 + i], color);
			colour2 = getPixelColour(block[0 + i], color);
			if(mask[2 + i] == -1)
				colour1 = invertLSB(colour1);
			if(mask[0 + i] == -1)
				colour2 = invertLSB(colour2);
			var += Math.abs(colour1 - colour2);
		}
		return var;
	}
	public int getRed(int pixel){
		return ((pixel >> 16) & 0xff);
	}
	
	public int getGreen(int pixel){
		return ((pixel >> 8) & 0xff);
	}
	
	public int getBlue(int pixel){
		return (pixel & 0xff);
	}

	
	private double getXRS(double r, double rm, double r1, double rm1, double s, double sm, double s1, double sm1) {
		double x = 0;
		double d0 = r - s;
		double dm0 = rm - sm;
		double d1 = r1 - s1;
		double dm1 = rm1 - sm1;
		
		double a = 2*(d1+d0);
		double b = dm0 - dm1 - d1 - 3*d0;
		double c = d0 - dm0;
		
		if(a==0)
			x = c / b;
		double discriminant = Math.pow(b, 2) - 4*a*c;
		
		if (discriminant >= 0) {
			double pos = (-b + Math.sqrt(discriminant))/2*a;
			double neg = (-b - Math.sqrt(discriminant))/2*a;
			if(Math.abs(pos)<=Math.abs(neg))
				x = pos;
			else
				x = neg;
		}else {
			x = c / b;
		}
		if (x==0)
			x = c / b;
		
		return x;
	}

}
