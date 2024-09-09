package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Pago;

public interface PagoService {
	public abstract void insert(Pago pago);
	public abstract void update(Pago pago);
	public abstract void delete(Integer pagoId);
	public abstract Pago findById(Integer pagoId);
	public abstract Collection<Pago> findAll();
	
	public abstract Collection<Pago> PagosxReservaId(Integer reservaid);
}
