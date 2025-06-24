package sage.simulation;

import java.util.Random;

public class TemperaturaSimulation {

	private double temperatura;
	private Random random;
	
	public TemperaturaSimulation() {
		this.random = new Random();
		this.temperatura = 22 + random.nextDouble() * 10;
	}
	
	public double temperatureCheck() {
		temperatura += (random.nextDouble() - 0.5) * 2;
		return Math.round(temperatura* 10.0) / 10.0;
	}
	
}
