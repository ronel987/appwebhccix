package com.sys.myapp.modelo;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="reserva")
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idreserva;	
		
	//@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	@Column (name="fecha_reserva", nullable=false)
	private String fecha_reserva;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)	
	@Column (name="fecha_ingresa", nullable=false)
	private String fecha_ingresa;    //con Date no inserta reserva porq no puede convertir de String a Date
	
	//@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	@Column (name="fecha_salida", nullable=false)
	private String fecha_salida;
	
	@Column (name="costo_alojamiento", nullable=false)
	private Double costo_alojamiento;
	@Column (name="estado", nullable=false,length=9)
	private String estado;
	
	@OneToOne
	@JoinColumn(name="idreserva",nullable=false,unique=true) 	  //le digo:fk
	private Alquiler alquiler;  //llega la referencia
		
	@ManyToOne
	@JoinColumn(name="idhabitacion",nullable=false)
	private Habitacion habitacion;
	
	@ManyToOne
	@JoinColumn(name="idcliente",nullable=false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idtrabajador",nullable=false)
	private Trabajador trabajador;
		
	public Reserva() {
	}

	public Reserva(Integer idreserva, String fecha_reserva, String fecha_ingresa, String fecha_salida, Double costo_alojamiento,
			String estado) {
		this.idreserva = idreserva;				
		this.fecha_reserva = fecha_reserva;
		this.fecha_ingresa = fecha_ingresa;
		this.fecha_salida = fecha_salida;
		this.costo_alojamiento = costo_alojamiento;
		this.estado = estado;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Integer getIdreserva() {
		return idreserva;
	}

	public void setIdreserva(Integer idreserva) {
		this.idreserva = idreserva;
	}

	public String getFecha_reserva() {
		return fecha_reserva;
	}

	public void setFecha_reserva(String fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}

	public String getFecha_ingresa() {
		return fecha_ingresa;
	}

	public void setFecha_ingresa(String fecha_ingresa) {
		this.fecha_ingresa = fecha_ingresa;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public Double getCosto_alojamiento() {
		return costo_alojamiento;
	}

	public void setCosto_alojamiento(Double costo_alojamiento) {
		this.costo_alojamiento = costo_alojamiento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Alquiler getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}
	
	
	@PrePersist
	public void asignarfechareserva() {
		//fecha_reserva=Date.valueOf(LocalDate  );
	}
	
	
	
	
}
