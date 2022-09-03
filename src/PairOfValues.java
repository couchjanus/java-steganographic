
public class PairOfValues {

	private long[] pov = new long[256];
	public PairOfValues() {
		for (int i = 0; i < 256; i++) {
			this.pov[i] = 1L; 
		}
	}
	
	
	public double[] getExpected() {
		double[] result = new double[this.pov.length / 2];
		for (int i = 0; i < result.length; i++) {
			double avg = ((this.pov[2*i] + this.pov[2*i + 1]) / 2L);
			result[i] = avg;
		}
		return result;
	}
	
	public long[] getObserved() {
		long[] result = new long[this.pov.length / 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = this.pov[2*i + 1];
		}
		return result;
	}
	
	public void incPov(int i) {
		this.pov[i] = this.pov[i] + 1L;
	}
}
