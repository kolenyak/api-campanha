/**
 * 
 */
package br.com.sergio.apicampanha.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuração do Swagger para documentação da API
 * 
 * @author Sérgio
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.sergio.apicampanha.v1"))
				.paths(PathSelectors.any())
				.build()
				.tags(
						new Tag("campanhas", "Operações sobre as Campanhas"),
						new Tag("times", "Informações sobre os Times")
				)
				.apiInfo(apiInfo());
	}
	
	/**
	 * @return Informações sobre a API
	 */
	private ApiInfo apiInfo() {
	    Contact contact = new Contact("Sérgio", null, "sergiokole@yahoo.com.br");
		ApiInfo apiInfo = new ApiInfoBuilder().contact(contact).build();
	    return apiInfo;
	}
	
}
