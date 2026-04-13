package com.taller.culebritaBackend.estado;

import com.taller.culebritaBackend.servicio.JuegoServicio;

/*
  Estado: El juego espera que el jugador presione Iniciar.
 */
public class EstadoEsperando implements EstadoJuego {

    @Override
    public void iniciar(JuegoServicio contexto) {
        contexto.setEstado(new EstadoJugando());
        contexto.comenzarBucleJuego();
    }

    @Override
    public void pausar(JuegoServicio contexto) {
        // No válido en este estado
    }

    @Override
    public void terminar(JuegoServicio contexto) {
        // No válido en este estado
    }

    @Override
    public void reiniciar(JuegoServicio contexto) {
        // Ya está en estado inicial
    }

    @Override
    public String getNombre() {
        return "ESPERANDO";
    }
}
