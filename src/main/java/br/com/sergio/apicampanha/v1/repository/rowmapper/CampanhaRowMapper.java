package br.com.sergio.apicampanha.v1.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.sergio.apicampanha.v1.model.Campanha;

/**
 * Responsável por mapear o registro na modelo {@link Campanha}
 * 
 * @author Sérgio
 */
public class CampanhaRowMapper implements RowMapper<Campanha> {
	
	@Override
	public Campanha mapRow(ResultSet rs, int rowNum) throws SQLException {
		Campanha vo = new Campanha();
		vo.setId(rs.getInt("id"));
		vo.setIdTimeCoracao(rs.getInt("id_time_coracao"));
		vo.setNome(rs.getString("nome"));
		vo.setDataVigenciaInicio(rs.getDate("data_vigencia_inicio").toLocalDate());
		vo.setDataVigenciaFim(rs.getDate("data_vigencia_fim").toLocalDate());
		return vo;
	}
	
}