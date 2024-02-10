package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Consumo;
import com.sys.myapp.repository.ConsumoRepository;

@Service
public class ConsumoServiceImpl implements ConsumoService {

	@Autowired
    private ConsumoRepository conrepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Consumo consumo) {
		conrepos.save(consumo);
	}

	@Override
	@Transactional
	public void update(Consumo consumo) {
		conrepos.save(consumo);		
	}

	@Override
	@Transactional
	public void delete(Integer consumoId) {		
		conrepos.deleteById(consumoId);
	}

	@Override
	@Transactional(readOnly=true)
	public Consumo findById(Integer consumoId) {		
		return conrepos.findById(consumoId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Consumo> findAll() {		
		return conrepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Object> consumosxIdReserva(Integer reservaId) {
		return conrepos.consumosxIdReserva(reservaId);
	}

	

}
