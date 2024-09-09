package com.sys.myapp.servicio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Producto;
import com.sys.myapp.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
    private ProductoRepository prorepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Producto producto) {
		prorepos.save(producto);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		prorepos.save(producto);		
	}

	@Override
	@Transactional
	public void delete(Integer productoId) {		
		prorepos.deleteById(productoId);
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findById(Integer productoId) {		
		return prorepos.findById(productoId).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Producto> findAll() {		
		return prorepos.findAll();
	}

	@Override
	@Transactional (readOnly=true)
	public Collection<Integer> productoID_Consumo() {
		return prorepos.productoID_Consumo();
	}

}
