package com.sys.myapp.servicio;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import com.sys.myapp.modelo.Habitacion;

public interface HabitacionService {
	public abstract void insert(Habitacion habita);
	public abstract void update(Habitacion habita);
	public abstract void delete(Integer habitaId);
	public abstract Habitacion findById(Integer habitaId);
	public abstract Collection<Habitacion> findAll();
	
	public abstract Collection<Integer> habitaID_Reserva();
	public abstract Collection<Integer> habitaID_Imagenes();
	public abstract Set<Habitacion> habitacionesxpiso(String piso);
	public abstract Set<Habitacion> habitaDisponiblesxfecha(Date fecha);
	public abstract boolean desocupar (Integer habitaid,Date fecha);
	public abstract void ocupar (Integer habitaid, Date fecha);
	public abstract boolean desocupareserva (Integer habitaid,Date fecha);
}
