package com.sys.myapp.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
    public abstract Set<Habitacion> habitacionesxpiso(String busca);
    
  //lista de Habitaciones disponibles por fecha_ingresa:sí retorna un Set<Habitacion>
    @Query(value="select * from habitacion h join habita_calendario hc on h.idhabitacion=hc.idhabitacion join calendario c on  hc.idcalendario=c.idcalendario where hc.estado='Disponible' and c.fecha=? ",nativeQuery=true)	    
    public abstract Set<Habitacion> habitaDisponiblesxfecha(Date fecha);
    
    
    //si actualizo la reserva:desocupa(GET): debe ser para una fecha_ingresa
    @Query(value="update habita_calendario hc join calendario c on hc.idcalendario=c.idcalendario set estado='DISPONIBLE' where hc.idhabitacion=?1 and c.fecha=?2",nativeQuery=true)	
    public abstract boolean desocupareserva (Integer habitaid, Date fecha);
    
  // con el checkout_al pagar: desocupa fisicamente: debe ser para una fecha_salida
    @Query(value="update habita_calendario set estado='DISPONIBLE' where idhabitacion=?1 and idcalendario=?2",nativeQuery=true)	
    public abstract boolean desocupar (Integer habitaid, Date fecha);
                                                                          
    //con la reserva(ocupa) y con checkin : actualiza la fecha_ingresa: debe ser para una fecha_ingresa:en mysql si funca
    @Modifying
    @Transactional
    @Query(value="update habita_calendario hc join calendario c on hc.idcalendario=c.idcalendario set hc.estado='OCUPADA' where hc.idhabitacion=?1 and c.fecha=?2",nativeQuery=true)	
    public abstract void ocupar (Integer habitaid,String fecha);
}
