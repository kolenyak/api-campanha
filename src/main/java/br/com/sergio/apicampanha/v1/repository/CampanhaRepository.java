package br.com.sergio.apicampanha.v1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.repository.rowmapper.CampanhaRowMapper;

/**
 * Repositório para dados da Campanha com conexão jdbc
 * 
 * @author Sérgio
 */
@Repository
public class CampanhaRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Value("${campanha.cmd.insert}")
	private String comandoSqlInsert;
	
	@Value("${campanha.cmd.update}")
	private String comandoSqlUpdate;
	
	@Value("${campanha.cmd.select.where.id}")
	private String comandoSqlSelectWhereId;
	
	@Value("${campanha.cmd.select}")
	private String comandoSqlSelect;
	
	@Value("${campanha.cmd.select.count}")
	private String comandoSqlSelectCount;
	
	@Value("${campanha.cmd.delete.where.id}")
	private String comandoSqlDeleteWhereId;

	@Value("${campanha.cmd.select.where.datainicio-datafim}")
	private String comandoSqlDeleteWhereDataInicioDataFim;
	
	public Integer criar(
			String nomeCampanha,
			Integer idTimeCoracao,
			LocalDate dataVigenciaInicio,
			LocalDate dataVigenciaFim) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("nomeCampanha", nomeCampanha)
			.addValue("idTimeCoracao", idTimeCoracao)
			.addValue("dataVigenciaInicio", dataVigenciaInicio)
			.addValue("dataVigenciaFim", dataVigenciaFim);
		
		KeyHolder chaveiro = new GeneratedKeyHolder();
		jdbcTemplate.update(comandoSqlInsert, parametros, chaveiro);
		try {
			return chaveiro.getKey().intValue();
		} catch (DataRetrievalFailureException e){
			return null;
		}
	}
	
	public void atualizar(
			Integer idCampanha,
			String nomeCampanha,
			Integer idTimeCoracao,
			LocalDate dataVigenciaInicio,
			LocalDate dataVigenciaFim,
			LocalDate dataVigenciaAlterada) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idCampanha", idCampanha)
			.addValue("nomeCampanha", nomeCampanha)
			.addValue("idTimeCoracao", idTimeCoracao)
			.addValue("dataVigenciaInicio", dataVigenciaInicio)
			.addValue("dataVigenciaFim", dataVigenciaFim)
			.addValue("dataVigenciaAlterada", dataVigenciaAlterada);
		
		jdbcTemplate.update(comandoSqlUpdate, parametros);
	}
	
	public Campanha pesquisar(Integer idCampanha) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idCampanha", idCampanha);
		
		try {
			return jdbcTemplate.queryForObject(comandoSqlSelectWhereId, parametros, new CampanhaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return  null;
		}
	}
	
	public List<Campanha> pesquisarTodos() {
		
		return jdbcTemplate.query(comandoSqlSelect, new CampanhaRowMapper());
	}
	
	public List<Campanha> pesquisarPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
				.addValue("dataInicial", dataInicial)
				.addValue("dataFinal", dataFinal);
		
		return jdbcTemplate.query(comandoSqlDeleteWhereDataInicioDataFim, parametros, new CampanhaRowMapper());
	}
	
	
	public void apagar(Integer idCampanha) {
		SqlParameterSource parametros = new MapSqlParameterSource()
				.addValue("idCampanha", idCampanha);
		
		jdbcTemplate.update(comandoSqlDeleteWhereId, parametros);
	}
	
	public boolean exists(Integer idCampanha) {
		
		SqlParameterSource parametros = new MapSqlParameterSource()
			.addValue("idCampanha", idCampanha);
		
		Integer quantidade = jdbcTemplate.queryForObject(comandoSqlSelectCount, parametros, Integer.class);
		
		return quantidade > 0;
	}

}
