package br.com.sergio.apicampanha.v1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sergio.apicampanha.v1.repository.CampanhaClienteRepository;
import br.com.sergio.apicampanha.v1.validator.AssociacaoCampanhaClienteValidator;

@Service
public class CampanhaClienteService {
	
	@Autowired
	private CampanhaClienteRepository campanhaClienteRepository;
	
	@Autowired
	private AssociacaoCampanhaClienteValidator associacaoValidator;

	@Transactional
	public void associar(Integer idCliente, List<Integer> idsCampanha) {
		
		associacaoValidator.verifica()
			.seClienteExiste(idCliente)
			.seCampanhasExistem(idsCampanha);
		
		idsCampanha.stream()
			.filter(idCampanha -> !campanhaClienteRepository.exists(idCampanha, idCliente))
			.forEach(idCampanha -> campanhaClienteRepository.criar(idCampanha, idCliente));
	}
	
}
