package com.taller.culebritaBackend.estado;

import com.taller.culebritaBackend.servicio.JuegoServicio;

/**
 * Estado: El juego está en curso, la culebra se mueve.
 */
public class EstadoJugando implements EstadoJuego {

    @Override
    public void iniciar(JuegoServicio contexto) {
        // Ya está jugando
    }

    @Override
    public void pausar(JuegoServicio contexto) {
        contexto.setEstado(new EstadoPausado());
        contexto.detenerBucleJuego();
    }

    @Override
    public void terminar(JuegoServicio contexto) {
        contexto.setEstado(new EstadoTerminado());
        contexto.detenerBucleJuego();
    }

    @Override
    public void reiniciar(JuegoServicio contexto) {
        contexto.setEstado(new EstadoEsperando());
        contexto.reiniciarJuego();
    }

    @Override
    public String getNombre() {
        return "JUGANDO";
    }
}
