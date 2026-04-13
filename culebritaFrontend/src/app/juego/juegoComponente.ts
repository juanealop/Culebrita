import { Component, HostListener, OnInit } from '@angular/core';
import { NgClass } from '@angular/common';
import { JuegoVistaModelo } from './juegoVistaModelo';
import { TableroComponent } from './componentes/tablero/tableroComponent';
import { HistorialComponent } from './componentes/historial/historialComponent';

/**
 * Vista del juego (patrón MVVM).
 *
 * Responsabilidades:
 *  - Renderizar la plantilla vinculada al VistaModelo (vm).
 *  - Capturar eventos del usuario (teclado) y delegarlos al vm.
 *  - Sin lógica de negocio: sólo delega en vm y gestiona el DOM.
 */
@Component({
  selector: 'app-juego',
  standalone: true,
  imports: [NgClass, TableroComponent, HistorialComponent],
  templateUrl: './juegoComponente.html',
  styleUrl: './juegoComponente.css',
  providers: [JuegoVistaModelo],
})
export class JuegoComponente implements OnInit {

  constructor(public readonly vm: JuegoVistaModelo) {}

  ngOnInit(): void {
    this.vm.inicializar();
  }

  @HostListener('document:keydown', ['$event'])
  onTecla(evento: KeyboardEvent): void {
    if (this.vm.procesarTecla(evento.key)) {
      evento.preventDefault();
    }
  }
}
