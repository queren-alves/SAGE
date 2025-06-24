package sage.controller;

import java.util.List;
import java.util.Optional;

import sage.model.Ambiente;
import sage.model.Bloco;

public class AmbienteController {

	/**
	 * Retorna todos os ambientes armazenados no banco de dados.
	 *
	 * @return uma lista contendo todos os ambientes armazenados.
	 */
	public static List<Ambiente> findAll() {
		return Database.getAmbientes();
	}

	/**
	 * Salva as alterações no ambiente.
	 *
	 * @param ambiente o ambiente existente com os parâmetros a serem alterados.
	 * @return {@code true} se o ambiente foi alterado com sucesso;
	 *         {@code false} caso o ambiente não exista no banco de dados.
	 */
	public static boolean save(Ambiente ambiente) {
		Optional<Ambiente> ambienteOpt = findById(ambiente.getId());
		if (ambienteOpt.isEmpty())
			return false;
		ambienteOpt.get().setNome(ambiente.getNome());
		ambienteOpt.get().setTempoOciosidade(ambiente.getTempoOciosidade());
		return true;
	}

	/**
	 * Retorna todos os ambientes referentes ao bloco informado.
	 *
	 * @param bloco o bloco a ser pesquisado.
	 * @return uma lista contendo todos os ambientes referentes ao bloco.
	 */
	public static List<Ambiente> findByBloco(Bloco bloco) {
		return bloco.getAmbientes();
	}

	/**
	 * Persiste um ambiente no banco de dados.
	 *
	 * @param ambiente o ambiente a ser adicionado ao banco de dados.
	 * @return {@code true} se o ambiente foi adicionado com sucesso; {@code false}
	 *         caso o ambiente já exista ou não tenha sido adicionado.
	 */
	public static boolean persist(Ambiente ambiente) {
		if (Database.getAmbientes().stream().anyMatch(a -> a.equals(ambiente)))
			return false;
		ambiente.getBloco().addAmbiente(ambiente);
		return Database.getAmbientes().add(ambiente);
	}

	/**
	 * Remove um ambiente do banco de dados.
	 *
	 * @param ambiente o ambiente a ser removido.
	 * @return {@code true} se o ambiente foi removido com sucesso; {@code false}
	 *         caso o ambiente não esteja presente no banco de dados.
	 */
	public static boolean remove(Ambiente ambiente) {
		if (Database.getAmbientes().remove(ambiente)) {
			ambiente.getDispositivos().stream().forEach(d -> d.setAmbiente(null));
			ambiente.getSensores().stream().forEach(s -> s.setAmbiente(null));
			return ambiente.getBloco().removeAmbiente(ambiente);
		}
		return false;
	}

	/**
	 * Remove um ambiente do banco de dados.
	 *
	 * @param id o id do ambiente a ser removido.
	 * @return {@code true} se o ambiente foi removido com sucesso; {@code false}
	 *         caso o ambiente não esteja presente no banco de dados.
	 */
	public static boolean removeById(int id) {
		Optional<Ambiente> ambienteOptional = findById(id);
		if (ambienteOptional.isPresent())
			return remove(ambienteOptional.get());
		return false;
	}

	/**
	 * Busca um ambiente pelo ID.
	 *
	 * @param id o identificador único do ambiente.
	 * @return um {@code Optional} contendo o ambiente encontrado, ou
	 *         {@code Optional.empty()} caso não seja encontrado.
	 */
	public static Optional<Ambiente> findById(int id) {
		return Database.getAmbientes().stream().filter(a -> a.getId() == id).findFirst();
	}

}
