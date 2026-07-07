package com.api.crud.controllers;

import com.api.crud.models.Libro;
import com.api.crud.repositories.ILibroRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
class LibroController {

    private final ILibroRepository libroRepository;

    LibroController(ILibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String buscar) {
        if (buscar != null && !buscar.isEmpty()) {
            model.addAttribute("libros", libroRepository.findByNombrelibroContainingIgnoreCase(buscar));
        } else {
            model.addAttribute("libros", libroRepository.findAll());
        }
        model.addAttribute("buscar", buscar);
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute Libro libro, BindingResult resultado,
                          RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            return "libros/formulario";
        }
        libroRepository.save(libro);
        redirectAttributes.addFlashAttribute("mensaje", "Libro guardado correctamente ✅");
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