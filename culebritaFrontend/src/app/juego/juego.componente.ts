import { Component, HostListener, OnInit } from '@angular/core';
import { NgClass } from '@angular/common';
import { JuegoVistaModelo } from './juego.vista-modelo';
import { TableroComponent } from './componentes/tablero/tablero.component';
import { HistorialComponent } from './componentes/historial/historial.component';

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
  templateUrl: './juego.componente.html',
  styleUrl: './juego.componente.css',
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
