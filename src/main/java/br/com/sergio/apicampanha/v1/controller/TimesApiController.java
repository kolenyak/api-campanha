package br.com.sergio.apicampanha.v1.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.service.TimeService;

@RestController
public class TimesApiController implements TimesApi {
	
	@Autowired
	private TimeService timeService;

	@Override
    public List<Campanha> listaCampanhasPeloTime(
    		@PathVariable("idTime") Integer idTime) {
        
		return timeService.retornaCampanhasDoTime(idTime);
    }

	@Override
    public List<Campanha> listaCampanhasPeloTimeECliente(
    		@PathVariable("idTime") Integer idTime,
    		@PathVariable("idCliente") Integer idCliente) {
		
		return timeService.retornaCampanhasDoTimeECliente(idTime, idCliente);
    }

}
