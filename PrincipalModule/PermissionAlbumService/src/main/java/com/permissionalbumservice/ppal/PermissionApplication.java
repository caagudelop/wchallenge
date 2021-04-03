package com.permissionalbumservice.ppal;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
public class PermissionApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PermissionApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8082"));
        app.run(args);
	}
	
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return objectMapper;
	}
	
	private static final Logger log = LoggerFactory.getLogger(PermissionApplication.class);
	
	@Bean
	public CommandLineRunner iniciarDatosPermisosAlbum(PermissionAlbumRepo repo) {
		
		return args -> {
			log.info("Insertar registro " + repo.save(new PermissionAlbum(1, 1, "L")));
			log.info("Insertar registro " + repo.save(new PermissionAlbum(2, 1, "E")));
			log.info("Insertar registro " + repo.save(new PermissionAlbum(1, 2, "L")));
			log.info("Insertar registro " + repo.save(new PermissionAlbum(2, 2, "E")));
		};
		
	}

	
}
