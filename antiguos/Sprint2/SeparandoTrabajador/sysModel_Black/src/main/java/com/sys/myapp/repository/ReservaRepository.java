package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sys.myapp.modelo.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Integer> {
	//lista de ReservaID que estan en la tabla consumo
    @Query(value="select r.idreserva as 'reservas_id' from consumo c \r\n"
    		+ "inner join reserva r on r.idreserva =c.idreserva \r\n"	    		
    		+ "order by r.idreserva",nativeQuery=true)	    
    public abstract Collection<Integer> reservaID_Consumo();
    
  //lista de ReservaID que estan en la tabla pago
    @Query(value="select r.idreserva as 'reservas_id' from pago p \r\n"
    		+ "inner join reserva r on r.idreserva= p.idreserva \r\n"	    		
    		+ "order by r.idreserva",nativeQuery=true)	    
    public abstract Collection<Integer> reservaID_Pago();
    
  //lista de Reservas por una fecha y subconsultas
    @Query(value="select r.idreserva,r.idhabitacion,h.numero,(select nombre from persona where idpersona=r.idcliente)as clienten,\"+\r\n"
    		+ "\"(select apaterno from persona where idpersona=r.idcliente)as clienteap,\"+\r\n"
    		+ "\"r.idtrabajador,(select nombre from persona where idpersona=r.idtrabajador) as trabajadorn,\"+\r\n"
    		+ "\"(select apaterno from persona where idpersona=r.idtrabajador) as trabajadorap,\"+\r\n"
    		+ "\"r.tipo_reserva,r.fecha_reserva,r.fecha_ingresa,r.fecha_salida,r.costo_alojamiento,r.pagototal from"
    		+ " reserva r inner join habitacion h on r.idhabitacion=h.idhabitacion where r.fecha_reserva like '%\"+ buscar + \"%' order by idreserva desc",nativeQuery=true)	    
    public abstract Collection<Object> reservasxFecha(String buscar);
    
    //obtener ultimo id
    @Query(value="select idreserva from reserva order by idreserva desc limit 1",nativeQuery=true)
	public abstract Integer obtenerultimo();
    
    //pagar reserva
    @Query(value="update reserva set pagototal='Pagada' where idreserva=?",nativeQuery=true)	
    public abstract boolean pagareserva (Integer reservaid);

    //coleccion de reservas por cliente
    @Query(value="select * from reserva r where idcliente=? order by r.idreserva",nativeQuery=true)	
	public abstract Collection<Reserva> findMisreservas(Integer idcliente);
    
}
