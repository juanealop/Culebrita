package com.taller.culebritaBackend.modelo;

public class Tablero {

    private static final int TAMANIO = 20;
    private int[][] matriz;

    public Tablero() {
        this.matriz = new int[TAMANIO][TAMANIO];
        inicializar();
    }

    public void inicializar() {
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public static int getTamanio() {
        return TAMANIO;
    }

    public void setCelda(int fila, int columna, int valor) {
        if (fila >= 0 && fila < TAMANIO && columna >= 0 && columna < TAMANIO) {
            matriz[fila][columna] = valor;
        }
    }

    public int getCelda(int fila, int columna) {
        return matriz[fila][columna];
    }

    public void reiniciar() {
        inicializar();
    }
}
