package br.com.sergio.apicampanha.v1.infra.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.sergio.apicampanha.v1.model.Campanha;

/**
 * Responsável por ajustar as datas de vigência final
 * 
 * @author Sérgio
 */
@Component
public class AjustaDatasVigenciaFinal {

	/**
	 * Ajusta a data final das campanhas de acordo com a data base
	 * 
	 * @param campanhas {@link List} of {@link Campanha}
	 * @param database {@link LocalDate}
	 * @return {@link List} of {@link Campanha}
	 */
	public List<Campanha> ajuste(List<Campanha> campanhas, LocalDate database){

		List<Campanha> campanhasOrganizadas = new ArrayList<>(campanhas);
		
		ordenaPelaDataDeVigenciaFinalMenor(campanhasOrganizadas);
		
		incrementaDataFinal(campanhasOrganizadas, database);
		
		return campanhas;
	}
	
	/**
	 * @param campanhas {@link List} of {@link Campanha}
	 */
	private void ordenaPelaDataDeVigenciaFinalMenor(List<Campanha> campanhas) {
		campanhas.sort(new Comparator<Campanha>() {
			@Override
			public int compare(Campanha o1, Campanha o2) {
				return o1.getDataVigenciaFim().compareTo(o2.getDataVigenciaFim());
			}
		});
	}
	
	/**
	 * @param campanhas {@link List} of {@link Campanha}
	 * @param database {@link LocalDate}
	 */
	private void incrementaDataFinal(List<Campanha> campanhas, LocalDate database) {
		int count = 1;
		for(Campanha vo : campanhas) {
			vo.setDataVigenciaFim(database.plusDays(count++));
		}
	}
	
	
}
