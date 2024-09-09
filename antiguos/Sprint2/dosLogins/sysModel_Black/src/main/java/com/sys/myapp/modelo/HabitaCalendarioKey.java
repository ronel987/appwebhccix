package com.sys.myapp.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class HabitaCalendarioKey implements Serializable{
	//los objetos de esta clase es la PK q combina habita_id y calendario_id
		private static final long serialVersionUID = 1L;
		//la tabla intermedia tiene el lado muchos siempre
		@ManyToOne
	    private Habitacion habitacion;
	    @ManyToOne
	    private Calendario calendario; 
	
	
	public HabitaCalendarioKey() {
	}


	public HabitaCalendarioKey(Habitacion habitacion, Calendario calendario) {
		this.habitacion = habitacion;
		this.calendario = calendario;
	}


	public Habitacion getHabitacion() {
		return habitacion;
	}


	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}


	public Calendario getCalendario() {
		return calendario;
	}


	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}


	@Override
	public int hashCode() {
		return Objects.hash(habitacion,calendario);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HabitaCalendarioKey other = (HabitaCalendarioKey) obj;
		return Objects.equals(habitacion, other.habitacion) && Objects.equals(calendario, other.calendario);
	}
	
	//para evitar duplicados de la combinacion habita-calen
	
	
}
