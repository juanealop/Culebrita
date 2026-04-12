package com.taller.culebritaBackend.modelo;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Entidad Cassandra que representa una partida jugada.
 * Guarda cuánto duró y qué tamaño máximo alcanzó la culebra.
 */
@Table("partidas")
public class Partida {

    @PrimaryKey
    private UUID id;

    @Column("fecha")
    private Instant fecha;

    @Column("duracion_segundos")
    private long duracionSegundos;

    @Column("tamano_maximo")
    private int tamanoMaximo;

    public Partida() {}

    public Partida(UUID id, Instant fecha, long duracionSegundos, int tamanoMaximo) {
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
