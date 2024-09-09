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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name="trabajador")
public class Trabajador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private Integer idpersona;
	@Column (name="sueldo", nullable=false)
	private Double sueldo;
	@Column (name="acceso", nullable=false,length=15)
	private String acceso;
	@Column (name="login", nullable=false,length=15,unique=true)
	private String login;
	@Column (name="password", nullable=false,length=20)
	private String password;
	@Column (name="estado", nullable=false,length=1)
	private String estado;
	
	@OneToOne(mappedBy="trabajador")   //envia su heredero o referencia,llega como objeto al otro lado q representa la entidad 
	private Persona persona;
	
	@OneToMany(mappedBy="trabajador")   //envia su heredero o referencia
    private Collection<Reserva> itemsRe=new ArrayList<>();  //un trabajador tiene su coleccion de reservas
	
	public Trabajador() {
	}	

	public Trabajador(Integer idpersona, Double sueldo, String acceso, String login, String password, String estado) {
		this.idpersona = idpersona;
		this.sueldo = sueldo;
		this.acceso = acceso;
		this.login = login;
		this.password = password;
		this.estado = estado;
	}

	public Integer getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(Integer idpersona) {
		this.idpersona = idpersona;
	}

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Collection<Reserva> getItemsRe() {
		return itemsRe;
	}

	public void setItemsRe(Collection<Reserva> itemsRe) {
		this.itemsRe = itemsRe;
	}
	
	
	
	
}
