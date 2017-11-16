package br.com.sergio.apicampanha.v1.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ApiRepostaErro
 */
public class ApiRespostaErro   {
	
  @JsonProperty("parametro")
  private String parametro = null;

  @JsonProperty("mensagem")
  private String mensagem = null;

  public ApiRespostaErro parametro(String parametro) {
    this.parametro = parametro;
    return this;
  }

   /**
   * Get parametro
   * @return parametro
  **/
  @ApiModelProperty(example = "parametroX", value = "")
  public String getParametro() {
    return parametro;
  }

  public void setParametro(String parametro) {
    this.parametro = parametro;
  }

  public ApiRespostaErro mensagem(String mensagem) {
    this.mensagem = mensagem;
    return this;
  }

   /**
   * Get mensagem
   * @return mensagem
  **/
  @ApiModelProperty(example = "Parametro X inválido", value = "")
  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiRespostaErro apiRepostaErro = (ApiRespostaErro) o;
    return Objects.equals(this.parametro, apiRepostaErro.parametro) &&
        Objects.equals(this.mensagem, apiRepostaErro.mensagem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parametro, mensagem);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ApiRepostaErro {\n");
    sb.append("    parametro: ").append(toIndentedString(parametro)).append("\n");
    sb.append("    mensagem: ").append(toIndentedString(mensagem)).append("\n");
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

