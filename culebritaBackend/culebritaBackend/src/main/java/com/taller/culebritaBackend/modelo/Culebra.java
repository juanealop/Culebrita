package com.taller.culebritaBackend.modelo;

import java.util.LinkedList;

public class Culebra {

    private LinkedList<Posicion> cuerpo;
    private Direccion direccion;

    public Culebra() {
        cuerpo = new LinkedList<>();
        // Inicia en el centro del tablero apuntando a la derecha
        cuerpo.add(new Posicion(10, 10));
        cuerpo.add(new Posicion(10, 9));
        cuerpo.add(new Posicion(10, 8));
        direccion = Direccion.DERECHA;
    }

    public LinkedList<Posicion> getCuerpo() {
        return cuerpo;
    }

    public Posicion getCabeza() {
        return cuerpo.getFirst();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion nuevaDireccion) {
        // Evita que la culebra se revierta sobre sí misma
        if (nuevaDireccion == Direccion.ARRIBA && direccion == Direccion.ABAJO) return;
        if (nuevaDireccion == Direccion.ABAJO && direccion == Direccion.ARRIBA) return;
        if (nuevaDireccion == Direccion.IZQUIERDA && direccion == Direccion.DERECHA) return;
        if (nuevaDireccion == Direccion.DERECHA && direccion == Direccion.IZQUIERDA) return;
        this.direccion = nuevaDireccion;
    }

    public Posicion calcularSiguientePosicion() {
        Posicion cabeza = getCabeza();
        return switch (direccion) {
            case ARRIBA    -> new Posicion(cabeza.getFila() - 1, cabeza.getColumna());
            case ABAJO     -> new Posicion(cabeza.getFila() + 1, cabeza.getColumna());
            case IZQUIERDA -> new Posicion(cabeza.getFila(), cabeza.getColumna() - 1);
            case DERECHA   -> new Posicion(cabeza.getFila(), cabeza.getColumna() + 1);
        };
    }

    public void mover(Posicion nuevaPosicion) {
        cuerpo.addFirst(nuevaPosicion);
        cuerpo.removeLast();
    }

    public void crecer(Posicion nuevaPosicion) {
        cuerpo.addFirst(nuevaPosicion);
    }

    public boolean colisionaConCuerpo(Posicion posicion) {
        // Excluimos la cola porque se va a mover en el mismo tick
        return cuerpo.subList(0, cuerpo.size() - 1).contains(posicion);
    }
}
