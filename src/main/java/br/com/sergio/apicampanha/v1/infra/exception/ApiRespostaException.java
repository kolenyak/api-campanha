package br.com.sergio.apicampanha.v1.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.sergio.apicampanha.v1.model.ApiRespostaErro;

/**
 * ApiRespostaException
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiRespostaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444873869654834422L;

	private ApiRespostaErro apiRespostaErro; 

	public ApiRespostaException(String parametro, String mensagem) {
		super(mensagem);
		this.apiRespostaErro = new ApiRespostaErro().parametro(parametro).mensagem(mensagem);
	}

	public ApiRespostaErro getApiRespostaErro() {
		return apiRespostaErro;
	}
	
}

