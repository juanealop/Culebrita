package com.taller.culebritaBackend.controlador;

import com.taller.culebritaBackend.dto.PartidaDTO;
import com.taller.culebritaBackend.servicio.PartidaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST (capa Controller en MVC del backend).
 * Expone el historial de partidas para que el frontend lo consulte.
 */
@RestController
@RequestMapping("/api/partidas")
@CrossOrigin(origins = "*")
public class PartidaControlador {

    private final PartidaServicio partidaServicio;

    public PartidaControlador(PartidaServicio partidaServicio) {
        this.partidaServicio = partidaServicio;
    }

    @GetMapping
    public ResponseEntity<List<PartidaDTO>> obtenerHistorial() {
        return ResponseEntity.ok(partidaServicio.listar());
    }
}
