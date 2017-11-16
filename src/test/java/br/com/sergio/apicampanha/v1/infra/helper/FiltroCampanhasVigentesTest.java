package br.com.sergio.apicampanha.v1.infra.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.sergio.apicampanha.v1.model.Campanha;

/**
 * Teste da Function para filtrar campanhas vigentes
 * DataFinal menor que a data do dia corrente
 * 
 * @author Sérgio
 */
@RunWith(MockitoJUnitRunner.class)
public class FiltroCampanhasVigentesTest {
	
	@InjectMocks
	private FiltroCampanhasVigentes filtroCampanhasVigentes;
	
	@After
	public void fimDosTestes() {
		RelogioSistema.usarRelogioZonaPadrao();
	}

	@Test
	public void deveRetornarTrueParaDataPosteriorAoDiaAtual() {
		
		// GIVEN
		LocalDate diaAtual = LocalDate.of(2017, 11, 13);
		RelogioSistema.fixeRelogioEm(diaAtual);
		
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
		// WHEN
		List<Campanha> campanhasVigentes = filtroCampanhasVigentes.filtra(campanhasEsperadas);
		
		// THEN
		Assert.assertEquals(campanhasEsperadas, campanhasVigentes);
	}
	
	@Test
	public void deveRetornarTrueParaDataIgualAoDiaAtual() {
		
		// GIVEN
		LocalDate diaAtual = LocalDate.of(2017, 11, 13);
		RelogioSistema.fixeRelogioEm(diaAtual);
		
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		
		// WHEN
		List<Campanha> campanhasVigentes = filtroCampanhasVigentes.filtra(campanhasEsperadas);
		
		// THEN
		Assert.assertEquals(campanhasEsperadas, campanhasVigentes);
	}
	
	@Test
	public void deveRetornarFalsoParaDataAnteriorAoDiaAtual() {
		
		// GIVEN
		LocalDate diaAtual = LocalDate.of(2017, 11, 13);
		RelogioSistema.fixeRelogioEm(diaAtual);
		
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 12)));
		
		// WHEN
		List<Campanha> campanhasVigentes = filtroCampanhasVigentes.filtra(campanhasEsperadas);
		
		// THEN
		Assert.assertTrue(campanhasVigentes.isEmpty());
	}
	
	@Test
	public void deveRetornarApenasDuasCampanhasQueAindaEstaoVigentes() {
		
		// GIVEN
		LocalDate diaAtual = LocalDate.of(2017, 11, 13);
		RelogioSistema.fixeRelogioEm(diaAtual);
		
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		campanhasEsperadas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
		List<Campanha> campanhas = new ArrayList<>();
		campanhas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 12)));
		campanhas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		campanhas.add(new Campanha().dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
		// WHEN
		List<Campanha> campanhasVigentes = filtroCampanhasVigentes.filtra(campanhasEsperadas);
		
		// THEN
		Assert.assertThat(campanhasEsperadas, Matchers.equalTo(campanhasVigentes));
	}

}
