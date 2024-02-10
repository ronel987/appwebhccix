package com.sys.myapp.servicio;

import java.util.Collection;

import com.sys.myapp.modelo.Cliente;

public interface ClienteService {
	//definiendo los servicios para el controlador
	public abstract void insert(Cliente cliente);
	public abstract void update(Cliente cliente);
	public abstract void delete(Integer clienteId);
	public abstract Cliente findById(Integer clienteId);
	public abstract Collection<Cliente> findAll();
	
	public abstract Cliente findByCodigo(String codigo);
	public abstract Collection<Integer> clienteID_Reserva();
	public abstract Object findByNumeroDocumento(String numdoc);
	
}
