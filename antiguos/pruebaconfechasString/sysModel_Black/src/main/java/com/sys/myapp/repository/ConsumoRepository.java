package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sys.myapp.modelo.Consumo;

public interface ConsumoRepository extends JpaRepository<Consumo,Integer> {

	//lista de consumos y productos de una reserva especifica:
    @Query(value="select c.idconsumo,c.idreserva,c.idproducto,p.nombre,c.cantidad,c.precio_venta \"\r\n"
    		+ "  + \",c.estado from consumo c inner join producto p on c.idproducto=p.idproducto\"\r\n"
    		+ " + \" where c.idreserva =\"+ reservaId + \" order by c.idconsumo desc",nativeQuery=true)	    
    public abstract Collection<Object> consumosxIdReserva(Integer reservaId);
	
}
