package com.sys.myapp.servicio;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.myapp.modelo.Imagen;
import com.sys.myapp.repository.ImagenRepository;

@Service
public class ImagenServiceImpl implements ImagenService {

	@Autowired
    private ImagenRepository imagenrepos; //hereda metodos de JpaRepository
	
	@Override
	@Transactional
	public void insert(Imagen imagen) {
		imagenrepos.save(imagen);		
	}

	@Override
	@Transactional
	public void update(Imagen imagen) {
		imagenrepos.save(imagen);		
	}

	@Override
	@Transactional
	public void delete(Integer imagenId) {
		imagenrepos.deleteById(imagenId);		
	}

	@Override
	@Transactional(readOnly=true)
	public Imagen findById(Integer imagenId) {		
		return imagenrepos.findById(imagenId).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Collection<Imagen> findAll() {
		return imagenrepos.findAll();
	}

}
