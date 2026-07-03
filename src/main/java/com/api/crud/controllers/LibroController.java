package com.api.crud.controllers;

import com.api.crud.models.Libro;
import com.api.crud.repositories.ILibroRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/libros")
class LibroController {

    private final ILibroRepository libroRepository;

    LibroController(ILibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "libros/lista";

    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute Libro libro) {
        libroRepository.save(libro);
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("libro", libroRepository.findById(id).orElseThrow());
        return "libros/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        libroRepository.deleteById(id);
        return "redirect:/libros";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        model.addAttribute("libro", libroRepository.findById(id).orElseThrow());
        return "libros/ver";
    }
}



