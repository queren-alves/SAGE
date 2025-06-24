package sage.model;

import java.util.Objects;

public class Sensor {

	private int id;
	private String nome;
	private String fabricante;
	private Ambiente ambiente;

	/**
	 * Inicializa uma nova instância de {@code Sensor}.
	 * 
	 * @param id         o identificador único do sensor.
	 * @param nome       o nome do sensor.
	 * @param fabricante o fabricante do sensor.
	 */
	public Sensor(int id, String nome, String fabricante, Ambiente ambiente) {
		this.id = id;
		this.nome = nome;
		this.fabricante = fabricante;
		this.ambiente = ambiente;
	}
	
	/**
	 * Inicializa uma nova instância de {@code Sensor}.
	 * 
	 * @param id         o identificador único do sensor.
	 * @param nome       o nome do sensor.
	 * @param fabricante o fabricante do sensor.
	 */
	public Sensor(int id, String nome, String fabricante) {
		this.id = id;
		this.nome = nome;
		this.fabricante = fabricante;
	}

	/**
	 * Simula a presença no Sensor.
	 */
	public void presenca() {
		this.ambiente.acionamento();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sensor other = (Sensor) obj;
		return id == other.id;
	}

}
