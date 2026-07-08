package com.api.crud.controllers;

import com.api.crud.models.Libro;
import com.api.crud.repositories.ILibroRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/libros")
class LibroController {

    private final ILibroRepository libroRepository;
    private final String CARPETA_PORTADAS = "src/main/resources/static/images/portadas/";

    LibroController(ILibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String buscar,
                         @RequestParam(defaultValue = "0") int pagina) {
        int tamanoPagina = 5;
        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(pagina, tamanoPagina);
        org.springframework.data.domain.Page<Libro> paginaLibros;

        if (buscar != null && !buscar.isEmpty()) {
            paginaLibros = libroRepository.findByNombrelibroContainingIgnoreCase(buscar, pageable);
        } else {
            paginaLibros = libroRepository.findAll(pageable);
        }

        model.addAttribute("libros", paginaLibros.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaLibros.getTotalPages());
        model.addAttribute("buscar", buscar);
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute Libro libro,
                          BindingResult resultado,
                          @RequestParam("archivoPortada") MultipartFile archivo,
                          RedirectAttributes redirectAttributes) throws IOException {
        if (resultado.hasErrors()) {
            return "libros/formulario";
        }

        if (!archivo.isEmpty()) {
            String nombreFichero = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path ruta = Paths.get(CARPETA_PORTADAS + nombreFichero);
            Files.createDirectories(ruta.getParent());
            Files.write(ruta, archivo.getBytes());
            libro.setRutaPortada("/images/portadas/" + nombreFichero);
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