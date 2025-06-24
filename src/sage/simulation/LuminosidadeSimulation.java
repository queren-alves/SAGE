package sage.simulation;

import java.util.Random;

public class LuminosidadeSimulation {
	
	private int luz;
	private Random random;
	public LuminosidadeSimulation() {
		this.random = new Random();
	}
	
	public int lightCheck() {
		luz = 40 + random.nextInt(61);;
		return luz;
	}

}
