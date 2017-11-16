package br.com.sergio.apicampanha.v1.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.repository.CampanhasTimeCoracaoRepository;
import br.com.sergio.apicampanha.v1.validator.TimeCoracaoValidator;

@Service
public class TimeService {
	
	@Autowired
	private TimeCoracaoValidator timeCoracaoValidator;
	
	@Autowired
	private CampanhasTimeCoracaoRepository repository;
	
	public List<Campanha> retornaCampanhasDoTime(Integer idTime) {
		
		timeCoracaoValidator.verifica()
			.seTimeExiste(idTime);
		
		List<Campanha> list = repository.pesquisar(idTime);
		
		list.stream().filter(vo -> vo.getDataVigenciaFim().isBefore(LocalDate.now()));
		
		return list;
	}
	
	public List<Campanha> retornaCampanhasDoTimeECliente(Integer idTime, Integer idCliente) {
		
		timeCoracaoValidator.verifica()
			.seTimeExiste(idTime)
			.seClienteExiste(idCliente);
		
		List<Campanha> list = repository.pesquisar(idTime, idCliente);
		
		list.stream().filter(vo -> vo.getDataVigenciaFim().isBefore(LocalDate.now()));
		
		return list;
	}
	
}
