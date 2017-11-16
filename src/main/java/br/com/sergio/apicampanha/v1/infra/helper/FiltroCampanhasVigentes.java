package br.com.sergio.apicampanha.v1.infra.helper;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.sergio.apicampanha.v1.model.Campanha;

/**
 * Representa o filtro das campanhas vigentes
 * 
 * @author Sérgio
 */
@Component
public class FiltroCampanhasVigentes {
	
	/**
	 * Data final da vigência não pode ser anterior a data do dia atual
	 * 
	 * @param campanhas {@link List} of {@link Campanha}
	 * @return {@link List} of {@link Campanha}
	 */
	public List<Campanha> filtra(List<Campanha> campanhas){
		return campanhas.stream()
				.filter(vo -> !vo.getDataVigenciaFim().isBefore(RelogioSistema.hoje()))
				.collect(Collectors.toList());
	}
	

}
