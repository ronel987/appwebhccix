package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sys.myapp.modelo.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
	
	
	@Query(value="select * from cliente where codigo_cliente=?1",nativeQuery=true)
	public abstract Cliente findByCodigo(String codigo);
	
	//lista de clientesID que estan en la tabla reserva
    @Query(value="select c.idpersona as 'clientes_id' from reserva re \r\n"
    		+ "inner join cliente c on c.idpersona=re.idcliente \r\n"	    		
    		+ "order by c.idpersona",nativeQuery=true)	    
    public abstract Collection<Integer> clienteID_Reserva();
    
  //lista de datos de 2 tablas pa el detalle de cliente:devuelve 1 fila
    @Query(value="select p.idpersona,p.nombre,p.apaterno,p.amaterno,p.tipo_documento,p.num_documento,\"\r\n"
    		+ "  + \"p.direccion,p.telefono,p.email,c.codigo_cliente from persona p inner join cliente c \"\r\n"
    		+ "                + \"on p.idpersona=c.idpersona where num_documento like '%\"\r\n"
    		+ "                + numdoc + \"%' order by idpersona desc     ",nativeQuery=true)	    
    public abstract Object findByNumeroDocumento(String numdoc);
}
