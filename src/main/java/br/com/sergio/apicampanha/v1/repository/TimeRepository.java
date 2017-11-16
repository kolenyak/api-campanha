package br.com.sergio.apicampanha.v1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Repositório para dados do TimeO com conexão jdbc
 * 
 * @author Sérgio
 */
@Repository
public class TimeRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Value("${time.cmd.select.count}")
	private String comandoSqlSelectCount;
	
	public boolean exists(Integer idTime) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idTime", idTime);
		
		Integer quantidade = jdbcTemplate.queryForObject(comandoSqlSelectCount, parametros, Integer.class);
		
		return quantidade > 0;
	}
	
}
