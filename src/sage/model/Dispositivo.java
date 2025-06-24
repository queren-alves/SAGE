package sage.model;

import java.util.Objects;

public class Dispositivo {

	private int id;
	private String nome;
	private String tipo;
	private Ambiente ambiente;
	private boolean ligado = false;
	private double consumo;

	/**
	 * Inicializa uma nova instância de {@code Dispositivo}.
	 * 
	 * @param id      o identificador único do dispositivo.
	 * @param nome    o nome do dispositivo.
	 * @param tipo    o tipo do dispositivo (por exemplo: lâmpada, ventilador,
	 *                etc.).
	 * @param consumo o consumo de energia do dispositivo em watt (W).
	 */
	public Dispositivo(int id, String nome, String tipo, double consumo, Ambiente ambiente) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.consumo = consumo;
		this.ambiente = ambiente;
	}
	
	/**
	 * Inicializa uma nova instância de {@code Dispositivo}.
	 * 
	 * @param id      o identificador único do dispositivo.
	 * @param nome    o nome do dispositivo.
	 * @param tipo    o tipo do dispositivo (por exemplo: lâmpada, ventilador,
	 *                etc.).
	 * @param consumo o consumo de energia do dispositivo em watt (W).
	 */
	public Dispositivo(int id, String nome, String tipo, double consumo) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.consumo = consumo;
	}

	/**
	 * Liga o dispositivo
	 */
	public void ligar() {
		if (ligado)
			return;
		ligado = true;
		//System.out.println("Ligou dispositivo " + nome);
	}

	/**
	 * Desliga o dispositivo
	 */
	public void desligar() {
		if (!ligado)
			return;
		ligado = false;
		//System.out.println("Desligou dispositivo " + nome);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public boolean isLigado() {
		return ligado;
	}

	public void setLigado(boolean ligado) {
		this.ligado = ligado;
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
		Dispositivo other = (Dispositivo) obj;
		return id == other.id;
	}

}
