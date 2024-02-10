package com.sys.myapp.modelo;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
		
	@Column (name="tipo_reserva", nullable=false,length=20)
	private String tipo_reserva;
	
	@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	@Column (name="fecha_reserva", nullable=false)
	private LocalDate fecha_reserva;
	
	@Column (name="fecha_ingresa", nullable=false)
	private Date fecha_ingresa;
	@Column (name="fecha_salida", nullable=false)
	private Date fecha_salida;
	@Column (name="costo_alojamiento", nullable=false)
	private Double costo_alojamiento;
	@Column (name="pagototal", nullable=false,length=15)
	private String pagototal;
	
	@ManyToOne
	@JoinColumn(name="idhabitacion",nullable=false)
	private Habitacion habitacion;
	
	@ManyToOne
	@JoinColumn(name="idcliente",nullable=false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idtrabajador",nullable=false)
	private Trabajador trabajador;
	
	@OneToMany(mappedBy="reserva")   //envia su heredero o referencia
    private Collection<Pago> itemsPa=new ArrayList<>();  //una Reserva tiene su coleccion de pagos
	
	@OneToMany(mappedBy="reserva")   //envia su heredero o referencia,
    private Collection<Consumo> itemsCo=new ArrayList<>();  //una Reserva tiene su coleccion de consumos
	
	public Reserva() {
	}

	public Reserva(Integer idreserva, String tipo_reserva, LocalDate fecha_reserva, Date fecha_ingresa, Date fecha_salida, Double costo_alojamiento,
			String pagototal) {
		this.idreserva = idreserva;		
		this.tipo_reserva = tipo_reserva;
		this.fecha_reserva = fecha_reserva;
		this.fecha_ingresa = fecha_ingresa;
		this.fecha_salida = fecha_salida;
		this.costo_alojamiento = costo_alojamiento;
		this.pagototal = pagototal;
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

	public String getTipo_reserva() {
		return tipo_reserva;
	}

	public void setTipo_reserva(String tipo_reserva) {
		this.tipo_reserva = tipo_reserva;
	}

	public LocalDate getFecha_reserva() {
		return fecha_reserva;
	}

	public void setFecha_reserva(LocalDate fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}

	public Date getFecha_ingresa() {
		return fecha_ingresa;
	}

	public void setFecha_ingresa(Date fecha_ingresa) {
		this.fecha_ingresa = fecha_ingresa;
	}

	public Date getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(Date fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public Double getCosto_alojamiento() {
		return costo_alojamiento;
	}

	public void setCosto_alojamiento(Double costo_alojamiento) {
		this.costo_alojamiento = costo_alojamiento;
	}

	public String getPagototal() {
		return pagototal;
	}

	public void setPagototal(String estado) {
		this.pagototal = estado;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Collection<Pago> getItemsPa() {
		return itemsPa;
	}

	public void setItemsPa(Collection<Pago> itemsPa) {
		this.itemsPa = itemsPa;
	}

	public Collection<Consumo> getItemsCo() {
		return itemsCo;
	}

	public void setItemsCo(Collection<Consumo> itemsCo) {
		this.itemsCo = itemsCo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/*
	@PrePersist
	public void asignarfechareserva() {
		fecha_reserva=LocalDate.now();
	}
	*/
	
	
	
}
