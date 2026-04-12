import { Component, OnInit, OnDestroy, output, computed, signal } from '@angular/core';
import { Subscription } from 'rxjs';
import { JuegoServicio } from '../../servicios/juego.servicio';
import { Partida } from '../../modelos/partida.modelo';

/**
 * ViewModel del HistorialComponent (patrón MVVM).
 * La Vista (historial.component.html) sólo accede a las señales y al comando onVolver().
 * Toda la comunicación con el backend va por WebSocket a través de JuegoServicio.
 */
@Component({
  selector: 'app-historial',
  standalone: true,
  templateUrl: './historial.component.html',
})
export class HistorialComponent implements OnInit, OnDestroy {

  // ─── Salida (evento hacia el padre) ────────────────────────────────────
  readonly volver = output<void>();

  // ─── Señales del ViewModel ──────────────────────────────────────────────
  private readonly _partidas = signal<Partida[]>([]);
  readonly cargando = signal(true);
  readonly error    = signal(false);

  /** Lista ordenada de partidas (ya viene ordenada desde el backend) */
  readonly partidas = computed(() => this._partidas());

  // ─── Estado derivado ─────────────────────────────────────────────────────
  readonly sinRegistros = computed(() => !this.cargando() && !this.error() && this.partidas().length === 0);

  private sub!: Subscription;

  constructor(private readonly juegoServicio: JuegoServicio) {}

  ngOnInit(): void {
    // Escucha la respuesta del backend vía WebSocket
    this.sub = this.juegoServicio.historial$.subscribe(partidas => {
      this._partidas.set(partidas);
      this.cargando.set(false);
      this.error.set(false);
    });
    // Solicita el historial
    this.juegoServicio.solicitarHistorial();
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  // ─── Comandos (la Vista llama sólo estos métodos) ───────────────────────

  onVolver(): void {
    this.volver.emit();
  }

  /** Formatea duración en mm:ss */
  formatearDuracion(segundos: number): string {
    const m = Math.floor(segundos / 60).toString().padStart(2, '0');
    const s = (segundos % 60).toString().padStart(2, '0');
    return `${m}:${s}`;
  }

  /** Formatea la fecha ISO a dd/MM/yyyy HH:mm */
  formatearFecha(iso: string): string {
    const d = new Date(iso);
    const dd = d.getDate().toString().padStart(2, '0');
    const mm = (d.getMonth() + 1).toString().padStart(2, '0');
    const yyyy = d.getFullYear();
    const hh = d.getHours().toString().padStart(2, '0');
    const min = d.getMinutes().toString().padStart(2, '0');
    return `${dd}/${mm}/${yyyy} ${hh}:${min}`;
  }
}
