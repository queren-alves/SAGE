package sage.controller;

import java.util.List;
import java.util.Optional;

import sage.model.Bloco;

public class BlocoController {

	/**
	 * Retorna todos os blocos armazenados no banco de dados.
	 *
	 * @return uma lista contendo todos os blocos armazenados.
	 */
	public static List<Bloco> findAll() {
		return Database.getBlocos();
	}

	/**
	 * Salva as alterações no bloco.
	 *
	 * @param bloco o bloco existente com os parâmetros a serem alterados.
	 * @return {@code true} se o bloco foi alterado com sucesso;
	 *         {@code false} caso o bloco não exista no banco de dados.
	 */
	public static boolean save(Bloco bloco) {
		Optional<Bloco> blocoOptional = findById(bloco.getId());
		if (blocoOptional.isEmpty())
			return false;
		blocoOptional.get().setNome(bloco.getNome());
		return true;
	}

	/**
	 * Persiste um bloco no banco de dados.
	 *
	 * @param bloco o bloco a ser adicionado ao banco de dados.
	 * @return {@code true} se o bloco foi adicionado com sucesso; {@code false}
	 *         caso o bloco já exista ou não tenha sido adicionado.
	 */
	public static boolean persist(Bloco bloco) {
		if (Database.getBlocos().stream().anyMatch(b -> b.equals(bloco)))
			return false;
		return Database.getBlocos().add(bloco);
	}

	/**
	 * Remove um bloco do banco de dados.
	 *
	 * @param bloco o bloco a ser removido.
	 * @return {@code true} se o bloco foi removido com sucesso; {@code false} caso
	 *         o bloco não esteja presente no banco de dados.
	 */
	public static boolean remove(Bloco bloco) {
		boolean result = Database.getBlocos().remove(bloco);
		if (result)
			bloco.getAmbientes().stream().forEach(a -> a.setBloco(null));
		return result;
	}

	/**
	 * Remove um bloco do banco de dados.
	 *
	 * @param id o id do bloco a ser removido.
	 * @return {@code true} se o bloco foi removido com sucesso; {@code false} caso
	 *         o bloco não esteja presente no banco de dados.
	 */
	public static boolean removeById(int id) {
		Optional<Bloco> blocoOptional = findById(id);
		if (blocoOptional.isPresent())
			return remove(blocoOptional.get());
		return false;
	}

	/**
	 * Busca um bloco pelo ID.
	 *
	 * @param id o identificador único do bloco.
	 * @return um {@code Optional} contendo o bloco encontrado, ou
	 *         {@code Optional.empty()} caso não seja encontrado.
	 */
	public static Optional<Bloco> findById(int id) {
		return Database.getBlocos().stream().filter(b -> b.getId() == id).findFirst();
	}

}
