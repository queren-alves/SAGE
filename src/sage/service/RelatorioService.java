package sage.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sage.controller.AmbienteController;
import sage.controller.PainelSolarController;
import sage.model.Ambiente;
import sage.model.EventoDesligamento;
import sage.model.PainelSolar;
import sage.model.RelatorioDiario;

public class RelatorioService {

	/**
	 * Recupera uma lista de relatórios diários correspondentes à data especificada.
	 * 
	 * @param date data utilizada como critério de busca.
	 * @return uma lista de RelatorioDiario contendo os relatórios de todos os
	 * 		   ambientes na data informada.
	 */
	public List<RelatorioDiario> gerarRelatorioDiario(LocalDate date) {

		List<RelatorioDiario> relatorios = new ArrayList<RelatorioDiario>();

		double geracaoTotal = PainelSolarController.findAll().stream().mapToDouble(PainelSolar::getGeracao).sum();			
		for (Ambiente a : AmbienteController.findAll()) {
			List<EventoDesligamento> eventosNaData = a.getRelatorios().stream()
					.filter(r -> r.getTimestamp().toLocalDate().equals(date)).toList();
			if (!eventosNaData.isEmpty()) {
				long minutos = eventosNaData.stream().mapToLong(EventoDesligamento::getMinutos).sum();
				double consumoTotal = eventosNaData.stream().mapToDouble(EventoDesligamento::getConsumo).sum();
				double geracaoPorAmbiente = geracaoTotal / AmbienteController.findAll().size();
				relatorios.add(new RelatorioDiario(date, a, consumoTotal, minutos, geracaoPorAmbiente));
			}
		}
		
		return relatorios;
	}

}
