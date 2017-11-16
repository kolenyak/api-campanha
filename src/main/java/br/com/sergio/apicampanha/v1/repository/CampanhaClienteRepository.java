package br.com.sergio.apicampanha.v1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Repositório para dados de Campanha/Cliente com conexão jdbc
 * 
 * @author Sérgio
 */
@Repository
public class CampanhaClienteRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Value("${campanha-cliente.cmd.insert}")
	private String comandoSqlInsert;
	
	@Value("${campanha-cliente.cmd.select.count}")
	private String comandoSqlSelectCount;

	public void criar(
			Integer idCampanha,
			Integer idCliente) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idCampanha", idCampanha)
			.addValue("idCliente", idCliente);
		
		jdbcTemplate.update(comandoSqlInsert, parametros);
	}
	
	public boolean exists(Integer idCampanha, Integer idCliente) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idCampanha", idCampanha)
			.addValue("idCliente", idCliente);
		
		Integer quantidade = jdbcTemplate.queryForObject(comandoSqlSelectCount, parametros, Integer.class);
		
		return quantidade > 0;
	}
	
}
