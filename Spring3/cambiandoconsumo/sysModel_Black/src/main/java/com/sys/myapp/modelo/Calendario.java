package com.sys.myapp.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name="calendario")
public class Calendario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer idcalendario;
	
	 @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)	 
     private LocalDate fecha;
	 
     
     @OneToMany(mappedBy="id.calendario")         //envio el lazo:la referencia:un obj calendario q debe aparecer en HabitaCalendarioKey
  	 private Set<HabitaCalendario> itemsAuSub=new HashSet<>(); //cada calen puede tener varios obj HabitaCalendario
     
     public Calendario() {
		}

	public Calendario(Integer idcalendario, LocalDate fecha) {
		this.idcalendario = idcalendario;
		this.fecha = fecha;
		
	}

	public Integer getIdcalendario() {
		return idcalendario;
	}

	public void setIdcalendario(Integer idcalendario) {
		this.idcalendario = idcalendario;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Set<HabitaCalendario> getItemsAuSub() {
		return itemsAuSub;
	}

	public void setItemsAuSub(Set<HabitaCalendario> itemsAuSub) {
		this.itemsAuSub = itemsAuSub;
	}
    
	
	

	

	
	
     
     
     
	
}
