package br.com.sergio.apicampanha.v1.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import br.com.sergio.apicampanha.v1.infra.exception.ApiRespostaException;
import br.com.sergio.apicampanha.v1.repository.CampanhaRepository;
import br.com.sergio.apicampanha.v1.repository.TimeRepository;

/**
 * Responsável por verificar os dados da entidade
 * 
 * @author Sérgio
 */
@Component
public class CampanhaValidator {
	
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private TimeRepository timeRepository;
	
	public Verifica verifica() {
		return new Verifica();
	}
	
	
	public class Verifica {
		
		/**
		 * Verifica se a campanha existe
		 * 
		 * @param idCampanha
		 * @return {@link Verifica}
		 */
		public Verifica seCampanhaExiste(Integer idCampanha) {
			
			if(!campanhaRepository.exists(idCampanha)) {
				throw new ResourceNotFoundException("Campanha não encontrada");
			}
			
			return this;
		}
		
		/**
		 * Verifica se a data inicial é maior que a data final
		 * 
		 * @param dataVigenciaInicio {@link LocalDate}
		 * @param dataVigenciaFim {@link LocalDate}
		 * @return {@link Verifica}
		 */
		public Verifica seDataVigenciaInicioMaiorQueDataVigenciaFim(LocalDate dataVigenciaInicio, LocalDate dataVigenciaFim) {
			
			if(dataVigenciaInicio.isAfter(dataVigenciaFim)) {
				throw new ApiRespostaException("dataVigenciaInicio", "Data inicial maior que a data final");
			}
			
			return this;
		}
		
		/**
		 * Verifica se o time existe
		 * 
		 * @param idTimeCoracao
		 * @return {@link Verifica}
		 */
		public Verifica seTimeDoCoracaoExiste(Integer idTimeCoracao) {
			
			if(!timeRepository.exists(idTimeCoracao)) {
				throw new ApiRespostaException("idTimeCoracao", "Esse time não foi encontrado");
			}
			
			return this;
		}
		
	}
	
	

}
