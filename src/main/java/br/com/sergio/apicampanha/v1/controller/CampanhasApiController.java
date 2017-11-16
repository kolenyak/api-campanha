package br.com.sergio.apicampanha.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.model.ApiPostCampanha;
import br.com.sergio.apicampanha.v1.service.CampanhaClienteService;
import br.com.sergio.apicampanha.v1.service.CampanhaService;

@RestController
public class CampanhasApiController implements CampanhasApi {

	@Autowired
	private CampanhaService campanhaService;

	@Autowired
	private CampanhaClienteService campanhaClienteService;

	@Override
	@Transactional
	public void atualizaCampanha(
			@PathVariable("idCampanha") Integer idCampanha, 
			@Valid @RequestBody Campanha apiCampanhaVO) {

		campanhaService.atualizaCampanha(
				idCampanha, 
				apiCampanhaVO.getNome(), 
				apiCampanhaVO.getIdTimeCoracao(),
				apiCampanhaVO.getDataVigenciaInicio(),
				apiCampanhaVO.getDataVigenciaFim());
	}

	@Override
	public	void associaCampanhasComCliente(
			@PathVariable("idCliente") Integer idCliente,
			@Valid @RequestBody List<Integer> idsCampanha){
			
		campanhaClienteService.associar(idCliente, idsCampanha);
	}

	@Override
	public Campanha criaCampanha(@Valid @RequestBody ApiPostCampanha dados) {

		Campanha apiCampanhaVO = campanhaService.criaCampanha(
				dados.getNome(), 
				dados.getIdTimeCoracao(), 
				dados.getDataVigenciaInicio(), 
				dados.getDataVigenciaFim());
		
		return apiCampanhaVO;
	}

	@Override
	public List<Campanha> listaCampanha() {

		return campanhaService.retornaCampanhasVigentes();
	}

	@Override
	public List<Campanha> listaCampanhaVigenciaAlterada() {

		return campanhaService.retornaCampanhasComVigenciaAlteradas();
	}

	@Override
	public Campanha listaUmaCampanha(@PathVariable("idCampanha") Integer idCampanha) {

		return campanhaService.retornaCampanha(idCampanha);
	}

	@Override
	public void removeCampanha(@PathVariable("idCampanha") Integer idCampanha) {

		campanhaService.removeCampanha(idCampanha);
	}

}
