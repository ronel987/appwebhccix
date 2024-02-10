package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Producto;

public interface ProductoService {
	public abstract void insert(Producto producto);
	public abstract void update(Producto producto);
	public abstract void delete(Integer productoId);
	public abstract Producto findById(Integer productoId);
	public abstract Collection<Producto> findAll();
	public abstract Collection<Integer> productoID_Consumo();
	
}
