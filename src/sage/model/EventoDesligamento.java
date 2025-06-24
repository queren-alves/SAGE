package sage.model;

import java.time.LocalDateTime;

public class EventoDesligamento {

	private LocalDateTime timestamp;
	private long minutos;
	private double consumo;

	/**
	 * Inicializa uma nova instância de {@code EventoDesligamento}.
	 * 
	 * @param timestamp o timestamp em que o evento foi gerado.
	 * @param consumo   o consumo total do evento.
	 */
	public EventoDesligamento(LocalDateTime timestamp, double consumo, long minutos) {
		this.timestamp = timestamp;
		this.consumo = consumo;
		this.setMinutos(minutos);
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public long getMinutos() {
		return minutos;
	}

	public void setMinutos(long minutos) {
		this.minutos = minutos;
	}

}
