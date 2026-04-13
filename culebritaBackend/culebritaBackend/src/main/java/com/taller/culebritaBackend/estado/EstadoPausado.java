package com.taller.culebritaBackend.estado;

import com.taller.culebritaBackend.servicio.JuegoServicio;

/*
  Estado: El juego está pausado, el bucle está detenido.
 */
public class EstadoPausado implements EstadoJuego {

    @Override
    public void iniciar(JuegoServicio contexto) {
        contexto.setEstado(new EstadoJugando());
        contexto.comenzarBucleJuego();
    }

    @Override
    public void pausar(JuegoServicio contexto) {
        // Ya está pausado
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
        return "PAUSADO";
    }
}
