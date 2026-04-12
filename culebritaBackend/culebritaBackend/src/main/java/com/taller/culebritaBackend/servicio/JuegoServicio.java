package com.taller.culebritaBackend.servicio;

import com.taller.culebritaBackend.dto.EstadoTableroDTO;
import com.taller.culebritaBackend.dto.PartidaDTO;
import com.taller.culebritaBackend.estado.EstadoEsperando;
import com.taller.culebritaBackend.estado.EstadoJuego;
import com.taller.culebritaBackend.modelo.Comida;
import com.taller.culebritaBackend.modelo.Culebra;
import com.taller.culebritaBackend.modelo.Direccion;
import com.taller.culebritaBackend.modelo.Posicion;
import com.taller.culebritaBackend.modelo.Tablero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class JuegoServicio {

    private static final int VELOCIDAD_MS = 200;
    private static final String DESTINO_ESTADO = "/tema/estado";
    private static final String DESTINO_HISTORIAL = "/tema/historial";

    private final SimpMessagingTemplate mensajeria;
    private final PartidaServicio partidaServicio;
    private final ScheduledExecutorService planificador;

    private Tablero tablero;
    private Culebra culebra;
    private Comida comida;
    private int puntuacion;
    private int tamanoMaximo;
    private Instant tiempoInicio;
    private EstadoJuego estadoActual;
    private ScheduledFuture<?> tareaJuego;

    @Autowired
    public JuegoServicio(SimpMessagingTemplate mensajeria, PartidaServicio partidaServicio) {
        this.mensajeria = mensajeria;
        this.partidaServicio = partidaServicio;
        this.planificador = Executors.newSingleThreadScheduledExecutor();
        this.estadoActual = new EstadoEsperando();
        inicializarEntidades();
    }

    

    public synchronized void iniciar() {
        estadoActual.iniciar(this);
    }

    public synchronized void pausar() {
        estadoActual.pausar(this);
    }

    public synchronized void reiniciar() {
        estadoActual.reiniciar(this);
    }

    public synchronized void cambiarDireccion(Direccion direccion) {
        if ("JUGANDO".equals(estadoActual.getNombre())) {
            culebra.setDireccion(direccion);
        }
    }

    public synchronized EstadoTableroDTO obtenerEstadoActual() {
        return construirDTO();
    }

    public synchronized void publicarEstadoActual() {
        enviarEstado();
    }

    public void publicarHistorial() {
        List<PartidaDTO> historial = partidaServicio.listar();
        mensajeria.convertAndSend(DESTINO_HISTORIAL, historial);
    }

    // ─── API interna (usada por los estados) ────────────────────────────────

    public void setEstado(EstadoJuego nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void comenzarBucleJuego() {
        cancelarTarea();
        tiempoInicio = Instant.now();
        tareaJuego = planificador.scheduleAtFixedRate(
                this::tickJuego, 0, VELOCIDAD_MS, TimeUnit.MILLISECONDS);
    }

    public void detenerBucleJuego() {
        cancelarTarea();
        enviarEstado();
    }

    public void reiniciarJuego() {
        cancelarTarea();
        inicializarEntidades();
        enviarEstado();
    }

    // ─── Lógica del juego ───────────────────────────────────────────────────

    private synchronized void tickJuego() {
        Posicion siguiente = culebra.calcularSiguientePosicion();

        // Colisión con las paredes
        if (siguiente.getFila() < 0 || siguiente.getFila() >= Tablero.getTamanio()
                || siguiente.getColumna() < 0 || siguiente.getColumna() >= Tablero.getTamanio()) {
            registrarPartida();
            estadoActual.terminar(this);
            return;
        }

        // Colisión con el propio cuerpo
        if (culebra.colisionaConCuerpo(siguiente)) {
            registrarPartida();
            estadoActual.terminar(this);
            return;
        }

        // ¿Come la comida?
        if (siguiente.equals(comida.getPosicion())) {
            culebra.crecer(siguiente);
            comida.reposicionar(culebra);
            puntuacion += 10;
            // Actualiza el tamaño máximo alcanzado
            if (culebra.getCuerpo().size() > tamanoMaximo) {
                tamanoMaximo = culebra.getCuerpo().size();
            }
        } else {
            culebra.mover(siguiente);
        }

        enviarEstado();
    }

    /** Calcula duración y persiste la partida en Cassandra al terminar. */
    private void registrarPartida() {
        long duracion = tiempoInicio != null
                ? Instant.now().getEpochSecond() - tiempoInicio.getEpochSecond()
                : 0;
        partidaServicio.guardar(duracion, tamanoMaximo);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private void inicializarEntidades() {
        tablero = new Tablero();
        culebra = new Culebra();
        comida = new Comida();
        puntuacion = 0;
        tamanoMaximo = culebra.getCuerpo().size();
        tiempoInicio = null;
    }

    private void cancelarTarea() {
        if (tareaJuego != null && !tareaJuego.isDone()) {
            tareaJuego.cancel(false);
        }
    }

    private void enviarEstado() {
        mensajeria.convertAndSend(DESTINO_ESTADO, construirDTO());
    }

    private EstadoTableroDTO construirDTO() {
        // Reconstruye la matriz a partir de entidades
        tablero.reiniciar();

        List<int[]> cuerpoLista = new ArrayList<>();
        boolean esCabeza = true;
        for (Posicion pos : culebra.getCuerpo()) {
            // 3 = cabeza, 1 = cuerpo
            tablero.setCelda(pos.getFila(), pos.getColumna(), esCabeza ? 3 : 1);
            cuerpoLista.add(new int[]{pos.getFila(), pos.getColumna()});
            esCabeza = false;
        }

        // 2 = comida
        tablero.setCelda(comida.getPosicion().getFila(), comida.getPosicion().getColumna(), 2);

        EstadoTableroDTO dto = new EstadoTableroDTO();
        dto.setTablero(tablero.getMatriz());
        dto.setCulebra(cuerpoLista);
        dto.setComida(new int[]{comida.getPosicion().getFila(), comida.getPosicion().getColumna()});
        dto.setPuntuacion(puntuacion);
        dto.setEstadoJuego(estadoActual.getNombre());
        return dto;
    }
}
