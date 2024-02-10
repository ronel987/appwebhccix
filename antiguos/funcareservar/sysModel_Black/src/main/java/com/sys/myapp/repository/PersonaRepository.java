package com.sys.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sys.myapp.modelo.Persona;

public interface PersonaRepository extends JpaRepository<Persona,Integer> {
	@Query(value="select idpersona from persona order by idpersona desc limit 1",nativeQuery=true)
	public abstract Integer obtenerultimo();
}
