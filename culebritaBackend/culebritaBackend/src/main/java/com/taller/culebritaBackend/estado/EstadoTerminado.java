package com.taller.culebritaBackend.estado;

import com.taller.culebritaBackend.servicio.JuegoServicio;

/**
 * Estado: La culebra colisionó, el juego terminó.
 */
public class EstadoTerminado implements EstadoJuego {

    @Override
    public void iniciar(JuegoServicio contexto) {
        // No válido en este estado
    }

    @Override
    public void pausar(JuegoServicio contexto) {
        // No válido en este estado
    }

    @Override
    public void terminar(JuegoServicio contexto) {
        // Ya está terminado
    }

    @Override
    public void reiniciar(JuegoServicio contexto) {
        contexto.setEstado(new EstadoEsperando());
        contexto.reiniciarJuego();
    }

    @Override
    public String getNombre() {
        return "TERMINADO";
    }
}
