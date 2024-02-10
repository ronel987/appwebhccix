package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador,Integer> {
	@Query(value="select * from trabajador where login=?1",nativeQuery=true)
	public abstract Trabajador findByLogin(String login);
	
	@Query(value="select * from trabajador where login=?1 and password=?2 and estado='A'",nativeQuery=true)
	public abstract Trabajador findByLoginyPassword(String login,String password);
	
	@Query(value="select p.idpersona,p.nombre,p.apaterno,p.amaterno,p.tipo_documento,p.num_documento,\"\r\n"
			+ "+ \"p.direccion,p.telefono,p.email,t.sueldo,t.acceso,t.login,t.password,t.estado from persona p inner join Trabajador t \"\r\n"
			+ "+ \"on p.idpersona=t.idpersona where num_documento like '% +numdoc+ %' order by idpersona desc",nativeQuery=true)
	public abstract Object findByNumdocumento(String numdoc);
	
	//lista de trabajadoresID que estan en la tabla reserva
    @Query(value="select t.idpersona as 'trabajadores_id' from reserva re \r\n"
    		+ "inner join trabajador t on t.idpersona=re.idtrabajador  \r\n"	    		
    		+ "order by t.idpersona",nativeQuery=true)	    
    public abstract Collection<Integer> trabajadorID_Reserva();
    
    @Query(value="select p.idpersona,p.nombre,p.apaterno,p.amaterno,t.acceso,t.login,t.password,t.estado from persona p inner join Trabajador t \"\r\n"
    		+ " + \"on p.idpersona=t.idpersona where t.login='\" + login + \"' and t.password='\" + password + \"' and t.estado='A'",nativeQuery=true)
	public abstract Object consultauserypassword(String login,String password);
    
    
}
