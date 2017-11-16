package br.com.sergio.apicampanha.v1.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sergio.apicampanha.v1.infra.helper.AjustaDatasVigenciaFinal;
import br.com.sergio.apicampanha.v1.infra.helper.FiltroCampanhasVigentes;
import br.com.sergio.apicampanha.v1.infra.helper.RelogioSistema;
import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.repository.CampanhaRepository;
import br.com.sergio.apicampanha.v1.validator.CampanhaValidator;

/**
 * Classe de serviços das Campanhas
 * 
 * @author Sérgio
 */
@Service
public class CampanhaService {
	
	@Autowired
	private CampanhaValidator campanhaValidator;
	
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private FiltroCampanhasVigentes filtroCampanhasVigentes;
	
	@Autowired
	private AjustaDatasVigenciaFinal ajustaDatasVigenciaFinal; 

	public Campanha criaCampanha(
			String nomeCampanha,
			Integer idTimeCoracao,
			LocalDate dataVigenciaInicio,
			LocalDate dataVigenciaFim) {
		
		campanhaValidator.verifica()
			.seDataVigenciaInicioMaiorQueDataVigenciaFim(dataVigenciaInicio, dataVigenciaFim)
			.seTimeDoCoracaoExiste(idTimeCoracao);
		
		ajustaCampanhasComVigenciaNoPeriodo(dataVigenciaInicio, dataVigenciaFim);
		
		Integer idCampanha = campanhaRepository.criar(
				nomeCampanha, 
				idTimeCoracao, 
				dataVigenciaInicio, 
				dataVigenciaFim);
		
		Campanha vo = campanhaRepository.pesquisar(idCampanha);
		
		return vo;
	}

	/**
	 * Caso existam campanhas com data final de vigência no período da nova campanha elas deveram ser atualizadas
	 * 
	 * @param dataVigenciaInicio {@link LocalDate}
	 * @param dataVigenciaFim {@link LocalDate}
	 */
	private void ajustaCampanhasComVigenciaNoPeriodo(LocalDate dataVigenciaInicio, LocalDate dataVigenciaFim) {
		List<Campanha> campanhasNoPeriodo = campanhaRepository.pesquisarPeriodo(dataVigenciaInicio, dataVigenciaFim);
		
		if(campanhasNoPeriodo.isEmpty()) {
			return;
		}
		
		List<Campanha> campanhasComNovoPeriodo = ajustaDatasVigenciaFinal.ajuste(campanhasNoPeriodo, dataVigenciaFim);
		
		campanhasComNovoPeriodo.forEach(c -> campanhaRepository.atualizar(
				c.getId(), c.getNome(), c.getIdTimeCoracao(), c.getDataVigenciaInicio(), c.getDataVigenciaFim(), RelogioSistema.hoje()));
	}
	
	public void atualizaCampanha(
			Integer idCampanha,
			String nomeCampanha,
			Integer idTimeCoracao,
			LocalDate dataVigenciaInicio,
			LocalDate dataVigenciaFim) {
		
		campanhaValidator.verifica()
			.seCampanhaExiste(idCampanha)
			.seDataVigenciaInicioMaiorQueDataVigenciaFim(dataVigenciaInicio, dataVigenciaFim)
			.seTimeDoCoracaoExiste(idTimeCoracao);
		
		ajustaCampanhasComVigenciaNoPeriodo(dataVigenciaInicio, dataVigenciaFim);
		
		campanhaRepository.atualizar(idCampanha, nomeCampanha, idTimeCoracao, dataVigenciaInicio, dataVigenciaFim, null);
	}
	
	public Campanha retornaCampanha(Integer idCampanha) {
		
		campanhaValidator.verifica().seCampanhaExiste(idCampanha);
		
		return campanhaRepository.pesquisar(idCampanha);
	}
	
	public List<Campanha> retornaCampanhasVigentes() {
		
		List<Campanha> todas = campanhaRepository.pesquisarTodos();
		
		return filtroCampanhasVigentes.filtra(todas);
	}
	
	public List<Campanha> retornaCampanhasComVigenciaAlteradas() {
		
		List<Campanha> todas = campanhaRepository.pesquisarTodos();
		
		return filtroCampanhasVigentes.filtra(todas);
	}

	public void removeCampanha(Integer idCampanha) {
		
		campanhaValidator.verifica().seCampanhaExiste(idCampanha);

		campanhaRepository.apagar(idCampanha);
	}
	
}
