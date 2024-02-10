package com.sys.myapp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name= "habitacion")
public class Habitacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idhabitacion;
	@Column(name="numero", nullable=false,length=4,unique=true)
    private String numero;
	@Column(name="piso", nullable=false,length=2)
    private String piso;
	@Column(name="descripcion", nullable=true,length=80)
    private String descripcion;
	@Column(name="caracteristicas", nullable=true,length=80)
    private String caracteristicas;
	@Column(name="precio_diario", nullable=false)
    private double precio_diario;
    @Column(name="estado", nullable=false,length=15)
    private String estado;
    @Column(name="tipo_habitacion", nullable=false,length=20)
    private String tipo_habitacion;
    
    @OneToMany(mappedBy="habitacion")   //envia su referencia
    private Collection<Reserva> itemsRe=new ArrayList<>();  //una Habita tiene su coleccion de reservas
    
    @OneToMany(mappedBy="habitacion")   //envia su referencia
    private Collection<Imagen> itemsIma=new ArrayList<>();  //una Habita tiene su coleccion de imagenes
    
    public Habitacion() {
	}

	public Habitacion(int idhabitacion, String numero, String piso, String descripcion, String caracteristicas,
			double precio_diario, String estado, String tipo_habitacion) {
		this.idhabitacion = idhabitacion;
		this.numero = numero;
		this.piso = piso;
		this.descripcion = descripcion;
		this.caracteristicas = caracteristicas;
		this.precio_diario = precio_diario;
		this.estado = estado;
		this.tipo_habitacion = tipo_habitacion;
	}

	public int getIdhabitacion() {
		return idhabitacion;
	}

	public void setIdhabitacion(int idhabitacion) {
		this.idhabitacion = idhabitacion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public double getPrecio_diario() {
		return precio_diario;
	}

	public void setPrecio_diario(double precio_diario) {
		this.precio_diario = precio_diario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo_habitacion() {
		return tipo_habitacion;
	}

	public void setTipo_habitacion(String tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
	}

	public Collection<Reserva> getItemsRe() {
		return itemsRe;
	}

	public void setItemsRe(Collection<Reserva> itemsRe) {
		this.itemsRe = itemsRe;
	}

	public Collection<Imagen> getItemsIma() {
		return itemsIma;
	}

	public void setItemsIma(Collection<Imagen> itemsIma) {
		this.itemsIma = itemsIma;
	}
    
    

}
