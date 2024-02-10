package com.sys.myapp.modelo;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name= "pago")
public class Pago implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpago;	
	@Column(name="tipo_comprobante", nullable=false,length=20)
    private String tipo_comprobante;
	@Column(name="num_comprobante", nullable=false,length=20)
    private String num_comprobante;
	@Column(name="igv", nullable=false)
    private Double igv;
	@Column(name="total_pago", nullable=false)
    private Double total_pago;
	@Column(name="fecha_emision", nullable=false)
    private Date fecha_emision;
	@Column(name="fecha_pago", nullable=false)
    private Date fecha_pago;
	
	@ManyToOne
	@JoinColumn(name="idreserva",nullable=false)
	private Reserva reserva;
	
	public Pago() {		
	}

	public Pago(int idpago, Integer idreserva, String tipo_comprobante, String num_comprobante, Double igv,
			Double total_pago, Date fecha_emision, Date fecha_pago) {
		this.idpago = idpago;		
		this.tipo_comprobante = tipo_comprobante;
		this.num_comprobante = num_comprobante;
		this.igv = igv;
		this.total_pago = total_pago;
		this.fecha_emision = fecha_emision;
		this.fecha_pago = fecha_pago;
	}

	public int getIdpago() {
		return idpago;
	}

	public void setIdpago(int idpago) {
		this.idpago = idpago;
	}

	public String getTipo_comprobante() {
		return tipo_comprobante;
	}

	public void setTipo_comprobante(String tipo_comprobante) {
		this.tipo_comprobante = tipo_comprobante;
	}

	public String getNum_comprobante() {
		return num_comprobante;
	}

	public void setNum_comprobante(String num_comprobante) {
		this.num_comprobante = num_comprobante;
	}

	public Double getIgv() {
		return igv;
	}

	public void setIgv(Double igv) {
		this.igv = igv;
	}

	public Double getTotal_pago() {
		return total_pago;
	}

	public void setTotal_pago(Double total_pago) {
		this.total_pago = total_pago;
	}

	public Date getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(Date fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public Date getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(Date fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	
	
	
	
	
	
	
	
	
	
}
