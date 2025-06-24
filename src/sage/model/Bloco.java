package sage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bloco {

	private int id;
	private String nome;
	private List<Ambiente> ambientes = new ArrayList<Ambiente>();

	/**
	 * Inicializa uma nova instância de {@code Bloco}.
	 *
	 * @param id   o identificador único do bloco.
	 * @param nome o nome do bloco.
	 */
	public Bloco(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	/**
	 * Adiciona um ambiente ao bloco.
	 *
	 * @param ambiente o ambiente que será adicionado ao bloco.
	 * @return {@code true} se o ambiente foi adicionado com sucesso; {@code false}
	 *         caso o ambiente já esteja presente no bloco.
	 */
	public boolean addAmbiente(Ambiente ambiente) {
		if (ambientes.stream().anyMatch(a -> a.equals(ambiente)))
			return false;
		return ambientes.add(ambiente);
	}

	/**
	 * Remove um ambiente do bloco.
	 *
	 * @param ambiente o ambiente que será removido do bloco.
	 * @return {@code true} se o ambiente foi removido com sucesso; {@code false}
	 *         caso o ambiente não esteja presente no bloco.
	 */
	public boolean removeAmbiente(Ambiente ambiente) {
		return ambientes.remove(ambiente);
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

	public List<Ambiente> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(List<Ambiente> ambientes) {
		this.ambientes = ambientes;
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
		Bloco other = (Bloco) obj;
		return id == other.id;
	}

}
