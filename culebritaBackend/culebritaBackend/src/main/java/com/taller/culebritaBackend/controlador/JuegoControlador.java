package com.taller.culebritaBackend.controlador;

import com.taller.culebritaBackend.dto.MensajeAccionDTO;
import com.taller.culebritaBackend.modelo.Direccion;
import com.taller.culebritaBackend.servicio.JuegoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/*
 Controlador WebSocket (capa Controller del patrón MVC).
 Recibe acciones del cliente en /app/accion y delega al servicio.
 El servicio hace el broadcast del estado por /tema/estado.
 */
@Controller
public class JuegoControlador {  


    @Autowired
    private final JuegoServicio juegoServicio;

    
    public JuegoControlador(JuegoServicio juegoServicio) {
        this.juegoServicio = juegoServicio;
    }

    @MessageMapping("/accion")
    public void procesarAccion(MensajeAccionDTO mensaje) {
        switch (mensaje.getAccion()) {
            case "INICIAR"     -> juegoServicio.iniciar();
            case "PAUSAR"      -> juegoServicio.pausar();
            case "REINICIAR"   -> juegoServicio.reiniciar();
            case "ARRIBA"      -> juegoServicio.cambiarDireccion(Direccion.ARRIBA);
            case "ABAJO"       -> juegoServicio.cambiarDireccion(Direccion.ABAJO);
            case "IZQUIERDA"   -> juegoServicio.cambiarDireccion(Direccion.IZQUIERDA);
            case "DERECHA"     -> juegoServicio.cambiarDireccion(Direccion.DERECHA);
            case "OBTENER_ESTADO"    -> juegoServicio.publicarEstadoActual();
            case "OBTENER_HISTORIAL" -> juegoServicio.publicarHistorial();
        }
    }
}
