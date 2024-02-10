package com.sys.myapp.modelo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="consumo")
public class Consumo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idconsumo;
	@Column (name="cantidad", nullable=false)
	private Double cantidad;
	@Column (name="precio_venta", nullable=false)
	private Double precio_venta;
	@Column(name="estado", nullable=false,length=15)
    private String estado;
	
	@ManyToOne
	@JoinColumn(name="idproducto",nullable=false)
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="idreserva",nullable=false)
	private Reserva reserva;
	
	public Consumo() {
	}

	public Consumo(Integer idconsumo, Integer idreserva, Integer idproducto, Double cantidad, Double precio_venta,
			String estado) {
		this.idconsumo = idconsumo;
		this.cantidad = cantidad;
		this.precio_venta = precio_venta;
		this.estado = estado;
	}

	public Integer getIdconsumo() {
		return idconsumo;
	}

	public void setIdconsumo(Integer idconsumo) {
		this.idconsumo = idconsumo;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(Double precio_venta) {
		this.precio_venta = precio_venta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	
	
	
	
}
