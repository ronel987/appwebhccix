package com.sys.myapp.modelo;

import java.io.Serializable;
import java.util.Base64;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="imagenes")
public class Imagen implements Serializable {   	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer imagenId;
	@Column(name="nombre", nullable=false)
	private String nombre;
	@Column(columnDefinition="longblob",name="file",nullable=false)
	private byte[] file;
		
	@ManyToOne
	@JoinColumn(name="idhabitacion",nullable=false)
	private Habitacion habitacion;
	
	public Imagen() {
	}

	public Imagen(Integer imagenId, String nombre, byte[] file) {
		this.imagenId = imagenId;
		this.nombre = nombre;
		this.file = file;		
	}

	public Integer getImagenId() {
		return imagenId;
	}

	public void setImagenId(Integer imagenId) {
		this.imagenId = imagenId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
   
	
	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	//convertir bytes a String base64
	public String getBase64() {
		return Base64.getEncoder().encodeToString(this.file);
	}
	
	
}
