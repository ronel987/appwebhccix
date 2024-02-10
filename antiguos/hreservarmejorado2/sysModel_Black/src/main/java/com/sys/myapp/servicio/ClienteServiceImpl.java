package com.sys.myapp.servicio;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
    private ClienteRepository clienterepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Cliente cliente) {
		clienterepos.save(cliente);
	}

	@Override
	@Transactional
	public void update(Cliente cliente) {
		clienterepos.save(cliente);		
	}

	@Override
	@Transactional
	public void delete(Integer clienteId) {		
		clienterepos.deleteById(clienteId);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findById(Integer clienteId) {		
		return clienterepos.findById(clienteId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Cliente> findAll() {		
		return clienterepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Cliente findByCodigo(String codigo) {		
		return clienterepos.findByCodigo(codigo);  //metodo propio
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> clienteID_Reserva() {
		return clienterepos.clienteID_Reserva();
	}

	@Override
	@Transactional (readOnly=true)
	public Object findByNumeroDocumento(String numdoc) {
		return clienterepos.findByNumeroDocumento(numdoc);
	}

	@Override
	@Transactional (readOnly=true)
	public Optional<Cliente> findByNumeroDoc(String doc) {
		return clienterepos.findByNumeroDoc(doc);
	}

	

}
