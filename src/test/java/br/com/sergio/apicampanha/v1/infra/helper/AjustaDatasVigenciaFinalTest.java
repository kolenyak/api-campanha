package br.com.sergio.apicampanha.v1.infra.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.sergio.apicampanha.v1.model.Campanha;

public class AjustaDatasVigenciaFinalTest {
	
	private AjustaDatasVigenciaFinal vigencias = new AjustaDatasVigenciaFinal();
	
	@Test
	public void deveOrganizarAsDataFinaisQuandoExistemTresCampanhasNoMesmoPeríodo() {
		
		// GIVEN
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().id(1).nome("Camp1").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 5)));
		campanhasEsperadas.add(new Campanha().id(2).nome("Camp2").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 4)));
		
		List<Campanha> campanhas = new ArrayList<>();
		campanhas.add(new Campanha().id(1).nome("Camp1").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 3)));
		campanhas.add(new Campanha().id(2).nome("Camp2").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 2)));
		
		Campanha campanhaNova = new Campanha().nome("Camp5").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 3));

		// WHEN
		List<Campanha> campanhasAjustadas = vigencias.ajuste(campanhas, campanhaNova.getDataVigenciaFim());
		
		// THEN
		Assert.assertThat(campanhasAjustadas, Matchers.equalTo(campanhasEsperadas));
	}

	@Test
	public void deveOrganizarAsDataFinaisQuandoExistemCincoCampanhasNoMesmoPeríodo() {
		
		// GIVEN
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().id(1).nome("Camp1").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 9)));
		campanhasEsperadas.add(new Campanha().id(2).nome("Camp2").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 8)));
		campanhasEsperadas.add(new Campanha().id(3).nome("Camp3").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 7)));
		campanhasEsperadas.add(new Campanha().id(4).nome("Camp4").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 6)));
		
		List<Campanha> campanhas = new ArrayList<>();
		campanhas.add(new Campanha().id(1).nome("Camp1").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 5)));
		campanhas.add(new Campanha().id(2).nome("Camp2").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 4)));
		campanhas.add(new Campanha().id(3).nome("Camp3").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 3)));
		campanhas.add(new Campanha().id(4).nome("Camp4").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 2)));
		
		Campanha campanhaNova = new Campanha().nome("Camp5").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 5));

		// WHEN
		List<Campanha> campanhasAjustadas = vigencias.ajuste(campanhas, campanhaNova.getDataVigenciaFim());
		
		// THEN
		Assert.assertThat(campanhasAjustadas, Matchers.equalTo(campanhasEsperadas));
	}
	
	@Test
	public void deveOrganizarAsDataFinaisQuandoExistemTresCampanhasNoMesmoPeríodoComDatasMaisEspacadas() {
		
		// GIVEN
		List<Campanha> campanhasEsperadas = new ArrayList<>();
		campanhasEsperadas.add(new Campanha().id(1).nome("Camp1").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 12)));
		campanhasEsperadas.add(new Campanha().id(2).nome("Camp2").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 11)));
		
		List<Campanha> campanhas = new ArrayList<>();
		campanhas.add(new Campanha().id(1).nome("Camp1").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 10)));
		campanhas.add(new Campanha().id(2).nome("Camp2").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 5)));

		Campanha campanhaNova = new Campanha().nome("Camp3").dataVigenciaInicio(LocalDate.of(2017, 10, 1)).dataVigenciaFim(LocalDate.of(2017, 10, 10));
		
		// WHEN
		List<Campanha> campanhasAjustadas = vigencias.ajuste(campanhas, campanhaNova.getDataVigenciaFim());
		
		// THEN
		Assert.assertThat(campanhasAjustadas, Matchers.equalTo(campanhasEsperadas));
	}

}
