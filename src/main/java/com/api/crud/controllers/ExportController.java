package com.api.crud.controllers;

import com.api.crud.models.Libro;
import com.api.crud.repositories.ILibroRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/exportar")
public class ExportController {

    @Autowired
    private ILibroRepository libroRepository;

    @GetMapping("/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=libros.pdf");

        List<Libro> libros = libroRepository.findAll();

        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();

        Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font fuenteEncabezado = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 10);

        documento.add(new Paragraph("Biblioteca Guardia Civil", fuenteTitulo));
        documento.add(new Paragraph("Lista de Libros", fuenteNormal));
        documento.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1, 3, 3, 2});

        String[] encabezados = {"ID", "Nombre", "Autor", "Titulo"};
        for (String enc : encabezados) {
            PdfPCell celda = new PdfPCell(new Phrase(enc, fuenteEncabezado));
            celda.setBackgroundColor(new BaseColor(26, 92, 26));
            celda.setPadding(8);
            tabla.addCell(celda);
        }

        for (Libro libro : libros) {
            tabla.addCell(new Phrase(String.valueOf(libro.getId()), fuenteNormal));
            tabla.addCell(new Phrase(libro.getNombrelibro(), fuenteNormal));
            tabla.addCell(new Phrase(libro.getAutor() != null ? libro.getAutor() : "", fuenteNormal));
            tabla.addCell(new Phrase(libro.getTitulo() != null ? libro.getTitulo() : "", fuenteNormal));
        }

        documento.add(tabla);
        documento.close();
    }

    @GetMapping("/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=libros.xlsx");

        List<Libro> libros = libroRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Libros");

        CellStyle estiloEncabezado = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font fuente = workbook.createFont();
        fuente.setBold(true);
        estiloEncabezado.setFont(fuente);
        estiloEncabezado.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        estiloEncabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row encabezado = hoja.createRow(0);
        String[] columnas = {"ID", "Nombre", "Autor", "Titulo", "Contenido", "Caracteristicas"};
        for (int i = 0; i < columnas.length; i++) {
            Cell celda = encabezado.createCell(i);
            celda.setCellValue(columnas[i]);
            celda.setCellStyle(estiloEncabezado);
        }

        int fila = 1;
        for (Libro libro : libros) {
            Row row = hoja.createRow(fila++);
            row.createCell(0).setCellValue(libro.getId());
            row.createCell(1).setCellValue(libro.getNombrelibro());
            row.createCell(2).setCellValue(libro.getAutor() != null ? libro.getAutor() : "");
            row.createCell(3).setCellValue(libro.getTitulo() != null ? libro.getTitulo() : "");
            row.createCell(4).setCellValue(libro.getContenido() != null ? libro.getContenido() : "");
            row.createCell(5).setCellValue(libro.getCaracteristicas() != null ? libro.getCaracteristicas() : "");
        }

        for (int i = 0; i < columnas.length; i++) {
            hoja.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}