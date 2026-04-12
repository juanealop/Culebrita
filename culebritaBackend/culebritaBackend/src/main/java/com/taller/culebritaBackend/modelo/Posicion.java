package com.taller.culebritaBackend.modelo;

import java.util.Objects;

public class Posicion {

    private int fila;
    private int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Posicion other)) return false;
        return this.fila == other.fila && this.columna == other.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }
}
