/**
 * 
 */
package br.com.sergio.apicampanha.v1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.sergio.apicampanha.v1.infra.exception.ApiRespostaException;
import br.com.sergio.apicampanha.v1.model.ApiRespostaErro;

/**
 * Tratamento de exceções na resposta das requisições
 *  
 * @author Sérgio
 */

@ControllerAdvice
public class ExceptionHandlerController {

	/**
	 * Trata exceções de Argumentos Ilegais.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param exception {@link IllegalArgumentException}
	 * @return VO {@link ApiRespostaErro} com as informações sobre o erro
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody ApiRespostaErro 
	handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException exception) {
		return new ApiRespostaErro().parametro("").mensagem(exception.getLocalizedMessage());
	}
	
	/**
	 * Trata exceções de Argumentos Ilegais.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param exception {@link ResourceNotFoundException}
	 * @return VO {@link ApiRespostaErro} com as informações sobre o erro
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody ApiRespostaErro 
	handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException exception) {
		
		return new ApiRespostaErro()
				.parametro("URI: ".concat(req.getRequestURI()))
				.mensagem(exception.getLocalizedMessage());
	}
	
	/**
	 * Trata exceções de Argumentos Ilegais.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param exception {@link IllegalArgumentException}
	 * @return VO {@link ApiRespostaErro} com as informações sobre o erro
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody List<ApiRespostaErro>
	handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException exception) {
		
		List<ApiRespostaErro> errors = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(e -> {
			errors.add(new ApiRespostaErro().parametro(e.getField()).mensagem(e.getDefaultMessage()));
		});
		
		return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody ApiRespostaErro
	handleMethodArgumentTypeMismatchException(HttpServletRequest req, MethodArgumentTypeMismatchException exception) {
		return new ApiRespostaErro().parametro("").mensagem(exception.getLocalizedMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody ApiRespostaErro
	handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException exception) {
		if (exception.getCause() != null && 
				(exception.getCause() instanceof JsonMappingException 
						|| exception.getCause() instanceof JsonParseException)) {
			 return new ApiRespostaErro().parametro("").mensagem("Could not read document");			 
		}
		if(exception.getMessage().startsWith("Required request body is missing")){
			return new ApiRespostaErro().parametro("").mensagem("Required request body is missing");
		}
		return new ApiRespostaErro().parametro("").mensagem(exception.getLocalizedMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiRespostaException.class)
	@ResponseBody ApiRespostaErro
	handleHttpMessageNotReadableException(HttpServletRequest req, ApiRespostaException exception) {
		
		if(exception.getApiRespostaErro() != null) {
			return exception.getApiRespostaErro();
		}
		
		return new ApiRespostaErro().parametro("").mensagem(exception.getMessage());
	}
	
	
	/**
	 * Trata exceções.
	 * 
	 * @param exception
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { RuntimeException.class, Exception.class })
	@ResponseBody ApiRespostaErro
	handleUncaughtException(HttpServletRequest req, RuntimeException exception) {
		return new ApiRespostaErro().parametro("").mensagem("Erro inesperado. Contate o administrador do sistema.");
	}
	
}
