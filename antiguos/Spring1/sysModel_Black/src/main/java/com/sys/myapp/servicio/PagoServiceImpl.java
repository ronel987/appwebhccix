package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Pago;
import com.sys.myapp.repository.PagoRepository;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
    private PagoRepository parepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Pago pago) {
		parepos.save(pago);
	}

	@Override
	@Transactional
	public void update(Pago pago) {
		parepos.save(pago);		
	}

	@Override
	@Transactional
	public void delete(Integer pagoId) {		
		parepos.deleteById(pagoId);
	}

	@Override
	@Transactional(readOnly=true)
	public Pago findById(Integer pagoId) {		
		return parepos.findById(pagoId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Pago> findAll() {		
		return parepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Pago> PagosxReservaId(Integer reservaid) {
		return parepos.PagosxReservaId(reservaid);
	}

}
