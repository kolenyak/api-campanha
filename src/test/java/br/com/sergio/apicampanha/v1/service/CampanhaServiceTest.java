package br.com.sergio.apicampanha.v1.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.sergio.apicampanha.v1.infra.helper.AjustaDatasVigenciaFinal;
import br.com.sergio.apicampanha.v1.infra.helper.FiltroCampanhasVigentes;
import br.com.sergio.apicampanha.v1.infra.helper.RelogioSistema;
import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.repository.CampanhaRepository;
import br.com.sergio.apicampanha.v1.validator.CampanhaValidator;
import br.com.sergio.apicampanha.v1.validator.CampanhaValidator.Verifica;

@RunWith(MockitoJUnitRunner.class)
public class CampanhaServiceTest {
	
	@InjectMocks
	private CampanhaService campanhaService;
	
	@Mock
	private CampanhaValidator campanhaValidatorMock;
	
	@Mock
	private Verifica verificaMock;
	
	@Mock
	private CampanhaRepository campanhaRepositoryMock;
	
	@Mock
	private FiltroCampanhasVigentes filtroCampanhasVigentesMock;
	
	@Mock
	private AjustaDatasVigenciaFinal ajustaDatasVigenciaFinalMock;
	
	@After
	public void fimDosTestes() {
		RelogioSistema.usarRelogioZonaPadrao();
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaCriarCampanha() {
		
		// GIVEN
		Campanha campanhaVO = new Campanha()
			.id(1)
			.nome("Campanha Teste")
			.idTimeCoracao(10)
			.dataVigenciaInicio(LocalDate.of(2017, 11, 10))
			.dataVigenciaFim(LocalDate.of(2017, 11, 13));
		
		when(campanhaValidatorMock.verifica()).thenReturn(verificaMock);
		when(campanhaValidatorMock.verifica().seDataVigenciaInicioMaiorQueDataVigenciaFim(any(LocalDate.class), any(LocalDate.class))).thenReturn(verificaMock);
		when(campanhaRepositoryMock.pesquisarPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>());
		when(campanhaRepositoryMock.criar(anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);
		when(campanhaRepositoryMock.pesquisar(anyInt())).thenReturn(campanhaVO);
		
		// WHEN
		Campanha campanha = campanhaService.criaCampanha("Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		
		// THEN
		verify(verificaMock, times(1)).seDataVigenciaInicioMaiorQueDataVigenciaFim(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(verificaMock, times(1)).seTimeDoCoracaoExiste(10);
		
		verify(campanhaRepositoryMock, times(1)).pesquisarPeriodo(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		Mockito.verifyZeroInteractions(ajustaDatasVigenciaFinalMock);
		
		verify(campanhaRepositoryMock, times(1)).criar("Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(campanhaRepositoryMock, times(1)).pesquisar(1);
		assertThat(campanhaVO, equalTo(campanha));
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaCriarCampanhaComOutrasNoPeriodo() {
		
		// GIVEN
		LocalDate diaAtual = LocalDate.of(2017, 11, 14);
		RelogioSistema.fixeRelogioEm(diaAtual);
		
		Campanha campanhaVO = new Campanha()
			.id(1)
			.nome("Campanha Teste")
			.idTimeCoracao(10)
			.dataVigenciaInicio(LocalDate.of(2017, 11, 10))
			.dataVigenciaFim(LocalDate.of(2017, 11, 13));
		
		List<Campanha> campanhasNoPeriodo = new ArrayList<>();
		campanhasNoPeriodo.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		campanhasNoPeriodo.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 12)));
		
		List<Campanha> campanhasComPeriodosAjustados = new ArrayList<>();
		campanhasComPeriodosAjustados.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 15)));
		campanhasComPeriodosAjustados.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
		when(campanhaValidatorMock.verifica()).thenReturn(verificaMock);
		when(campanhaValidatorMock.verifica().seDataVigenciaInicioMaiorQueDataVigenciaFim(any(LocalDate.class), any(LocalDate.class))).thenReturn(verificaMock);
		when(campanhaRepositoryMock.pesquisarPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(campanhasNoPeriodo);
		when(ajustaDatasVigenciaFinalMock.ajuste(anyListOf(Campanha.class), any(LocalDate.class))).thenReturn(campanhasComPeriodosAjustados);
		when(campanhaRepositoryMock.criar(anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);
		when(campanhaRepositoryMock.pesquisar(anyInt())).thenReturn(campanhaVO);
		
		// WHEN
		Campanha campanha = campanhaService.criaCampanha("Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		
		// THEN
		// validação
		verify(verificaMock, times(1)).seDataVigenciaInicioMaiorQueDataVigenciaFim(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(verificaMock, times(1)).seTimeDoCoracaoExiste(10);
		
		// vigências no mesmo periodo
		verify(campanhaRepositoryMock, times(1)).pesquisarPeriodo(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(ajustaDatasVigenciaFinalMock, times(1)).ajuste(campanhasNoPeriodo, LocalDate.of(2017, 11, 13));
		
		// atualização das vigências
		verify(campanhaRepositoryMock, times(1)).atualizar(3, null, null, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 14), LocalDate.of(2017, 11, 14));
		verify(campanhaRepositoryMock, times(1)).atualizar(2, null, null, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 15), LocalDate.of(2017, 11, 14));
		
		// nova campannha
		verify(campanhaRepositoryMock, times(1)).criar("Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(campanhaRepositoryMock, times(1)).pesquisar(1);
		assertThat(campanhaVO, equalTo(campanha));
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaAtualizarCampanha() {
		
		// GIVEN
		when(campanhaValidatorMock.verifica()).thenReturn(verificaMock);
		when(campanhaValidatorMock.verifica().seCampanhaExiste(anyInt())).thenReturn(verificaMock);
		when(campanhaValidatorMock.verifica().seDataVigenciaInicioMaiorQueDataVigenciaFim(any(LocalDate.class), any(LocalDate.class))).thenReturn(verificaMock);
		when(campanhaRepositoryMock.pesquisarPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>());
		
		// WHEN
		campanhaService.atualizaCampanha(1, "Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		
		// THEN
		verify(verificaMock, times(1)).seCampanhaExiste(1);
		verify(verificaMock, times(1)).seDataVigenciaInicioMaiorQueDataVigenciaFim(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(verificaMock, times(1)).seTimeDoCoracaoExiste(10);
		
		verify(campanhaRepositoryMock, times(1)).pesquisarPeriodo(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		Mockito.verifyZeroInteractions(ajustaDatasVigenciaFinalMock);
		
		verify(campanhaRepositoryMock, times(1)).atualizar(1, "Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13), null);
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaAtualizarCampanhaComOutrasNoPeriodo() {
		
		// GIVEN
		List<Campanha> campanhasNoPeriodo = new ArrayList<>();
		campanhasNoPeriodo.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		campanhasNoPeriodo.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 12)));
		
		List<Campanha> campanhasComPeriodosAjustados = new ArrayList<>();
		campanhasComPeriodosAjustados.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 15)));
		campanhasComPeriodosAjustados.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));

		when(campanhaValidatorMock.verifica()).thenReturn(verificaMock);
		when(campanhaValidatorMock.verifica().seCampanhaExiste(anyInt())).thenReturn(verificaMock);
		when(campanhaValidatorMock.verifica().seDataVigenciaInicioMaiorQueDataVigenciaFim(any(LocalDate.class), any(LocalDate.class))).thenReturn(verificaMock);
		when(campanhaRepositoryMock.pesquisarPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(campanhasNoPeriodo);
		when(ajustaDatasVigenciaFinalMock.ajuste(anyListOf(Campanha.class), any(LocalDate.class))).thenReturn(campanhasComPeriodosAjustados);
		
		
		// WHEN
		campanhaService.atualizaCampanha(1, "Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		
		// THEN
		// validação
		verify(verificaMock, times(1)).seCampanhaExiste(1);
		verify(verificaMock, times(1)).seDataVigenciaInicioMaiorQueDataVigenciaFim(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(verificaMock, times(1)).seTimeDoCoracaoExiste(10);
		
		// vigências no mesmo periodo
		verify(campanhaRepositoryMock, times(1)).pesquisarPeriodo(LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13));
		verify(ajustaDatasVigenciaFinalMock, times(1)).ajuste(campanhasNoPeriodo, LocalDate.of(2017, 11, 13));
		
		// atualização das vigências
		verify(campanhaRepositoryMock, times(1)).atualizar(3, null, null, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 14), LocalDate.of(2017, 11, 14));
		verify(campanhaRepositoryMock, times(1)).atualizar(2, null, null, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 15), LocalDate.of(2017, 11, 14));
		
		// atualização da campannha
		verify(campanhaRepositoryMock, times(1)).atualizar(1, "Campanha Teste", 10, LocalDate.of(2017, 11, 10), LocalDate.of(2017, 11, 13), null);
	}

	@Test
	public void deveGarantirChamadasNasDependenciasParaRetornarUmaCampanha() {
		
		// GIVEN
		Campanha campanhaEsperada = new Campanha()
				.id(1)
				.nome("Campanha Teste")
				.idTimeCoracao(10)
				.dataVigenciaInicio(LocalDate.of(2017, 11, 10))
				.dataVigenciaFim(LocalDate.of(2017, 11, 13));
			
		when(campanhaValidatorMock.verifica()).thenReturn(verificaMock);
		when(campanhaRepositoryMock.pesquisar(anyInt())).thenReturn(campanhaEsperada);
			
		// WHEN
		Campanha campanha = campanhaService.retornaCampanha(1);
		
		// THEN
		verify(verificaMock, times(1)).seCampanhaExiste(1);
		verify(campanhaRepositoryMock, times(1)).pesquisar(1);
		assertThat(campanhaEsperada, equalTo(campanha));
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaRetornarCampanhasVigentes() {
		
		// GIVEN
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		campanhasEsperadas.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
		List<Campanha> todasCampanhas = new ArrayList<>();
		todasCampanhas.add(new Campanha().id(1).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 12)));
		todasCampanhas.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		todasCampanhas.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
			
		when(campanhaRepositoryMock.pesquisarTodos()).thenReturn(todasCampanhas);
		when(filtroCampanhasVigentesMock.filtra(anyListOf(Campanha.class))).thenReturn(campanhasEsperadas);
			
		// WHEN
		List<Campanha> campanhasVigentes = campanhaService.retornaCampanhasVigentes();
		
		// THEN
		verify(campanhaRepositoryMock, times(1)).pesquisarTodos();
		verify(filtroCampanhasVigentesMock, times(1)).filtra(todasCampanhas);
		assertThat(campanhasEsperadas, equalTo(campanhasVigentes));
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaRetornarCampanhasComVigenciaAlterada() {
		
		// GIVEN
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		campanhasEsperadas.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
		List<Campanha> todasCampanhas = new ArrayList<>();
		todasCampanhas.add(new Campanha().id(1).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 12)));
		todasCampanhas.add(new Campanha().id(2).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 13)));
		todasCampanhas.add(new Campanha().id(3).dataVigenciaInicio(LocalDate.of(2017, 11, 10)).dataVigenciaFim(LocalDate.of(2017, 11, 14)));
		
			
		when(campanhaRepositoryMock.pesquisarTodos()).thenReturn(todasCampanhas);
		when(filtroCampanhasVigentesMock.filtra(anyListOf(Campanha.class))).thenReturn(campanhasEsperadas);
			
		// WHEN
		List<Campanha> campanhasVigentes = campanhaService.retornaCampanhasVigentes();
		
		// THEN
		verify(campanhaRepositoryMock, times(1)).pesquisarTodos();
		verify(filtroCampanhasVigentesMock, times(1)).filtra(todasCampanhas);
		assertThat(campanhasEsperadas, equalTo(campanhasVigentes));
	}
	
	@Test
	public void deveGarantirChamadasNasDependenciasParaRemoverUmaCampanha() {
		
		// GIVEN
		when(campanhaValidatorMock.verifica()).thenReturn(verificaMock);
			
		// WHEN
		campanhaService.removeCampanha(1);
		
		// THEN
		verify(verificaMock, times(1)).seCampanhaExiste(1);
		verify(campanhaRepositoryMock, times(1)).apagar(1);
	}

}
