package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sys.myapp.modelo.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion,Integer> {
	//lista de HabitacionesID que estan en la tabla reserva
    @Query(value="select a.idhabitacion as 'habitaciones_id' from reserva re\r\n"
    		+ "inner join habitacion a on a.idhabitacion=re.idhabitacion \r\n"	    		
    		+ "order by a.idhabitacion",nativeQuery=true)	    
    public abstract Collection<Integer> habitaID_Reserva();
  //lista de HabitacionesID que estan en la tabla imagenes
    @Query(value="select a.idhabitacion as 'habitaciones_id' from imagenes im\r\n"
    		+ "inner join habitacion a on a.idhabitacion=im.idhabitacion \r\n"	    		
    		+ "order by a.idhabitacion",nativeQuery=true)	    
    public abstract Collection<Integer> habitaID_Imagenes();
  //lista de Habitaciones por piso:
    @Query(value="select * from habitacion where piso like '%\"+ busca + \"%' order by idhabitacion",nativeQuery=true)	    
    public abstract Collection<Habitacion> habitacionesxpiso(String busca);
  //lista de Habitaciones disponibles por piso:
    @Query(value="select * from habitacion where piso=? and estado='Disponible' order by idhabitacion",nativeQuery=true)	    
    public abstract Collection<Habitacion> habitaDisponiblesxpiso(String piso);
    //desocupar
    @Query(value="update habitacion set estado='Disponible' where idhabitacion=?",nativeQuery=true)	
    public abstract boolean desocupar (Integer habitaid);
    
    //ocupar
    @Query(value="update habitacion set estado='Ocupado' where idhabitacion=?",nativeQuery=true)	
    public abstract boolean ocupar (Integer habitaid);
}
