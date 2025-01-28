package com.essia.desafio_essia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition( servers= { @Server( url="/", description = "Servidor padrão root") } )
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class DesafioEssiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioEssiaApplication.class, args);
	}

}
