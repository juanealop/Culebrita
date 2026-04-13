import { Component, OnInit, OnDestroy, output, computed, signal } from '@angular/core';
import { Subscription } from 'rxjs';
import { JuegoServicio } from '../../servicios/juegoServicio';
import { Partida } from '../../../modelos/partidaModelo';

@Component({
  selector: 'app-historial',
  standalone: true,
  templateUrl: './historialComponent.html',
})
export class HistorialComponent implements OnInit, OnDestroy {

  // ─── Salida (evento hacia el padre) ────────────────────────────────────
  readonly volver = output<void>();

  // ─── Señales del ViewModel ──────────────────────────────────────────────
  private readonly _partidas = signal<Partida[]>([]);
  readonly cargando = signal(true);
  readonly error    = signal(false);

  readonly partidas    = computed(() => this._partidas());
  readonly sinRegistros = computed(
    () => !this.cargando() && !this.error() && this.partidas().length === 0
  );

  private sub!: Subscription;

  constructor(private readonly juegoServicio: JuegoServicio) {}

  ngOnInit(): void {
    this.sub = this.juegoServicio.historial$.subscribe(partidas => {
      this._partidas.set(partidas);
      this.cargando.set(false);
      this.error.set(false);
    });
    this.juegoServicio.solicitarHistorial();
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  // ─── Comandos ──────────────────────────────────────────────────────────

  onVolver(): void {
    this.volver.emit();
  }

  formatearDuracion(segundos: number): string {
    const m = Math.floor(segundos / 60).toString().padStart(2, '0');
    const s = (segundos % 60).toString().padStart(2, '0');
    return `${m}:${s}`;
  }

  formatearFecha(iso: string): string {
    const d = new Date(iso);
    const dd   = d.getDate().toString().padStart(2, '0');
    const mm   = (d.getMonth() + 1).toString().padStart(2, '0');
    const yyyy = d.getFullYear();
    const hh   = d.getHours().toString().padStart(2, '0');
    const min  = d.getMinutes().toString().padStart(2, '0');
    return `${dd}/${mm}/${yyyy} ${hh}:${min}`;
  }
}
