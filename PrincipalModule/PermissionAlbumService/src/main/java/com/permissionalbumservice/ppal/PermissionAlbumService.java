package com.permissionalbumservice.ppal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/permissionService/*")
public class PermissionAlbumService {

	@Autowired
	PermissionAlbumRepo permissionAlbumRepo;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@RequestMapping(value = "agregarPermisoAlbum", method = RequestMethod.GET)
	public String agregarPermisoAlbum() {

		PermissionAlbum p = new PermissionAlbum();
		p.setAlbumId(1);
		p.setUserId(1);
		p.setPermisos("L,E");
		
		permissionAlbumRepo.save(p);
		
		return "insertado con exito";
	}
	
	@RequestMapping(value = "guardar", method = RequestMethod.POST)
	public String guardar(@RequestBody PermissionAlbum permisoAlbum) {

		if(permisoAlbum == null)
			return "No hay datos que guardar";
		
		if(permisoAlbum.getAlbumId() == null){
			return "Indique album al cual desea agregar el permiso";
		}
		
		if(permisoAlbum.getUserId() == null) {
			return "Indique el usuario al cual desea agregar permisos";
		}
		
		if(permisoAlbum.getPermisos() == null || permisoAlbum.getPermisos().trim().length() == 0) {
			return "Escriba los permisos que desea asignar";
		}
		
		PermissionAlbum p = permissionAlbumRepo.findByAlbumUser(permisoAlbum.getAlbumId(), permisoAlbum.getUserId());
		
		if(p == null) {
			permissionAlbumRepo.save(permisoAlbum);
		}	else {
			
			p.setPermisos(permisoAlbum.getPermisos());
			permissionAlbumRepo.save(p);
		}
		
		return "guardado con exito";
	}
	
	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public String findAll() {

		List<PermissionAlbum> listaAlbum = (List<PermissionAlbum>) permissionAlbumRepo.findAll();
		
		String output;
		try {
			output = objectMapper.writeValueAsString(listaAlbum);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Error procesando datos";
		}
		
		return output;
	}
	
	@RequestMapping(value = "/permisosAlbum", method = RequestMethod.POST)
	public String permisosAlbum(@RequestParam("id") Integer albumId, @RequestParam("permiso") String permiso) {

		List<PermissionAlbum> listaAlbum = (List<PermissionAlbum>) permissionAlbumRepo.findListByAlbumPermission(albumId, permiso);
		
		String output;
		try {
			output = objectMapper.writeValueAsString(listaAlbum);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Error procesando datos";
		}
		
		return output;
	}
	
}
