package sage.simulation;

import java.util.function.Consumer;

public class DispositivoSimulation {
	
	private String nome;
	private String tipo;
	private boolean on;
	private long totalOn = 0;
	private long inicio = 0;
	private double consumo;
	private Consumer<String> output = System.out::println;
	
	public void setOutput(Consumer<String> output) {
        this.output = output;
    }
	
	public DispositivoSimulation(String nome, double consumo, String tipo) {
		this.nome = nome;
		this.consumo = consumo;
		this.tipo = tipo;
		this.on = false;
	}
	
	public void turnOn() {
		if(!on) {
			this.on = true;
			inicio = System.currentTimeMillis();
			output.accept(nome + " ligado.");
		}
	}
	
	public void turnOff() {
		if(on) {
			this.on = false;
			long timeOn = System.currentTimeMillis() - inicio;
			totalOn += timeOn;
			output.accept(nome + " desligado.");
		}
	}
	
	public double getConsumptionWh() {
		return (totalOn / 3600000.0) * consumo;
	}
	
	public String getReport() {
		long seg = totalOn / 1000;
		return nome + " ficou ligado por " + seg + " segundos."
				+ " Estimativa de consumo: " + String.format("%.2f", getConsumptionWh()) + " WH.";
	}
	
	public boolean isOn() {
		return on;
	}
	public String getNome() {
		return nome;
	}
	
	public String getTipo() {
		return tipo;
	}

}
