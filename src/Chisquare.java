import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Chisquare {
	
	public double[] calcColors(double[] histogram) {
//	    Prepare color histogram for further calculations
		double[] hist = new double[histogram.length];
//		to avoid dividing by zero
		for(int x = 0; x < histogram.length; x++) {
			if(histogram[x] == 0) {
				hist[x] = 1;
			}else {
				hist[x] = histogram[x];
			}
		}
		  
	    return hist;
	}

	 public int[] expFreq(double[] histogram) {
//		    Calculating expacted and observed freqs
		    int[] expected = new int[histogram.length / 2];
		    for(int k = 0; k < histogram.length / 2; k++) 
		    	expected[k] = (int)(histogram[2 * k] + histogram[2 * k + 1]) / 2;
		    return expected;
	 }

	public double[] obsFreq(double[] histogram) {
	//    Calculating expacted and observed freqs
	    double[] observed = new double[histogram.length / 2];
	    for(int k = 0; k < histogram.length / 2; k++) 
	    	observed[k] = histogram[2 * k];
	    return observed;
	}

	 // Обчисліть хі-квадрат
    public static double calcChiSquare(double[] observed, int[] expected){
    	
    	Probability probability = new Probability();
    	
    	double[] e;
    	double chisquare = 0.0;
    	int k;
    	e = new double[expected.length];

    	for(k = 0; k < expected.length  ; k++){ 
            // Кількість подій - це кількість елементів у expected[]
    		if (k == 0)
    			e[k]= 1000*(probability.normLowerDistribution(observed[k]));		//менша ймовірність
    		else if(k == (expected.length-1))
    			e[k]= 1000*(probability.normUpperDistribution(observed[k-1]));	//верхня ймовірність
    		else
    			e[k]= 1000*(probability.normLowerDistribution(observed[k]) - probability.normLowerDistribution(observed[k-1])) ;

    		chisquare = chisquare +  (Math.pow((expected[k]-e[k]), 2)/e[k]);

    	}
    	return chisquare;
    }


}
