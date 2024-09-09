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
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name= "pago")
public class Pago implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpago;	
	@Column(name="tipodepago", nullable=false,length=20)
    private String tipodepago;
	
	@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	@Column(name="fecha_pago", nullable=false)
    private LocalDate fecha_pago;
	
	@Column(name="montopagado", nullable=false)
    private Double montopagado;	
	
	@Column(name="idconsumo", nullable=true)
	private Integer idconsumo;
	
	@ManyToOne
	@JoinColumn(name="idalquiler",nullable=false)
	private Alquiler alquiler2;
	
	public Pago() {		
	}

	public Pago(int idpago, String tipodepago, LocalDate fecha_pago, Double montopagado) {
		this.idpago = idpago;
		this.tipodepago = tipodepago;
		this.fecha_pago = fecha_pago;
		this.montopagado = montopagado;
	}

	public int getIdpago() {
		return idpago;
	}

	public void setIdpago(int idpago) {
		this.idpago = idpago;
	}

	public String getTipodepago() {
		return tipodepago;
	}

	public void setTipodepago(String tipodepago) {
		this.tipodepago = tipodepago;
	}

	public LocalDate getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(LocalDate fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public Double getMontopagado() {
		return montopagado;
	}

	public void setMontopagado(Double montopagado) {
		this.montopagado = montopagado;
	}

	public Integer getIdconsumo() {
		return idconsumo;
	}

	public void setIdconsumo(Integer idconsumo) {
		this.idconsumo = idconsumo;
	}

	public Alquiler getAlquiler2() {
		return alquiler2;
	}

	public void setAlquiler2(Alquiler alquiler2) {
		this.alquiler2 = alquiler2;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
