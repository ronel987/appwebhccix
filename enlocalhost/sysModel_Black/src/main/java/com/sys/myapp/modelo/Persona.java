package com.sys.myapp.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "persona")
public class Persona implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idpersona;
	@Column(name="nombre", nullable=false,length=20)
    private String nombre;
	@Column(name="apaterno", nullable=false,length=20)
    private String apaterno;
    @Column(name="amaterno", nullable=false,length=20)
    private String amaterno;
    @Column(name="tipo_documento", nullable=false,length=15)
    private String tipo_documento;
    @Column(name="num_documento", nullable=false,length=12)
    private String num_documento;
    @Column(name="direccion", nullable=true,length=80)
    private String direccion;
    @Column(name="telefono", nullable=true,length=12)
    private String telefono;
    @Column(name="email", nullable=true,length=25)
    private String email;
    
    @OneToOne
	@JoinColumn(name="idpersona",nullable=false,unique=true) 	  //le digo:fk
	private Trabajador trabajador;  //llega la referencia
    
    @OneToOne
	@JoinColumn(name="idpersona",nullable=false,unique=true) 	  //le digo:fk
	private Cliente cliente;  //llega la referencia
    
    public Persona() {
	}

	public Persona(Integer idpersona, String nombre, String apaterno, String amaterno, String tipo_documento,
			String num_documento, String direccion, String telefono, String email) {
		this.idpersona = idpersona;
		this.nombre = nombre;
		this.apaterno = apaterno;
		this.amaterno = amaterno;
		this.tipo_documento = tipo_documento;
		this.num_documento = num_documento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}
	
	public String getNombreCompleto() {
        return String.format("%s %s %s", nombre, apaterno, amaterno).trim();
    }

	public Integer getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(Integer idpersona) {
		this.idpersona = idpersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApaterno() {
		return apaterno;
	}

	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}

	public String getAmaterno() {
		return amaterno;
	}

	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}

	public String getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

	public String getNum_documento() {
		return num_documento;
	}

	public void setNum_documento(String num_documento) {
		this.num_documento = num_documento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
    
}
