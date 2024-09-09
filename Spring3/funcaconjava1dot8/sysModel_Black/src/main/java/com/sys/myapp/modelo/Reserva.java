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
//con java.sql.Date no inserta
//con java.util.Date si inserta en BD:logra convertir de String(cajas html) a Date
@Entity
@Table(name="reserva")
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idreserva;	
		
	@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	@Column (name="fecha_reserva", nullable=false)
	private LocalDate fecha_reserva;
	
	@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)	
	@Column (name="fecha_ingresa", nullable=false)
	private LocalDate fecha_ingresa;    
	
	@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	@Column (name="fecha_salida", nullable=false)
	private LocalDate fecha_salida;
	
	@Column (name="costo_alojamiento", nullable=false)
	private Double costo_alojamiento;
	@Column (name="estado", nullable=false,length=9)
	private String estado;
	
	
	//@JoinColumn(name="idreserva",nullable=false,unique=true) 	  //le digo:fk
	@OneToOne(mappedBy="reserva") 
	private Alquiler alquiler;
		
	@ManyToOne
	@JoinColumn(name="idhabitacion",nullable=false)
	private Habitacion habitacion;
	
	@ManyToOne
	@JoinColumn(name="idcliente",nullable=false)
	private Cliente cliente;
	
	//@ManyToOne
	//@JoinColumn(name="idtrabajador",nullable=false)
	@Column (name="idtrabajador", nullable=true)
	private Integer idtrabajador;
		
	public Reserva() {
	}

	public Reserva(Integer idreserva, LocalDate fecha_reserva, LocalDate fecha_ingresa, LocalDate fecha_salida, Double costo_alojamiento,
			String estado) {
		this.idreserva = idreserva;				
		this.fecha_reserva = fecha_reserva;
		this.fecha_ingresa = fecha_ingresa;
		this.fecha_salida = fecha_salida;
		this.costo_alojamiento = costo_alojamiento;
		this.estado = estado;
	}
	
	public Integer getIdreserva() {
		return idreserva;
	}

	public void setIdreserva(Integer idreserva) {
		this.idreserva = idreserva;
	}

	public LocalDate getFecha_reserva() {
		return fecha_reserva;
	}

	public void setFecha_reserva(LocalDate fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}

	public LocalDate getFecha_ingresa() {
		return fecha_ingresa;
	}

	public void setFecha_ingresa(LocalDate fecha_ingresa) {
		this.fecha_ingresa = fecha_ingresa;
	}

	public LocalDate getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(LocalDate fecha_salida) {
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
	
	
	public Integer getIdtrabajador() {
		return idtrabajador;
	}

	public void setIdtrabajador(Integer idtrabajador) {
		this.idtrabajador = idtrabajador;
	}

	@PrePersist
	public void asignarfechareserva() {
		//fecha_reserva=Date.valueOf(LocalDate  );
	}
	
	
	
	
}
