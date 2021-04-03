package com.permissionalbumservice.ppal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "PermisoAlbum")
public class PermissionAlbum {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer albumId;
	private Integer userId;
	private String permisos;
	
	@Override
	public String toString() {
		return "[albumId=" + this.albumId + ", userId=" + userId + ", permisos=" + permisos + "]"; 
	}
	
	public PermissionAlbum(Integer albumId, Integer userId, String permisos) {
		this.albumId = albumId;
		this.userId = userId;
		this.permisos = permisos;
	}
	
	public PermissionAlbum() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPermisos() {
		return permisos;
	}
	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}
	
	
}
