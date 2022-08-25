
public class Probability {

	public static double normLowerDistribution(double z) {
		int i;
		double z2, prev, p, t;

		z2 = z * z;
		t = p = z * Math.exp(-0.5 * z2) / Math.sqrt(2 * Math.PI);
		for (i = 3; i < 200; i += 2) {
			prev = p;  t *= z2 / i;  p += t;
			if (p == prev) return 0.5 + p;
		}
		return (z > 0) ? 1: 0;
	    }

	    public static double normUpperDistribution(double z) {
			return 1.0 - normLowerDistribution(z);
	    }

}
