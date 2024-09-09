package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sys.myapp.modelo.Alquiler;
import com.sys.myapp.modelo.Reserva;

public interface AlquilerRepository extends JpaRepository<Alquiler,Integer> {
	//lista de alquilerID que estan en la tabla consumo
    @Query(value="select a.idalquiler as 'alquiler_id' from consumo c \r\n"
    		+ "inner join alquiler a on a.idalquiler =c.idalquiler \r\n"	    		
    		+ "order by a.idalquiler",nativeQuery=true)	    
    public abstract Collection<Integer> alquilerID_Consumo();
    
  //lista de alquilerID que estan en la tabla pago
    @Query(value="select a.idalquiler as 'alquiler_id' from pago p \r\n"
    		+ "inner join alquiler a on a.idalquiler= p.idalquiler \r\n"	    		
    		+ "order by a.idalquiler",nativeQuery=true)	    
    public abstract Collection<Integer> alquilerID_Pago();
    
  //lista de Alquileres por una fecha y subconsultas
    @Query(value="select r.idreserva,r.idhabitacion,h.numero,(select nombre from persona where idpersona=r.idcliente)as clienten,\"+\r\n"
    		+ "\"(select apaterno from persona where idpersona=r.idcliente)as clienteap,\"+\r\n"
    		+ "\"r.idtrabajador,(select nombre from persona where idpersona=r.idtrabajador) as trabajadorn,\"+\r\n"
    		+ "\"(select apaterno from persona where idpersona=r.idtrabajador) as trabajadorap,\"+\r\n"
    		+ "\"r.tipo_reserva,r.fecha_reserva,r.fecha_ingresa,r.fecha_salida,r.costo_alojamiento,r.pagototal from"
    		+ " reserva r inner join habitacion h on r.idhabitacion=h.idhabitacion where r.fecha_reserva like '%\"+ buscar + \"%' order by idreserva desc",nativeQuery=true)	    
    public abstract Collection<Object> alquileresxFecha(String buscar);
    
    //obtener ultimo id
    @Query(value="select idalquiler from alquiler order by idalquiler desc limit 1",nativeQuery=true)
	public abstract Integer obtenerultimo();
    
    //pago parcial alquiler
    @Query(value="update pago set pagoparcial=?1 where idalquiler=?2",nativeQuery=true)	
    public abstract boolean pagarparcial (Double pago,Integer idalquiler);

  //obtener el idalquiler previo a un consumo, dado el num_doc de un cliente
    @Query(value="select idalquiler from alquiler a inner join reserva r on a.idreserva=r.idreserva where r.idcliente=? and a.estado='Vigente' order by idalquiler desc limit 1",nativeQuery=true)
	public abstract Integer consultaridalquilercliente(Integer idcliente);
   
    // incrementar costototal con un consumo
    @Modifying
    @Query(value="update alquiler set costototal=costototal+?1 where idalquiler=?2",nativeQuery=true)	
    public abstract void subircostotxconsumo (Double preciototal,Integer idalquiler);
 
    // incrementar deuda con un consumo
    @Modifying
    @Query(value="update alquiler set deuda=deuda+?1 where idalquiler=?2",nativeQuery=true)	
    public abstract void subirdeudaconsumo (Double costo,Integer idalquiler);
    
    
 // incrementar deudas con un alojamiento
    @Modifying
    @Query(value="update alquiler set (deuda=deuda+?1 and deudaaloja=?1 ) where idalquiler=?2",nativeQuery=true)	
    public abstract void subirdeudaaloja (Double costoaloja,Integer idalquiler);
    
    //bajar deuda aloja al pagarla
    @Modifying
    @Query(value="update alquiler set deudaaloja=deudaaloja-?1 where idalquiler=?2",nativeQuery=true)	
    public abstract void bajardeudaaloja (Double pago,Integer idalquiler); 
    
    // disminuir deuda total con un pago de consumo o aloja
    @Modifying
    @Query(value="update alquiler set deuda=deuda-?1 where idalquiler=?2",nativeQuery=true)	
    public abstract void bajardeuda (Double montopagado,Integer idalquiler);  
    
 //obtener lista de num de docs de clientes con idalquiler Vigente
    @Query(value="select DISTINCT num_documento from persona p inner join cliente c on p.idpersona=c.idpersona inner join reserva r on c.idpersona=r.idcliente inner join alquiler a on r.idreserva=a.idreserva where a.estado='Vigente' order by p.num_documento",nativeQuery=true)
    public abstract Collection<String> obtenerlistanumdocs();
    
    
}
