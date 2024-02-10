package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Habitacion;

public interface HabitacionService {
	public abstract void insert(Habitacion habita);
	public abstract void update(Habitacion habita);
	public abstract void delete(Integer habitaId);
	public abstract Habitacion findById(Integer habitaId);
	public abstract Collection<Habitacion> findAll();
	
	public abstract Collection<Integer> habitaID_Reserva();
	public abstract Collection<Integer> habitaID_Imagenes();
	public abstract Collection<Habitacion> habitacionesxpiso(String piso);
	public abstract Collection<Habitacion> habitaDisponiblesxpiso(String piso);
	public abstract boolean desocupar (Integer habitaid);
	public abstract boolean ocupar (Integer habitaid);
	
}
