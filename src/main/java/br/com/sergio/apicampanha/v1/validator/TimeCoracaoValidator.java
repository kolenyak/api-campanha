package br.com.sergio.apicampanha.v1.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import br.com.sergio.apicampanha.v1.repository.ClienteRepository;
import br.com.sergio.apicampanha.v1.repository.TimeRepository;

/**
 * Responsável por verificar os dados da entidade
 * 
 * @author Sérgio
 */
@Component
public class TimeCoracaoValidator {
	
	@Autowired
	private TimeRepository timeRepository;
	
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
		 * Verifica se o time existe
		 * 
		 * @param idTime
		 * @return {@link Verifica}
		 */
		public Verifica seTimeExiste(Integer idTime) {
			
			if(!timeRepository.exists(idTime)) {
				throw new ResourceNotFoundException("Time não encontrado");
			}
			
			return this;
		}
		
	}
	
	

}
