package com.sys.myapp.servicio;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
    public abstract void subircostotxconsumo (Double preciototal,Integer idalquiler);  
    public abstract void subirdeudaconsumo (Double costo,Integer idalquiler);
    public abstract void subirdeudaaloja (Double costoaloja,Integer idalquiler);    
    public abstract void bajardeudaaloja (Double pago,Integer idalquiler); 
    public abstract void bajardeuda (Double montopagado,Integer idalquiler);
	public abstract Collection<String> obtenerlistanumdocs(); 
    
	
}
