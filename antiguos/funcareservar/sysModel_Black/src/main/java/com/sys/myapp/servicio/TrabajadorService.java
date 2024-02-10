package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Trabajador;

public interface TrabajadorService {
	public abstract void insert(Trabajador trabajador);
	public abstract void update(Trabajador trabajador);
	public abstract void delete(Integer trabajadorId);
	public abstract Trabajador findById(Integer trabajadorId);
	public abstract Collection<Trabajador> findAll();
	
	public abstract Trabajador findByLogin(String login);
	public abstract Trabajador findByLoginyPassword(String login,String password);
	public abstract Object findByNumdocumento(String numdoc);
	public abstract Collection<Integer> trabajadorID_Reserva();
	public abstract Object consultauserypassword(String login,String password);
}
