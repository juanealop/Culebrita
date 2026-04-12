package com.taller.culebritaBackend.dto;

import java.util.List;

public class EstadoTableroDTO {

    private int[][] tablero;
    private List<int[]> culebra;
    private int[] comida;
    private int puntuacion;
    private String estadoJuego;

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public List<int[]> getCulebra() {
        return culebra;
    }

    public void setCulebra(List<int[]> culebra) {
        this.culebra = culebra;
    }

    public int[] getComida() {
        return comida;
    }

    public void setComida(int[] comida) {
        this.comida = comida;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(String estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
}
