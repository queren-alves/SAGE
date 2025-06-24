package sage.controller;

import java.util.List;
import java.util.Optional;

import sage.model.Ambiente;
import sage.model.Dispositivo;

public class DispositivoController {

	/**
	 * Retorna todos os dispositivos armazenados no banco de dados.
	 *
	 * @return uma lista contendo todos os dispositivos armazenados.
	 */
	public static List<Dispositivo> findAll() {
		return Database.getDispositivos();
	}
	
	/**
	 * Salva as alterações no dispositivo.
	 *
	 * @param dispositivo o dispositivo existente com os parâmetros a serem alterados.
	 * @return {@code true} se o dispositivo foi alterado com sucesso;
	 *         {@code false} caso o dispositivo não exista no banco de dados.
	 */
	public static boolean save(Dispositivo dispositivo) {
		Optional<Dispositivo> dispositivoOptional = findById(dispositivo.getId());
		if (dispositivoOptional.isEmpty())
			return false;
		dispositivoOptional.get().setNome(dispositivo.getNome());
		dispositivoOptional.get().setTipo(dispositivo.getTipo());
		dispositivoOptional.get().setConsumo(dispositivo.getConsumo());
		return true;
	}

	/**
	 * Retorna todos os dispositivos referentes ao ambiente informado.
	 *
	 * @param ambiente o ambiente a ser pesquisado.
	 * @return uma lista contendo todos os dispositivos referentes ao ambiente.
	 */
	public static List<Dispositivo> findByAmbiente(Ambiente ambiente) {
		return ambiente.getDispositivos();
	}

	/**
	 * Persiste um dispositivo no banco de dados.
	 *
	 * @param dispositivo o dispositivo a ser adicionado ao banco de dados.
	 * @return {@code true} se o dispositivo foi adicionado com sucesso;
	 *         {@code false} caso o dispositivo já exista ou não tenha sido
	 *         adicionado.
	 */
	public static boolean persist(Dispositivo dispositivo) {
		if (Database.getDispositivos().stream().anyMatch(d -> d.equals(dispositivo)))
			return false;
		if (dispositivo.getAmbiente() != null)
			dispositivo.getAmbiente().addDispositivo(dispositivo);
		return Database.getDispositivos().add(dispositivo);
	}

	/**
	 * Remove um dispositivo do banco de dados.
	 *
	 * @param dispositivo o dispositivo a ser removido.
	 * @return {@code true} se o dispositivo foi removido com sucesso; {@code false}
	 *         caso o dispositivo não esteja presente no banco de dados.
	 */
	public static boolean remove(Dispositivo dispositivo) {
		if (Database.getDispositivos().remove(dispositivo))
			return dispositivo.getAmbiente().removeDispositivo(dispositivo);
		return false;
	}

	/**
	 * Remove um dispositivo do banco de dados.
	 *
	 * @param id o id do dispositivo a ser removido.
	 * @return {@code true} se o dispositivo foi removido com sucesso; {@code false}
	 *         caso o dispositivo não esteja presente no banco de dados.
	 */
	public static boolean removeById(int id) {
		Optional<Dispositivo> dispositivoOptional = findById(id);
		if (dispositivoOptional.isPresent())
			return remove(dispositivoOptional.get());
		return false;
	}

	/**
	 * Busca um dispositivo pelo ID.
	 *
	 * @param id o identificador único do dispositivo.
	 * @return um {@code Optional} contendo o dispositivo encontrado, ou
	 *         {@code Optional.empty()} caso não seja encontrado.
	 */
	public static Optional<Dispositivo> findById(int id) {
		return Database.getDispositivos().stream().filter(d -> d.getId() == id).findFirst();
	}

}
