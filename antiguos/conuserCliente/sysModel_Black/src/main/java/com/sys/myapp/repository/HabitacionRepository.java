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
    
  //lista de Habitaciones disponibles por piso y fecha:
    @Query(value="select * from habitacion h inner join reserva r on h.idhabitacion=r.idhabitacion"
    		+ "inner join habita_calendario hc on h.idhabitacion=hc.idhabitacion"
    		+ "inner join calendario c on  hc.idcalendario=c.idcalendario "
    		+ "where piso=?1 and hc.estado='Disponible' and c.fecha=?2 order by idhabitacion",nativeQuery=true)	    
    public abstract Collection<Habitacion> habitaDisponiblesxpiso(String piso,Integer calendarioid);
    
    
    //con el checkout_al pagar: desocupar: debe ser para una fecha
    @Query(value="update habita_calendario set estado='Disponible' where idhabitacion=?1 and idcalendario=?2",nativeQuery=true)	
    public abstract boolean desocupar (Integer habitaid, Integer calendarioid);
    
    //con el checkin: ocupar: debe ser para una fecha
    @Query(value="update habita_calendario set estado='Ocupado' where idhabitacion=?1 and idcalendario=?2",nativeQuery=true)	
    public abstract boolean ocupar (Integer habitaid,Integer calendarioid);
}
