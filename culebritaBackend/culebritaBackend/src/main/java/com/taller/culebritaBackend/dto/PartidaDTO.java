package com.taller.culebritaBackend.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO de respuesta para el historial de partidas.
 * Solo expone los campos que necesita el frontend.
 */
public class PartidaDTO {

    private UUID id;
    private Instant fecha;
    private long duracionSegundos;
    private int tamanoMaximo;

    public PartidaDTO() {}

    public PartidaDTO(UUID id, Instant fecha, long duracionSegundos, int tamanoMaximo) {
        this.id = id;
        this.fecha = fecha;
        this.duracionSegundos = duracionSegundos;
        this.tamanoMaximo = tamanoMaximo;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }

    public long getDuracionSegundos() { return duracionSegundos; }
    public void setDuracionSegundos(long duracionSegundos) { this.duracionSegundos = duracionSegundos; }

    public int getTamanoMaximo() { return tamanoMaximo; }
    public void setTamanoMaximo(int tamanoMaximo) { this.tamanoMaximo = tamanoMaximo; }
}
