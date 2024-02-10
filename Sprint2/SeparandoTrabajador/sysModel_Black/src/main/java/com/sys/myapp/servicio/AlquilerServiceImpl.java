package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Alquiler;
import com.sys.myapp.repository.AlquilerRepository;


@Service
public class AlquilerServiceImpl implements AlquilerService {

	@Autowired
    private AlquilerRepository alquilerepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Alquiler alquiler) {
		alquilerepos.save(alquiler);
	}

	@Override
	@Transactional
	public void update(Alquiler reserva) {
		alquilerepos.save(reserva);		
	}


	@Override
	@Transactional(readOnly=true)
	public Alquiler findById(Integer alquilerId) {		
		return alquilerepos.findById(alquilerId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Alquiler> findAll() {		
		return alquilerepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> alquilerID_Pago() {
		return alquilerepos.alquilerID_Pago();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> alquilerID_Consumo() {
		return alquilerepos.alquilerID_Consumo();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Object> alquileresxFecha(String buscar) {
		return alquilerepos.alquileresxFecha(buscar);
	}

	@Override
	@Transactional
	public boolean pagaralquiler(Double pago, Integer alquilerid) {
		return alquilerepos.pagarparcial(pago,alquilerid);
	}

	@Override
	@Transactional (readOnly=true)
	public Integer obtenerultimo() {		
		return alquilerepos.obtenerultimo();
	}

	@Override
	@Transactional (readOnly=true)
	public Integer consultaridalquilercliente(Integer idcliente) {
		
		return alquilerepos.consultaridalquilercliente(idcliente);
	}

	

}
