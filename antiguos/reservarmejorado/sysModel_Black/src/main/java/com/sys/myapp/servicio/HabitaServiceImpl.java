package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.repository.HabitacionRepository;

@Service
public class HabitaServiceImpl implements HabitacionService {

	@Autowired
    private HabitacionRepository habirepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Habitacion habita) {
		habirepos.save(habita);
	}

	@Override
	@Transactional
	public void update(Habitacion habita) {
		habirepos.save(habita);		
	}

	@Override
	@Transactional
	public void delete(Integer habitaId) {		
		habirepos.deleteById(habitaId);
	}

	@Override
	@Transactional(readOnly=true)
	public Habitacion findById(Integer habitaId) {		
		return habirepos.findById(habitaId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Habitacion> findAll() {		
		return habirepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> habitaID_Reserva() {
		return habirepos.habitaID_Reserva();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> habitaID_Imagenes() {
		return habirepos.habitaID_Imagenes();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Habitacion> habitacionesxpiso(String piso) {
		return habirepos.habitacionesxpiso(piso);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Habitacion> habitaDisponiblesxpiso(String piso) {
		return habirepos.habitaDisponiblesxpiso(piso);
	}

	@Override
	@Transactional
	public boolean desocupar(Integer habitaid) {
		return habirepos.desocupar(habitaid);
	}

	@Override
	@Transactional
	public boolean ocupar(Integer habitaid) {
		return habirepos.ocupar(habitaid);
	}

	

}
