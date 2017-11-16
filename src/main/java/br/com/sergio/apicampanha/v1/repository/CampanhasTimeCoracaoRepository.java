package br.com.sergio.apicampanha.v1.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.repository.rowmapper.CampanhaRowMapper;

/**
 * Repositório para dados do campanhas e times com conexão jdbc
 * 
 * @author Sérgio
 */
@Repository
public class CampanhasTimeCoracaoRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Value("${campanha.cmd.select.where.idtime}")
	private String comandoSqlSelectWhereIdTime;
	
	@Value("${campanha.cmd.select.where.idtime-idcliente}")
	private String comandoSqlSelectWhereIdTimeAndIdCliente;
	
	public List<Campanha> pesquisar(Integer idTime) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idTime", idTime);
		
		return jdbcTemplate.query(comandoSqlSelectWhereIdTime, parametros, new CampanhaRowMapper());
	}
	
	public List<Campanha> pesquisar(Integer idTime, Integer idCliente) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idTime", idTime)
			.addValue("idCliente", idCliente);
		
		return jdbcTemplate.query(comandoSqlSelectWhereIdTimeAndIdCliente, parametros, new CampanhaRowMapper());
	}

}
