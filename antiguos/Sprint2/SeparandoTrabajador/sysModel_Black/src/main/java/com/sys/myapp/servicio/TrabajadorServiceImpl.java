package com.sys.myapp.servicio;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.repository.TrabajadorRepository;

@Service
public class TrabajadorServiceImpl implements TrabajadorService {

	@Autowired
    private TrabajadorRepository trarepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Trabajador traba) {
		trarepos.save(traba);
	}

	@Override
	@Transactional
	public void update(Trabajador traba) {
		trarepos.save(traba);		
	}

	@Override
	@Transactional
	public void delete(Integer trabaId) {		
		trarepos.deleteById(trabaId);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional <Trabajador> findById(Integer trabaId) {		
		return trarepos.findById(trabaId);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Trabajador> findAll() {		
		return trarepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Optional <Trabajador> findByLogin(String login) {
		return trarepos.findByLogin(login);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> trabajadorID_Reserva() {
		return trarepos.trabajadorID_Reserva();
	}

	@Override
	@Transactional (readOnly=true)
	public Object findByNumdocumento(String numdoc) {
		return trarepos.findByNumdocumento(numdoc);
	}

	@Override
	@Transactional (readOnly=true)
	public Object consultauserypassword(String login, String password) {
		return trarepos.consultauserypassword(login, password);
	}

	@Override
	@Transactional (readOnly=true)
	public Optional <Trabajador> findByLoginyPassword(String login, String password) {		
		return trarepos.findByLoginyPassword(login, password);
	}

}
