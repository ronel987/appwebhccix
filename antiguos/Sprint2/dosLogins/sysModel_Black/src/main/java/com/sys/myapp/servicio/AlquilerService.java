package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Alquiler;



public interface AlquilerService {
	public abstract void insert(Alquiler alquiler);
	public abstract void update(Alquiler alquiler);
	
	public abstract Alquiler findById(Integer alquilerId);
	public abstract Collection<Alquiler> findAll();
	
	public abstract Collection<Integer> alquilerID_Pago();
	public abstract Collection<Integer> alquilerID_Consumo();
	public abstract Collection<Object> alquileresxFecha(String buscar);
	public abstract boolean pagaralquiler (Double pago,Integer alquilerid);
	public abstract Integer obtenerultimo();
	public abstract Integer consultaridalquilercliente(Integer idcliente);
	
	
}
