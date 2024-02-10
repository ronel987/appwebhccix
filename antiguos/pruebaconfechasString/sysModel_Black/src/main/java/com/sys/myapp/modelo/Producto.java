package com.sys.myapp.modelo;
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
@Table(name="producto")
public class Producto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idproducto;
	@Column (name="nombre", nullable=false,length=40)
	private String nombre;
	@Column (name="descripcion", nullable=false,length=80)
	private String descripcion;
	@Column (name="unidad_medida", nullable=true,length=20)
	private String unidad_medida;
	@Column (name="precio_venta", nullable=false)
	private Double precio_venta;
	
	@OneToMany(mappedBy="producto")   //envia su heredero o referencia,llega como objeto al otro lado q representa la entidad Marca
    private Collection<Consumo> itemsCo=new ArrayList<>();  //un Producto tiene su coleccion de consumos
	
	public Producto() {		
	}

	public Producto(Integer idproducto, String nombre, String descripcion, String unidad_medida, Double precio_venta) {
		this.idproducto = idproducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.unidad_medida = unidad_medida;
		this.precio_venta = precio_venta;
	}

	public Integer getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	public Double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(Double precio_venta) {
		this.precio_venta = precio_venta;
	}

	public Collection<Consumo> getItemsCo() {
		return itemsCo;
	}

	public void setItemsCo(Collection<Consumo> itemsCo) {
		this.itemsCo = itemsCo;
	}
	
	
	
}
