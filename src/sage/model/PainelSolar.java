package sage.model;

import java.util.Objects;

public class PainelSolar {

	private int id;
	private String nome;
	private double geracao;

	/**
	 * Inicializa uma nova instância de {@code PainelSolar}.
	 * 
	 * @param id      o identificador único do PainelSolar.
	 * @param nome    o nome do PainelSolar.
	 * @param geracao a geração de energia do PainelSolar em kilowatt-hora (kWh).
	 */
	public PainelSolar(int id, String nome, double geracao) {
		this.id = id;
		this.nome = nome;
		this.geracao = geracao;
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

	public double getGeracao() {
		return geracao;
	}

	public void setGeracao(double geracao) {
		this.geracao = geracao;
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
		PainelSolar other = (PainelSolar) obj;
		return id == other.id;
	}

}
