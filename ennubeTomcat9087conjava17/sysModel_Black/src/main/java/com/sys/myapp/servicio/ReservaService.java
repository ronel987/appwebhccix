package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Reserva;


public interface ReservaService {
	public abstract void insert(Reserva reserva);
	public abstract void update(Reserva reserva);
	public abstract void delete(Integer reservaId);
	public abstract Reserva findById(Integer reservaId);
	public abstract Collection<Reserva> findAll();
	
	public abstract Collection<Integer> reservaID_Pago();
	public abstract Collection<Integer> reservaID_Consumo();
	public abstract Collection<Object> reservasxFecha(String buscar);
	public abstract boolean pagareserva (Integer reservaid);
	public abstract Integer obtenerultimo();
	public abstract Collection<Reserva> findMisreservas(Integer idcliente);
	public abstract void cambiarestadoalalquilar (Integer reservaid);
	
}
