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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="alquiler")
public class Alquiler implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer idalquiler;	
		
	@Column (name="fecha_ingreso", nullable=false)
	private Date fecha_ingreso;
	@Column (name="fecha_salida", nullable=false)
	private Date fecha_salida;
	@Column (name="costo_alojamiento", nullable=false)
	private Double costo_alojamiento;
	@Column (name="costototal", nullable=false)
	private Double costototal;
	@Column (name="deuda", nullable=false)
	private Double deuda;
	@Column (name="estado", nullable=false,length=10)
	private String estado;
	
	@OneToOne(mappedBy="alquiler")   //envia su heredero o referencia,llega como objeto al otro lado q representa la entidad 
	private Reserva reserva;	
		
	
	@OneToMany(mappedBy="alquiler2")   //envia su heredero o referencia
    private Collection<Pago> itemsPa=new ArrayList<>();  //un Alquiler tiene su coleccion de pagos
	
	@OneToMany(mappedBy="alquiler2")   //envia su heredero o referencia,
    private Collection<Consumo> itemsCo=new ArrayList<>();  //un Alquiler tiene su coleccion de consumos
	
	public Alquiler() {
	}

	public Alquiler(Integer idalquiler, Date fecha_ingreso, Date fecha_salida, Double costo_alojamiento,
			Double costototal, Double deuda, String estado) {
		this.idalquiler = idalquiler;
		this.fecha_ingreso = fecha_ingreso;
		this.fecha_salida = fecha_salida;
		this.costo_alojamiento = costo_alojamiento;
		this.costototal = costototal;
		this.deuda = deuda;
		this.estado = estado;
	}


	public Integer getIdalquiler() {
		return idalquiler;
	}

	public void setIdalquiler(Integer idalquiler) {
		this.idalquiler = idalquiler;
	}

	public Date getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
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

	public Double getCostototal() {
		return costototal;
	}

	public void setCostototal(Double costototal) {
		this.costototal = costototal;
	}

	public Double getDeuda() {
		return deuda;
	}

	public void setDeuda(Double deuda) {
		this.deuda = deuda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
   
	
	
	
	
}
