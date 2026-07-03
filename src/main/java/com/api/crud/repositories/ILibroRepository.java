package com.api.crud.repositories;

import com.api.crud.models.Libro;
import com.api.crud.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILibroRepository extends JpaRepository <Libro,Long> {


    void deleteAllById(long id);
}
