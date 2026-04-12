import { Component, OnInit, HostListener, signal, computed } from '@angular/core';
import { NgClass } from '@angular/common';
import { JuegoServicio } from './servicios/juego.servicio';
import { TableroComponent } from './componentes/tablero/tablero.component';
import { HistorialComponent } from './componentes/historial/historial.component';
import { EstadoTablero } from './modelos/estado-tablero.modelo';

/**
 * ViewModel del patrón MVVM.
 *
 * Responsabilidades:
 *  - Mantiene el estado reactivo del juego mediante signals.
 *  - Expone a la Vista propiedades ya preparadas para el binding
 *    (clases CSS calculadas, flags booleanos, datos formateados).
 *  - Expone comandos (onIniciar, onPausar, onReiniciar, onTecla) que
 *    la Vista invoca sin conocer el servicio.
 *  - El Model (JuegoServicio) es privado: la Vista nunca lo toca.
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [TableroComponent, HistorialComponent, NgClass],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {

  // ─── Estado interno del ViewModel ──────────────────────────────────
  private readonly _estado = signal<EstadoTablero | null>(null);

  /** Controla qué pantalla está activa: 'juego' | 'historial' */
  private readonly _vista = signal<'juego' | 'historial'>('juego');

  readonly esHistorial = computed(() => this._vista() === 'historial');

  // ─── Propiedades computadas expuestas a la Vista ────────────────────
  // La Vista sólo bindea a estas propiedades; nunca evalúa lógica propia.

  readonly puntuacion = computed(() => this._estado()?.puntuacion ?? 0);

  readonly etiquetaEstado = computed(() => this._estado()?.estadoJuego ?? 'CONECTANDO...');

  readonly tablero = computed(
    () => this._estado()?.tablero ?? Array.from({ length: 20 }, () => Array(20).fill(0))
  );

  /** Clases Tailwind del badge de estado calculadas aquí, no en la Vista */
  readonly claseEstadoBadge = computed(() => {
    switch (this._estado()?.estadoJuego) {
      case 'ESPERANDO': return 'bg-yellow-100 text-yellow-700';
      case 'JUGANDO':   return 'bg-green-100  text-green-700';
      case 'PAUSADO':   return 'bg-blue-100   text-blue-700';
      case 'TERMINADO': return 'bg-red-100    text-red-700';
      default:          return 'bg-gray-100   text-gray-500';
    }
  });

  /** Flags de visibilidad: la Vista decide qué mostrar sin evaluar lógica */
  readonly mostrarIniciar   = computed(() =>
    this._estado()?.estadoJuego === 'ESPERANDO' || this._estado()?.estadoJuego === 'PAUSADO'
  );
  readonly mostrarPausar    = computed(() => this._estado()?.estadoJuego === 'JUGANDO');
  readonly mostrarReiniciar = computed(() =>
    ['JUGANDO', 'PAUSADO', 'TERMINADO'].includes(this._estado()?.estadoJuego ?? '')
  );
  readonly juegoTerminado   = computed(() => this._estado()?.estadoJuego === 'TERMINADO');

  // ─── El Model es privado: la Vista nunca accede al servicio ─────────
  constructor(private readonly juegoServicio: JuegoServicio) {}

  ngOnInit(): void {
    this.juegoServicio.conectar();
    // El ViewModel se suscribe al Model y actualiza el signal interno
    this.juegoServicio.estado$.subscribe((estado) => this._estado.set(estado));
  }

  // ─── Comandos de la Vista → ViewModel → Model ───────────────────────
  onIniciar():   void { this.juegoServicio.enviarAccion('INICIAR'); }
  onPausar():    void { this.juegoServicio.enviarAccion('PAUSAR'); }
  onReiniciar(): void { this.juegoServicio.enviarAccion('REINICIAR'); }

  onVerHistorial(): void { this._vista.set('historial'); }
  onVolver():       void { this._vista.set('juego'); }

  @HostListener('document:keydown', ['$event'])
  onTecla(evento: KeyboardEvent): void {
    const mapa: Record<string, string> = {
      ArrowUp: 'ARRIBA', ArrowDown: 'ABAJO',
      ArrowLeft: 'IZQUIERDA', ArrowRight: 'DERECHA',
      w: 'ARRIBA', s: 'ABAJO', a: 'IZQUIERDA', d: 'DERECHA',
    };
    const accion = mapa[evento.key];
    if (accion) {
      evento.preventDefault();
      this.juegoServicio.enviarAccion(accion);
    }
  }
}

