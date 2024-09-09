package com.sys.myapp.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

//Ojo:el id representa solo un item de la Boleta q podria tener varios items
//el name recibe objetos(habita y calen) desde el mappedBy (los objetos hacen el lazo)
//esta tabla tendra propiedades propias
@Entity
@Table(name="habita_calendario")
@AssociationOverrides({                                //en name recibe un obj de la clase habita
	@AssociationOverride(name="id.habitacion",           //vincula la entidad Habita...recibe del mappedBy
			joinColumns=@JoinColumn(name="idhabitacion",nullable=false,
			foreignKey=@ForeignKey(foreignKeyDefinition="foreign key(idhabitacion) references habitacion(idhabitacion)"))),	
	@AssociationOverride(name="id.calendario",             //vincula la entidad Calen...recibe del mappedBy
			joinColumns=@JoinColumn(name="idcalendario",nullable=false,
			foreignKey=@ForeignKey(foreignKeyDefinition="foreign key(idcalendario) references calendario(idcalendario)")))	
})
public class HabitaCalendario implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HabitaCalendarioKey id=new HabitaCalendarioKey();
	
	@Column
	private String estado;
	
	public HabitaCalendario() {
	}
	//para q no se repita el id:	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HabitaCalendario other = (HabitaCalendario) obj;
		return Objects.equals(id, other.id);
	}
   //getters and setters:
	public HabitaCalendarioKey getId() {
		return id;
	}

	public void setId(HabitaCalendarioKey id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	//probablemente los necesite:
	public Habitacion getHabitacion() {
		return id.getHabitacion();
	}

	public void setHabitacion(Habitacion habita) {
		this.id.setHabitacion(habita); 
	}

	public Calendario getCalendario() {
		return id.getCalendario();
	}

	public void setCalendario(Calendario calen) {
		this.id.setCalendario(calen); 
	}
	
	
}
