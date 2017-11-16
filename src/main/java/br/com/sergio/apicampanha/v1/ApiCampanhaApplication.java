package br.com.sergio.apicampanha.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:sql.properties" })
public class ApiCampanhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCampanhaApplication.class, args);
	}
}
