package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
    private PersonaRepository perrepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Persona persona) {
		perrepos.save(persona);
	}

	@Override
	@Transactional
	public void update(Persona persona) {
		perrepos.save(persona);		
	}

	@Override
	@Transactional
	public void delete(Integer personaId) {		
		perrepos.deleteById(personaId);
	}

	@Override
	@Transactional(readOnly=true)
	public Persona findById(Integer personaId) {		
		return perrepos.findById(personaId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Persona> findAll() {		
		return perrepos.findAll();
	}
	@Override
	@Transactional (readOnly=true)
	public Integer obtenerultimo() {
		return perrepos.obtenerultimo();
	}

}
