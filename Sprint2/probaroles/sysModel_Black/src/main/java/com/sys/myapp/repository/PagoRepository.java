package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sys.myapp.modelo.Pago;

public interface PagoRepository extends JpaRepository<Pago,Integer> {
	@Query(value="select * from pago where idreserva=\"+ reservaid + \" order by idpago desc",nativeQuery=true)
	public abstract Collection<Pago> PagosxReservaId(Integer reservaid);
	
}
