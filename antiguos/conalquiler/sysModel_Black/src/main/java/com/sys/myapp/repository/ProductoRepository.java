package com.sys.myapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sys.myapp.modelo.Producto;
import org.springframework.data.jpa.repository.Query;
import antlr.collections.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
	//lista de ProductoID que estan en la tabla consumo
    @Query(value="select p.idproducto as 'productos_id' from consumo co \r\n"
    		+ "inner join producto p on p.idproducto=co.idproducto \r\n"	    		
    		+ "order by p.idproducto",nativeQuery=true)	    
    public abstract Collection<Integer> productoID_Consumo();
}
