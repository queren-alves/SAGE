package sage.controller;

import java.util.List;
import java.util.Optional;

import sage.model.Ambiente;
import sage.model.Sensor;

public class SensorController {

	/**
	 * Retorna todos os sensores armazenados no banco de dados.
	 *
	 * @return uma lista contendo todos os sensores armazenados.
	 */
	public static List<Sensor> findAll() {
		return Database.getSensores();
	}

	/**
	 * Salva as alterações no sensor.
	 *
	 * @param sensor o sensor existente com os parâmetros a serem alterados.
	 * @return {@code true} se o sensor foi alterado com sucesso;
	 *         {@code false} caso o sensor não exista no banco de dados.
	 */
	public static boolean save(Sensor sensor) {
		Optional<Sensor> sensorOpt = findById(sensor.getId());
		if (sensorOpt.isEmpty())
			return false;
		sensorOpt.get().setNome(sensor.getNome());
		sensorOpt.get().setFabricante(sensor.getFabricante());
		return true;
	}

	/**
	 * Retorna todos os sensores referentes ao ambiente informado.
	 *
	 * @param ambiente o ambiente a ser pesquisado.
	 * @return uma lista contendo todos os sensores referentes ao ambiente.
	 */
	public static List<Sensor> findByAmbiente(Ambiente ambiente) {
		return ambiente.getSensores();
	}

	/**
	 * Persiste um sensor no banco de dados.
	 *
	 * @param sensor o sensor a ser adicionado ao banco de dados.
	 * @return {@code true} se o sensor foi adicionado com sucesso; {@code false}
	 *         caso o sensor já exista ou não tenha sido adicionado.
	 */
	public static boolean persist(Sensor sensor) {
		if (Database.getSensores().stream().anyMatch(s -> s.equals(sensor)))
			return false;
		sensor.getAmbiente().addSensor(sensor);
		return Database.getSensores().add(sensor);
	}

	/**
	 * Remove um sensor do banco de dados.
	 *
	 * @param sensor o sensor a ser removido.
	 * @return {@code true} se o sensor foi removido com sucesso; {@code false} caso
	 *         o sensor não esteja presente no banco de dados.
	 */
	public static boolean remove(Sensor sensor) {
		if (Database.getSensores().remove(sensor))
			return sensor.getAmbiente().removeSensor(sensor);
		return false;
	}

	/**
	 * Remove um sensor do banco de dados.
	 *
	 * @param id o id do sensor a ser removido.
	 * @return {@code true} se o sensor foi removido com sucesso; {@code false} caso
	 *         o sensor não esteja presente no banco de dados.
	 */
	public static boolean removeById(int id) {
		Optional<Sensor> sensorOptional = findById(id);
		if (sensorOptional.isPresent())
			return remove(sensorOptional.get());
		return false;
	}

	/**
	 * Busca um sensor pelo ID.
	 *
	 * @param id o identificador único do sensor.
	 * @return um {@code Optional} contendo o sensor encontrado, ou
	 *         {@code Optional.empty()} caso não seja encontrado.
	 */
	public static Optional<Sensor> findById(int id) {
		return Database.getSensores().stream().filter(s -> s.getId() == id).findFirst();
	}

}
