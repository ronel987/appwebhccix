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
@Table(name="cliente")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer idpersona;	
	
	@Column (name="login", nullable=true)
	private String login;
	@Column (name="password", nullable=true)
	private String password;
	@Column (name="ruc", nullable=true,length=11,unique=true)
	private String ruc;
	@Column (name="razonsocial", nullable=true,length=60,unique=true)
	private String razonsocial;
	
	@OneToOne(mappedBy="cliente")   //envia su heredero o referencia,llega como objeto al otro lado q representa la entidad 
	private Persona persona;
	
	@OneToMany(mappedBy="cliente")   //envia su heredero o referencia
    private Collection<Reserva> itemsRe=new ArrayList<>();  //un Cliente tiene su coleccion de reservas
	
	public Cliente() {
	}	

	

	public Cliente(Integer idpersona, String login,String password, String ruc, String razonsocial) {
		this.idpersona = idpersona;
		this.login = login;
		this.password=password;
		this.ruc = ruc;
		this.razonsocial = razonsocial;
	}
	
	public Integer getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(Integer idpersona) {
		this.idpersona = idpersona;
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

	public String getRuc() {
		return ruc;
	}


	public void setRuc(String ruc) {
		this.ruc = ruc;
	}


	public String getRazonsocial() {
		return razonsocial;
	}


	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
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
