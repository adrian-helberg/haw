package structures;

public class Tuple implements Comparable<Tuple> {
	
	public final Complex complexNumber;
	public final Double length;
	
	public Tuple(Complex complexNumber, Double length) {
		this.complexNumber = complexNumber;
		this.length = length;
	}
	
	public int compareTo(Tuple t) {
		return this.length < t.length 
				? -1 : (this.length > t.length 
						? 1 : 0);
	}
}
