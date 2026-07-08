package com.api.crud.repositories;

import com.api.crud.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {

    Page<Libro> findByNombrelibroContainingIgnoreCase(String nombrelibro, Pageable pageable);
}