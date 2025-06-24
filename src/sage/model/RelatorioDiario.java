package sage.model;

import java.time.LocalDate;
import java.util.Objects;

public class RelatorioDiario {

	private LocalDate data;
	private Ambiente ambiente;
	private double consumo;
	private double geracao;
	private long minutos;
	final double VALOR_KWH = 0.70;

	/**
	 * Inicializa uma nova instância de {@code RelatorioDiario}.
	 * 
	 * @param data     data referente ao relatório.
	 * @param ambiente ambiente referente ao relatório.
	 * @param consumo  consumo total.
	 * @param minutos  minutos em que o Ambiente permaneceu ligado.
	 */
	public RelatorioDiario(LocalDate data, Ambiente ambiente, double consumo, long minutos, double geracao) {
		this.data = data;
		this.ambiente = ambiente;
		this.consumo = consumo;
		this.minutos = minutos;
		this.geracao = geracao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}
	
	public String getAmbienteNome() {
		return ambiente.getNome();
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ambiente, data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelatorioDiario other = (RelatorioDiario) obj;
		return Objects.equals(ambiente, other.ambiente) && Objects.equals(data, other.data);
	}

	public long getMinutos() {
		return minutos;
	}

	public void setMinutos(long minutos) {
		this.minutos = minutos;
	}

	public double getGeracao() {
		return geracao;
	}

	public double getConsumoFinal() {
		return consumo - geracao;
	}
	
	public double getCustoComGeracao() {
		return getConsumoFinal() * VALOR_KWH;
	}
	
	public double getCustoSemGeracao() {
		return consumo * VALOR_KWH;
	}
	
	public double getEconomia() {
		return getCustoSemGeracao() - getCustoComGeracao();
	}

}
