package com.taller.culebritaBackend.modelo;

import java.util.Random;

public class Comida {

    private Posicion posicion;
    private final Random random;

    public Comida() {
        random = new Random();
        posicion = new Posicion(5, 14);
    }

    public void reposicionar(Culebra culebra) {
        Posicion nuevaPosicion;
        int intentos = 0;
        do {
            int fila = random.nextInt(Tablero.getTamanio());
            int columna = random.nextInt(Tablero.getTamanio());
            nuevaPosicion = new Posicion(fila, columna);
            intentos++;
        } while (culebra.getCuerpo().contains(nuevaPosicion) && intentos < 400);
        posicion = nuevaPosicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }
}
