package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.repository.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService {

	@Autowired
    private ReservaRepository rerepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Reserva reserva) {
		rerepos.save(reserva);
	}

	@Override
	@Transactional
	public void update(Reserva reserva) {
		rerepos.save(reserva);		
	}

	@Override
	@Transactional
	public void delete(Integer reservaId) {		
		rerepos.deleteById(reservaId);
	}

	@Override
	@Transactional(readOnly=true)
	public Reserva findById(Integer reservaId) {		
		return rerepos.findById(reservaId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Reserva> findAll() {		
		return rerepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> reservaID_Pago() {
		return rerepos.reservaID_Pago();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> reservaID_Consumo() {
		return rerepos.reservaID_Consumo();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Object> reservasxFecha(String buscar) {
		return rerepos.reservasxFecha(buscar);
	}

	@Override
	@Transactional
	public boolean pagareserva(Integer reservaid) {
		return rerepos.pagareserva(reservaid);
	}

	@Override
	@Transactional (readOnly=true)
	public Integer obtenerultimo() {
		
		return rerepos.obtenerultimo();
	}

}
