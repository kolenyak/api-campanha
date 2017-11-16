package br.com.sergio.apicampanha.v1.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Objeto de dados para Campanha
 * 
 * @author Sergio
 */
public class Campanha  {

	@JsonProperty("id")
	private Integer id = null;

	@JsonProperty("idTimeCoracao")
	private Integer idTimeCoracao = null;

	@JsonProperty("nome")
	private String nome = null;

	@JsonProperty("dataVigenciaInicio")
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate dataVigenciaInicio = null;

	@JsonProperty("dataVigenciaFim")
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate dataVigenciaFim = null;

	private LocalDate dataVigenciaAlterada;

	public Campanha id(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 * @return id
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Campanha idTimeCoracao(Integer idTimeCoracao) {
		this.idTimeCoracao = idTimeCoracao;
		return this;
	}

	/**
	 * Get idTimeCoracao
	 * @return idTimeCoracao
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull
	public Integer getIdTimeCoracao() {
		return idTimeCoracao;
	}

	public void setIdTimeCoracao(Integer idTimeCoracao) {
		this.idTimeCoracao = idTimeCoracao;
	}

	public Campanha nome(String nome) {
		this.nome = nome;
		return this;
	}

	/**
	 * Get nome
	 * @return nome
	 **/
	@ApiModelProperty(example = "Campanha 1", required = true, value = "")
	@NotNull @NotBlank
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Campanha dataVigenciaInicio(LocalDate dataVigenciaInicio) {
		this.dataVigenciaInicio = dataVigenciaInicio;
		return this;
	}

	/**
	 * Get dataVigenciaInicio
	 * @return dataVigenciaInicio
	 **/
	@ApiModelProperty(example = "2017-11-10", required = true, value = "")
	@NotNull
	@Valid
	public LocalDate getDataVigenciaInicio() {
		return dataVigenciaInicio;
	}

	public void setDataVigenciaInicio(LocalDate dataVigenciaInicio) {
		this.dataVigenciaInicio = dataVigenciaInicio;
	}

	public Campanha dataVigenciaFim(LocalDate dataVigenciaFim) {
		this.dataVigenciaFim = dataVigenciaFim;
		return this;
	}

	/**
	 * Get dataVigenciaFim
	 * @return dataVigenciaFim
	 **/
	@ApiModelProperty(example = "2017-11-13", required = true, value = "")
	@NotNull
	@Valid
	public LocalDate getDataVigenciaFim() {
		return dataVigenciaFim;
	}

	public void setDataVigenciaFim(LocalDate dataVigenciaFim) {
		this.dataVigenciaFim = dataVigenciaFim;
	}

	public LocalDate getDataVigenciaAlterada() {
		return dataVigenciaAlterada;
	}

	public void setDataVigenciaAlterada(LocalDate dataVigenciaAlterada) {
		this.dataVigenciaAlterada = dataVigenciaAlterada;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Campanha campanha = (Campanha) o;
		return Objects.equals(this.id, campanha.id) &&
				Objects.equals(this.idTimeCoracao, campanha.idTimeCoracao) &&
				Objects.equals(this.nome, campanha.nome) &&
				Objects.equals(this.dataVigenciaInicio, campanha.dataVigenciaInicio) &&
				Objects.equals(this.dataVigenciaFim, campanha.dataVigenciaFim);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idTimeCoracao, nome, dataVigenciaInicio, dataVigenciaFim);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Campanha {\n");
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    idTimeCoracao: ").append(toIndentedString(idTimeCoracao)).append("\n");
		sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
		sb.append("    dataVigenciaInicio: ").append(toIndentedString(dataVigenciaInicio)).append("\n");
		sb.append("    dataVigenciaFim: ").append(toIndentedString(dataVigenciaFim)).append("\n");
		sb.append("}");
		return sb.toString();
	}


	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}

