package br.com.sergio.apicampanha.v1.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import br.com.sergio.apicampanha.v1.infra.exception.ApiRespostaException;
import br.com.sergio.apicampanha.v1.repository.CampanhaRepository;
import br.com.sergio.apicampanha.v1.repository.ClienteRepository;

/**
 * Responsável por verificar os dados da entidade
 * 
 * @author Sérgio
 */
@Component
public class AssociacaoCampanhaClienteValidator {
	
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Verifica verifica() {
		return new Verifica();
	}
	
	
	public class Verifica {
		
		/**
		 * Verifica se a cliente existe
		 * 
		 * @param idCampanha
		 * @return {@link Verifica}
		 */
		public Verifica seClienteExiste(Integer idCliente) {
			
			if(!clienteRepository.exists(idCliente)) {
				throw new ResourceNotFoundException("Cliente não encontrado");
			}
				
			return this;
		}
		
		/**
		 * Verifica se as campanhas existem
		 * 
		 * @param idsCampanha
		 * @return {@link Verifica}
		 */
		public Verifica seCampanhasExistem(List<Integer> idsCampanha) {
			
			List<Integer> idsNaoEncontrados = idsCampanha.stream()
					.filter(idCampanha -> !campanhaRepository.exists(idCampanha))
					.collect(Collectors.toList());
			
			if(!idsNaoEncontrados.isEmpty()) {
				String mensagem = "Campanha(s) ".concat(idsNaoEncontrados.toString()).concat(" não encontrada(s)");
				throw new ApiRespostaException("idsCampanha", mensagem);
			}
			
			return this;
		}
		
	}
	
	

}
