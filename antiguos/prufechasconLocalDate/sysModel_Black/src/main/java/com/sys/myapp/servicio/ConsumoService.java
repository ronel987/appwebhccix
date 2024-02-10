package com.sys.myapp.servicio;

import java.util.Collection;
import com.sys.myapp.modelo.Consumo;

public interface ConsumoService {
	//definiendo los servicios para el controlador
	public abstract void insert(Consumo consumo);
	public abstract void update(Consumo consumo);
	public abstract void delete(Integer consumoId);
	public abstract Consumo findById(Integer consumoId);
	public abstract Collection<Consumo> findAll();
	public abstract Collection<Object> consumosxIdAlquiler(Integer reservaId);
	
}
