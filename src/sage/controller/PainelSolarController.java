package sage.controller;

import java.util.List;
import java.util.Optional;

import sage.model.PainelSolar;

public class PainelSolarController {

	/**
	 * Retorna todos os PainelSolar armazenados no banco de dados.
	 *
	 * @return uma lista contendo todos os PainelSolar armazenados.
	 */
	public static List<PainelSolar> findAll() {
		return Database.getPaineisSolares();
	}

	/**
	 * Salva as alterações no PainelSolar.
	 *
	 * @param painel o PainelSolar existente com os parâmetros a serem alterados.
	 * @return {@code true} se o PainelSolar foi alterado com sucesso;
	 *         {@code false} caso o PainelSolar não exista no banco de dados.
	 */
	public static boolean save(PainelSolar painel) {
		Optional<PainelSolar> painelOptional = findById(painel.getId());
		if (painelOptional.isEmpty())
			return false;
		painelOptional.get().setNome(painel.getNome());
		return true;
	}

	/**
	 * Persiste um PainelSolar no banco de dados.
	 *
	 * @param painel o PainelSolar a ser adicionado ao banco de dados.
	 * @return {@code true} se o PainelSolar foi adicionado com sucesso; {@code false}
	 *         caso o PainelSolar já exista ou não tenha sido adicionado.
	 */
	public static boolean persist(PainelSolar painel) {
		if (Database.getPaineisSolares().stream().anyMatch(p -> p.equals(painel)))
			return false;
		return Database.getPaineisSolares().add(painel);
	}

	/**
	 * Remove um PainelSolar do banco de dados.
	 *
	 * @param painel o PainelSolar a ser removido.
	 * @return {@code true} se o PainelSolar foi removido com sucesso; {@code false} caso
	 *         o PainelSolar não esteja presente no banco de dados.
	 */
	public static boolean remove(PainelSolar painel) {
		return Database.getPaineisSolares().remove(painel);
	}

	/**
	 * Remove um PainelSolar do banco de dados.
	 *
	 * @param id o id do PainelSolar a ser removido.
	 * @return {@code true} se o PainelSolar foi removido com sucesso; {@code false} caso
	 *         o PainelSolar não esteja presente no banco de dados.
	 */
	public static boolean removeById(int id) {
		Optional<PainelSolar> painelOptional = findById(id);
		if (painelOptional.isPresent())
			return remove(painelOptional.get());
		return false;
	}

	/**
	 * Busca um PainelSolar pelo ID.
	 *
	 * @param id o identificador único do PainelSolar.
	 * @return um {@code Optional} contendo o PainelSolar encontrado, ou
	 *         {@code Optional.empty()} caso não seja encontrado.
	 */
	public static Optional<PainelSolar> findById(int id) {
		return Database.getPaineisSolares().stream().filter(p -> p.getId() == id).findFirst();
	}

}
