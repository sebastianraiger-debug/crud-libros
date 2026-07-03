package com.api.crud.service;

import com.api.crud.models.Libro;
import com.api.crud.repositories.ILibroRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LibroService{

    private ILibroRepository libroRepository = null;


    public LibroService(ILibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> list() {
        return libroRepository.findAll();
    }

    public Libro guardar(Libro libro){
        return libroRepository.save(libro);
    }

    public <Libro> Libro buscarPorId(Long id) {
        return (Libro) libroRepository.findById(id).orElseThrow();
    }
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }
}
