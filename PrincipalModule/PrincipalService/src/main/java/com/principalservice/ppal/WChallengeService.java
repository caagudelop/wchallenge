package com.principalservice.ppal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.principalservice.dto.Album;
import com.principalservice.dto.Fotos;
import com.principalservice.util.StringUtil;

@RestController
@RequestMapping("/WChallenge/*")
public class WChallengeService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private String _URL = "https://jsonplaceholder.typicode.com/";
	
	/*
	 * PUNTO 2
	 */
	@RequestMapping(value = "/obtenerFotos", method = RequestMethod.GET)
	public String obtenerFotos() {
		HttpHeaders headers = Constantes.getHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		return restTemplate.exchange(_URL + "photos", HttpMethod.GET, entity, String.class).getBody();
		
	}
	
	/*
	 * PUNTO 1
	 */
	@RequestMapping(value = "/obtenerUsuarios", method = RequestMethod.GET)
	public String obtenerUsuarios() {
		HttpHeaders headers = Constantes.getHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		return restTemplate.exchange(_URL + "users", HttpMethod.GET, entity, String.class).getBody();
		
	}
	
	/*
	 * PUNTO 3
	 */
	@RequestMapping(value = "/obtenerAlbumes", method = RequestMethod.GET)
	public String obtenerAlbumes() {
		HttpHeaders headers = Constantes.getHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		return restTemplate.exchange(_URL + "albums", HttpMethod.GET, entity, String.class).getBody();
	}
	
	/*
	 * PUNTO PLUS
	 */
	
	@RequestMapping(value = "/obtenerFotosPorUsuario/{id}", method = RequestMethod.GET)
	public String obtenerFotosPorUsuario(@PathVariable("id") String id){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<Album[]> listAlbum = restTemplate.exchange(_URL + "albums?userId=" +id, HttpMethod.GET, entity, Album[].class);
		
		Album[] listaAlbum = listAlbum.getBody();
		
		List<Fotos> listFotosUsuario = new ArrayList<Fotos>();
		
		for(Album a : listaAlbum) {
			ResponseEntity<Fotos[]> fotos = restTemplate.exchange(_URL + "albums/"+a.getId()+"/photos", HttpMethod.GET, entity, Fotos[].class);
			listFotosUsuario.addAll(Arrays.asList(fotos.getBody()));
		}
		
		String output;
		try {
			output = objectMapper.writeValueAsString(listFotosUsuario);
		} catch (JsonProcessingException e) {
//			e.printStackTrace();
			return "Error procesando la respuesta!";
		}
		
		return output;
	}
	
	/*
	 * BUSQUEDA POR NAME O USUARIO EN LOS COMENTARIOS 
	 */
	
	@RequestMapping(value = "/obtenerComentarios", method = RequestMethod.GET)
	public String obtenerComentarios(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email) {
		HttpHeaders headers = Constantes.getHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String filter = "";
		
		if(name != null && name.trim().length() > 0) {
			filter = StringUtil.addParameter(filter, "name=" + name);
		}
		
//		if(userId != null) {
//			User[] u = restTemplate.exchange(_URL + "users?id=" + userId, HttpMethod.GET, entity, User[].class).getBody();
//			if(u.length > 0) {
//				filter = StringUtil.addParameter(filter, "email=" + u[0].getEmail());
//			}
//		}
		
		if(email != null && email.trim().length() > 0) {
			filter = StringUtil.addParameter(filter, "email=" + email);
		}
		
		return restTemplate.exchange(_URL + "comments" + filter, HttpMethod.GET, entity, String.class).getBody();
	}
	
	@RequestMapping(value = "/obtenerUsuario/{id}", method = RequestMethod.GET)
	public String obtenerUsuario(@PathVariable("id") String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		return restTemplate.exchange(_URL + "users/" +id, HttpMethod.GET, entity, String.class).getBody();
	}
	
}
