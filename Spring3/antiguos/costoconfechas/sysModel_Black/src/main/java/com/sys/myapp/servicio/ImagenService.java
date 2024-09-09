package com.sys.myapp.servicio;

import java.util.Collection;
import com.sys.myapp.modelo.Imagen;



public interface ImagenService {
	//definiendo los servicios para el controlador
	public abstract void insert(Imagen imagen);
	public abstract void update(Imagen imagen);
	public abstract void delete(Integer imagenId);
	public abstract Imagen findById(Integer imagenId);
	public abstract Collection<Imagen> findAll();
}
