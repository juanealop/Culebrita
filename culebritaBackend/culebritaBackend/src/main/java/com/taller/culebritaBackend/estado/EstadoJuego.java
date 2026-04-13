package com.taller.culebritaBackend.estado;

import com.taller.culebritaBackend.servicio.JuegoServicio;

/*
  Interfaz del patrón de diseño State.
  Define los eventos posibles del juego. Cada estado concreto
  implementa el comportamiento adecuado para cada evento.
 */
public interface EstadoJuego {

    void iniciar(JuegoServicio contexto);

    void pausar(JuegoServicio contexto);

    void terminar(JuegoServicio contexto);

    void reiniciar(JuegoServicio contexto);

    String getNombre();
}
