package br.com.sergio.apicampanha.v1.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sergio.apicampanha.v1.infra.helper.RelogioSistema;
import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.repository.CampanhaRepository;

/**
 * Teste de integração para o endpoint de /campanhas
 *
 * @author Sérgio
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CampanhasApiControllerIntegrationTest {
	
	private static final Integer CAMPANHA_UM = 1;
	private static final Integer CAMPANHA_DOIS = 2;
	private static final Integer CAMPANHA_TRES = 3;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Test
	public void deveRetornarCampanhaComIdUm() {

		ResponseEntity<Campanha> responseEntity = restTemplate.getForEntity("/campanhas/1", Campanha.class);
		Campanha campanha = responseEntity.getBody();
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		Assert.assertNotNull(campanha);
		Assert.assertEquals("1", campanha.getId().toString());
		Assert.assertEquals("Campanha Palmeiras 1", campanha.getNome());
	}
	
	@Test
	public void deveRetornarListaDeCampanhas() {
		
		RelogioSistema.fixeRelogioEm(LocalDate.of(2017, 11, 13));

		ResponseEntity<Campanha[]> responseEntity = restTemplate.getForEntity("/campanhas", Campanha[].class);
		Campanha[] campanha = responseEntity.getBody();
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		Assert.assertNotNull(campanha);
		Assert.assertEquals(3, campanha.length);
		Assert.assertEquals(CAMPANHA_UM, campanha[0].getId());
		Assert.assertEquals(CAMPANHA_DOIS, campanha[1].getId());
		Assert.assertEquals(CAMPANHA_TRES, campanha[2].getId());
		
		RelogioSistema.usarRelogioZonaPadrao();
	}
	
	@Test
	public void deveAtualizarACampanha() {
		
		// Pega os dados antes da alteração
		List<Campanha> backupDasCampanhas = preparaBackupDasCampanhas(1, LocalDate.of(2017, 11, 13), LocalDate.of(2017, 11, 16));
		
		Campanha campanhaEnvio = new Campanha()
				.id(1)
				.nome("Campanha 1")
				.idTimeCoracao(1)
				.dataVigenciaInicio(LocalDate.of(2017, 11, 13))
				.dataVigenciaFim(LocalDate.of(2017, 11, 16));
		
		HttpEntity<Campanha> entity = new HttpEntity<>(campanhaEnvio);
		ResponseEntity<Campanha> responseEntity2 = restTemplate.exchange("/campanhas/1", HttpMethod.PUT, entity, Campanha.class);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
		
		ResponseEntity<Campanha> responseEntity = restTemplate.getForEntity("/campanhas/1", Campanha.class);
		Campanha campanhaDepois = responseEntity.getBody();
		
		Assert.assertNotNull(campanhaDepois);
		Assert.assertEquals("1", campanhaDepois.getId().toString());
		Assert.assertEquals("1", campanhaDepois.getIdTimeCoracao().toString());
		Assert.assertEquals("Campanha 1", campanhaDepois.getNome());
		Assert.assertEquals("2017-11-13", campanhaDepois.getDataVigenciaInicio().format(DateTimeFormatter.ISO_LOCAL_DATE));
		Assert.assertEquals("2017-11-16", campanhaDepois.getDataVigenciaFim().format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		// Volta os dados originais
		rollbackDasCampanhas(backupDasCampanhas);
	}

	private List<Campanha> preparaBackupDasCampanhas(Integer id, LocalDate dataInicial, LocalDate dataFinal) {
		Campanha campanhaBackup = campanhaRepository.pesquisar(1);
		List<Campanha> campanhasNoPeriodo = campanhaRepository.pesquisarPeriodo(dataInicial, dataFinal);
		
		campanhasNoPeriodo.add(campanhaBackup);
		
		return campanhasNoPeriodo;
	}
	
	private void rollbackDasCampanhas(List<Campanha> campanhasDeBackup) {
		campanhasDeBackup.forEach(campanhaBackup -> 
			campanhaRepository.atualizar(
					campanhaBackup.getId(), 
					campanhaBackup.getNome(), 
					campanhaBackup.getIdTimeCoracao(), 
					campanhaBackup.getDataVigenciaInicio(), 
					campanhaBackup.getDataVigenciaFim(), 
					campanhaBackup.getDataVigenciaAlterada()));
	}

}
