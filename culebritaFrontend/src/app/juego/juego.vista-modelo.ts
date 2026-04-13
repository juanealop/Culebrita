import { Injectable, OnDestroy } from '@angular/core';
import { computed, signal } from '@angular/core';
import { Subscription } from 'rxjs';
import { JuegoServicio } from './servicios/juego.servicio';
import { EstadoTablero } from '../modelos/estado-tablero.modelo';

/**
 * VistaModelo del juego (patrón MVVM).
 *
 * Responsabilidades:
 *  - Mantiene el estado reactivo mediante señales (signals).
 *  - Expone a la Vista propiedades ya preparadas para el binding
 *    (clases CSS calculadas, flags booleanos, datos formateados).
 *  - Expone comandos (iniciar, pausar, reiniciar, procesarTecla…) que
 *    la Vista invoca sin conocer directamente el servicio.
 *  - El Modelo (JuegoServicio) es privado: la Vista nunca lo toca.
 *
 * Se provee a nivel de componente (providers: [JuegoVistaModelo])
 * para que su ciclo de vida quede atado al de la Vista raíz del juego.
 */
@Injectable()
export class JuegoVistaModelo implements OnDestroy {

  // ─── Estado interno ───────────────────────────────────────────────────
  private readonly _estado = signal<EstadoTablero | null>(null);
  private readonly _vista  = signal<'juego' | 'historial'>('juego');
  private sub!: Subscription;

  // ─── Propiedades expuestas a la Vista ─────────────────────────────────
  readonly esHistorial    = computed(() => this._vista() === 'historial');
  readonly puntuacion     = computed(() => this._estado()?.puntuacion ?? 0);
  readonly etiquetaEstado = computed(() => this._estado()?.estadoJuego ?? 'CONECTANDO...');
  readonly tablero        = computed(
    () => this._estado()?.tablero ?? Array.from({ length: 20 }, () => Array(20).fill(0))
  );
  readonly claseEstadoBadge = computed(() => {
    switch (this._estado()?.estadoJuego) {
      case 'ESPERANDO': return 'bg-yellow-100 text-yellow-700';
      case 'JUGANDO':   return 'bg-green-100  text-green-700';
      case 'PAUSADO':   return 'bg-blue-100   text-blue-700';
      case 'TERMINADO': return 'bg-red-100    text-red-700';
      default:          return 'bg-gray-100   text-gray-500';
    }
  });
  readonly mostrarIniciar   = computed(() =>
    this._estado()?.estadoJuego === 'ESPERANDO' || this._estado()?.estadoJuego === 'PAUSADO'
  );
  readonly mostrarPausar    = computed(() => this._estado()?.estadoJuego === 'JUGANDO');
  readonly mostrarReiniciar = computed(() =>
    ['JUGANDO', 'PAUSADO', 'TERMINADO'].includes(this._estado()?.estadoJuego ?? '')
  );
  readonly juegoTerminado   = computed(() => this._estado()?.estadoJuego === 'TERMINADO');

  constructor(private readonly juegoServicio: JuegoServicio) {}

  // ─── Inicialización (llamada desde la Vista en ngOnInit) ───────────────
  inicializar(): void {
    this.juegoServicio.conectar();
    this.sub = this.juegoServicio.estado$.subscribe(estado => this._estado.set(estado));
  }

  // ─── Comandos ──────────────────────────────────────────────────────────
  iniciar():      void { this.juegoServicio.enviarAccion('INICIAR'); }
  pausar():       void { this.juegoServicio.enviarAccion('PAUSAR'); }
  reiniciar():    void { this.juegoServicio.enviarAccion('REINICIAR'); }
  verHistorial(): void { this._vista.set('historial'); }
  volver():       void { this._vista.set('juego'); }

  /**
   * Procesa una tecla del teclado y envía la acción correspondiente.
   * Retorna true si la tecla fue reconocida (para que la Vista llame preventDefault).
   */
  procesarTecla(tecla: string): boolean {
    const mapa: Record<string, string> = {
      ArrowUp: 'ARRIBA',    ArrowDown: 'ABAJO',
      ArrowLeft: 'IZQUIERDA', ArrowRight: 'DERECHA',
      w: 'ARRIBA', s: 'ABAJO', a: 'IZQUIERDA', d: 'DERECHA',
    };
    const accion = mapa[tecla];
    if (accion) {
      this.juegoServicio.enviarAccion(accion);
      return true;
    }
    return false;
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }
}
