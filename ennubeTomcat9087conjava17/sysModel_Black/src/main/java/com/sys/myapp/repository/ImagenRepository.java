package com.sys.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sys.myapp.modelo.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen,Integer> {

}
