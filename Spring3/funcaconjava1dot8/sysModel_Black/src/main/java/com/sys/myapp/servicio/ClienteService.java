package com.sys.myapp.servicio;

import java.util.Collection;
import java.util.Optional;

import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Trabajador;



public interface ClienteService {
	//definiendo los servicios para el controlador
	public abstract void insert(Cliente cliente);
	public abstract void update(Cliente cliente);
	public abstract void delete(Integer clienteId);
	public abstract Cliente findById(Integer clienteId);
	public Optional<Cliente> findById2(Integer clienteId);
	public abstract Collection<Cliente> findAll();
	public Optional<Cliente> findByNumeroDoc(String doc);
	public abstract Optional <Cliente> findByLoginyPassword(String login,String password);
	public abstract Collection<Integer> clienteID_Reserva();
	public abstract Object findByNumeroDocumento(String numdoc);
	
}
