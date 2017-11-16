/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package br.com.sergio.apicampanha.v1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.sergio.apicampanha.v1.model.Campanha;
import br.com.sergio.apicampanha.v1.model.ApiPostCampanha;
import br.com.sergio.apicampanha.v1.model.ApiRespostaErro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Interface com a documentação para API de Campanhas
 * 
 * @author Sérgio
 */
@Api(value = "campanhas")
public interface CampanhasApi {

	/**
	 * Endpoint para receber as atualizações dos dados da Campanha
	 * 
	 * @param dados {@link Campanha}
	 */
	@ApiOperation(value = "Atualiza campanha", 
			notes = "Atualiza os dados da campanha ", 
			tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Campanha atualizada com sucesso"),
			@ApiResponse(code = 400, message = "Dados de entrada inválidos", response = ApiRespostaErro.class),
			@ApiResponse(code = 404, message = "Campanha não encontrada", response = ApiRespostaErro.class )})
	@RequestMapping(value = "/campanhas/{idCampanha}",
		produces = { "application/json" }, 
		consumes = { "application/json" },
		method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	void atualizaCampanha(
			@ApiParam(value = "Id da campanha pesquisada", required=true ) Integer idCampanha,
			@ApiParam(value = "Dados necessário para atualizar a Campanha", required=true) Campanha dados);


	/**
	 * Endpoint para associar as campanhas ao cliente
	 * 
	 * @param idCliente
	 * @param idsCampanha
	 */
	@ApiOperation(value = "Associa as Campanhas ao Cliente", 
				notes = "Faz a associação da lista de ids das campanhas ao id do cliente", 
				tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Associação realizada com sucesso"),
			@ApiResponse(code = 400, message = "Dados de entrada inválidos", response = ApiRespostaErro.class),
			@ApiResponse(code = 404, message = "Cliente não encontrado") })
	@RequestMapping(value = "/campanhas/clientes/{idCliente}",
		produces = { "application/json" }, 
		consumes = { "application/json" },
		method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	void associaCampanhasComCliente(
			@ApiParam(value = "Id do Cliente a ser associado", required=true) Integer idCliente,
			@ApiParam(value = "Ids das campanhas para associação", required=true) List<Integer> idsCampanha);


	/**
	 * Endpoint para receber os dados necessários para criar a Campanha
	 * 
	 * @param dados {@link ApiPostCampanha}
	 * @return {@link Campanha}
	 */
	@ApiOperation(value = "Cria campanha", notes = "", response = Campanha.class, tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Campanha criada com sucesso", response = Campanha.class),
			@ApiResponse(code = 400, message = "Dados de entrada inválidos", response = ApiRespostaErro.class) })
	@RequestMapping(value = "/campanhas",
					produces = { "application/json" }, 
					consumes = { "application/json" },
					method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	Campanha criaCampanha(@ApiParam(value = "Dados para cadastro da Campanha", required=true) ApiPostCampanha dados);

	/**
	 * Endpoint para listagem das campanhas vigentes
	 * 
	 * @return {@link List} of {@link Campanha}
	 */
	@ApiOperation(value = "Lista Campanhas", 
			notes = "Listas as campanhas vigentes", 
			response = Campanha.class, 
			responseContainer = "List", 
			tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "", response = Campanha.class, responseContainer = "List") })
	@RequestMapping(value = {"/campanhas", "/campanhas/vigentes"},
					produces = { "application/json" }, 
					method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	List<Campanha> listaCampanha();


	/**
	 * Endpoint para listagem das campanhas que tiveram a vigência alterada
	 * 
	 * @return {@link List} of {@link Campanha}
	 */
	@ApiOperation(value = "Lista Campanhas com vigência alterada", 
			notes = "Listas as campanhas ativas que tiveram as datas de vigências alteradas", 
			response = Campanha.class, 
			responseContainer = "List", 
			tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, 
					message = "", 
					response = Campanha.class, 
					responseContainer = "List") })
	@RequestMapping(value = "/campanhas/vigencia-alterada",
					produces = { "application/json" }, 
					method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	List<Campanha> listaCampanhaVigenciaAlterada();
	
	
	/**
	 * Endpoint para pesquisa de campanha pelo ID
	 * 
	 * @param idCampanha
	 * @return {@link Campanha}
	 */
	@ApiOperation(value = "Retorna campanha", 
			notes = "Procura campanha pelo id informado", 
			response = Campanha.class, 
			tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "", response = Campanha.class),
			@ApiResponse(code = 400, message = "Id inválido"),
			@ApiResponse(code = 404, message = "Campanha não encontrada") })
	@RequestMapping(value = "/campanhas/{idCampanha}",
			produces = { "application/json" }, 
			method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	Campanha listaUmaCampanha(@ApiParam(value = "Id da campanha pesquisada", required=true) Integer idCampanha);

	/**
	 * Endpoint para remoção de campanha
	 * 
	 * @param idCampanha
	 */
	@ApiOperation(value = "Remove campanha", 
			notes = "Procura campanha pelo id informado", 
			tags={ "campanhas"})
	@ApiResponses(value = { 
			@ApiResponse(code = 202, message = "Campanha removida com sucesso"),
			@ApiResponse(code = 400, message = "Id inválido"),
			@ApiResponse(code = 404, message = "Campanha não encontrada") })
	@RequestMapping(value = "/campanhas/{idCampanha}",
		produces = { "application/json" }, 
		method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	void removeCampanha(@ApiParam(value = "Id da campanha removida", required=true) Integer idCampanha);

}
