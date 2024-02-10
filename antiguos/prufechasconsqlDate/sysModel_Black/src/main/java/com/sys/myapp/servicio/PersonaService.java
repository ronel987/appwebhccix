package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Persona;

public interface PersonaService {
	public abstract void insert(Persona persona);
	public abstract void update(Persona persona);
	public abstract void delete(Integer personaId);
	public abstract Persona findById(Integer personaId);
	public abstract Persona findByDocum(String numdoc);
	public abstract Collection<Persona> findAll();
	public abstract Integer obtenerultimo();
}
