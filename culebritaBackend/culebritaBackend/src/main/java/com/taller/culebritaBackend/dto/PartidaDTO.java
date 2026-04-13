package com.taller.culebritaBackend.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/*
 DTO de respuesta para el historial de partidas. Solo expone los campos que necesita el frontend.
 */
@Getter
@Setter
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
}
