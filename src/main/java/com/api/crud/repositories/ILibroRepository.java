package com.api.crud.repositories;

import com.api.crud.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByNombrelibroContainingIgnoreCase(String nombrelibro);
}