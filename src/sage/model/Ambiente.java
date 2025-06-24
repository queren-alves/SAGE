package sage.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Ambiente {

	private int id;
	private String nome;
	private int tempoOciosidade;
	private Bloco bloco;
	private LocalDateTime ultimaVezLigado;
	private LocalDateTime ultimaVezDesligado;
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	private List<Sensor> sensores = new ArrayList<Sensor>();
	private List<EventoDesligamento> eventosDesligamento = new LinkedList<EventoDesligamento>();

	private static final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	private ScheduledFuture<?> scheduleHandler;

	/**
	 * Inicializa uma nova instância de {@code Ambiente}.
	 *
	 * @param id              o identificador único do ambiente.
	 * @param nome            o nome do ambiente.
	 * @param tempoOciosidade o tempo de ociosidade, em minutos, após o qual os
	 *                        dispositivos do ambiente serão desligados caso não
	 *                        seja detectado movimento pelos sensores.
	 * @param bloco           o bloco ao qual o Ambiente pertence.
	 */
	public Ambiente(int id, String nome, int tempoOciosiodade, Bloco bloco) {
		this.id = id;
		this.nome = nome;
		this.tempoOciosidade = tempoOciosiodade;
		this.bloco = bloco;
	}
	
	/**
	 * Inicializa uma nova instância de {@code Ambiente}.
	 *
	 * @param id              o identificador único do ambiente.
	 * @param nome            o nome do ambiente.
	 * @param tempoOciosidade o tempo de ociosidade, em minutos, após o qual os
	 *                        dispositivos do ambiente serão desligados caso não
	 *                        seja detectado movimento pelos sensores.
	 */
	public Ambiente(int id, String nome, int tempoOciosiodade) {
		this.id = id;
		this.nome = nome;
		this.tempoOciosidade = tempoOciosiodade;
	}

	/**
	 * Adiciona um dispositivo ao ambiente.
	 *
	 * @param dispositivo o dispositivo que será adicionado ao ambiente.
	 * @return {@code true} se o dispositivo foi adicionado com sucesso;
	 *         {@code false} caso o dispositivo já esteja presente no ambiente.
	 */
	public boolean addDispositivo(Dispositivo dispositivo) {
		if (dispositivos.stream().anyMatch(d -> d.equals(dispositivo)))
			return false;
		return dispositivos.add(dispositivo);
	}

	/**
	 * Remove um dispositivo do ambiente.
	 *
	 * @param dispositivo o dispositivo que será removido do ambiente.
	 * @return {@code true} se o dispositivo foi removido com sucesso; {@code false}
	 *         caso o dispositivo não esteja presente no ambiente.
	 */
	public boolean removeDispositivo(Dispositivo dispositivo) {
		return dispositivos.remove(dispositivo);
	}

	/**
	 * Adiciona um sensor ao ambiente e define este ambiente no sensor.
	 *
	 * @param sensor o sensor que será adicionado ao ambiente.
	 * @return {@code true} se o sensor foi adicionado com sucesso; {@code false}
	 *         caso o sensor já esteja presente no ambiente.
	 */
	public boolean addSensor(Sensor sensor) {
		if (sensores.stream().anyMatch(s -> s.equals(sensor)))
			return false;
		sensor.setAmbiente(this);
		return sensores.add(sensor);
	}

	/**
	 * Remove um sensor do ambiente.
	 *
	 * @param sensor o sensor que será removido do ambiente.
	 * @return {@code true} se o sensor foi removido com sucesso; {@code false} caso
	 *         o sensor não esteja presente no ambiente.
	 */
	public boolean removeSensor(Sensor sensor) {
		return sensores.remove(sensor);
	}

	/**
	 * Liga todos os dispositivos do ambiente.
	 * 
	 * Se houver um agendamento de desligamento ativo, ele será interrompido. Em
	 * seguida, um novo agendamento de desligamento é criado com base no tempo de
	 * ociosidade definido (em minutos).
	 */
	public void acionamento() {
		ligarDispositivos();
		if (scheduleHandler != null) {
			scheduleHandler.cancel(true);
		}
		scheduleHandler = scheduler.schedule(this::desligarDispositivos, tempoOciosidade, TimeUnit.MINUTES);
	}

	/**
	 * Calcula e retorna o consumo total de energia do ambiente.
	 *
	 * @return o consumo total em kilowatts (kW) com base no tempo em que os
	 *         dispositivos do ambiente permaneceram ligados.
	 */
	private double calcularConsumo() {
		if (ultimaVezLigado == null || ultimaVezDesligado == null || !ultimaVezDesligado.isAfter(ultimaVezLigado)) {
			return 0.0;
		}

		Duration tempoLigado = Duration.between(ultimaVezLigado, ultimaVezDesligado);
		double horasLigado = tempoLigado.toMinutes() / 60.0;

		double consumoTotalemW = dispositivos.stream().mapToDouble(Dispositivo::getConsumo).sum();

		return consumoTotalemW * horasLigado / 1000.0;
	}

	/**
	 * Liga todos os dispositivos pertencentes ao ambiente.
	 * 
	 * Esse método ativa os dispositivos e os coloca em operação no ambiente.
	 */
	private void ligarDispositivos() {
		dispositivos.forEach(Dispositivo::ligar);
		ultimaVezLigado = LocalDateTime.now();
	}

	/**
	 * Desliga todos os dispositivos pertencentes ao ambiente.
	 * 
	 * Esse método desativa os dispositivos e os coloca em estado de inatividade no
	 * ambiente.
	 */
	private void desligarDispositivos() {
		dispositivos.forEach(Dispositivo::desligar);
		ultimaVezDesligado = LocalDateTime.now();
		eventosDesligamento.add(new EventoDesligamento(ultimaVezDesligado, calcularConsumo(), Duration.between(ultimaVezLigado, ultimaVezDesligado).toMinutes()));
	}

	public List<EventoDesligamento> getRelatorios() {
		return eventosDesligamento;
	}

	public void setRelatorios(List<EventoDesligamento> relatorios) {
		this.eventosDesligamento = relatorios;
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

	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public int getTempoOciosidade() {
		return tempoOciosidade;
	}

	public void setTempoOciosidade(int tempoOciosidade) {
		this.tempoOciosidade = tempoOciosidade;
	}

	/**
	 * Verifica o estado do Scheduler e desativa caso estiver ativo.
	 */
	public static void shutdownScheduler() {
		if (!scheduler.isShutdown())
			scheduler.shutdownNow();
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
		Ambiente other = (Ambiente) obj;
		return id == other.id;
	}

}
