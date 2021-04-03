package com.permissionalbumservice.ppal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PermissionAlbumRepo extends CrudRepository<PermissionAlbum, Long>{

	List<PermissionAlbum> findByUserId(Integer userId);
	
	@Query("SELECT p FROM PermisoAlbum p WHERE p.albumId = ?1 AND p.permisos = ?2")
	List<PermissionAlbum> findListByAlbumPermission(Integer userId, String permiso);
	
	@Query("SELECT p FROM PermisoAlbum p WHERE p.albumId = ?1 AND p.userId = ?2")
	PermissionAlbum findByAlbumUser(Integer albumId, Integer userId);
}
