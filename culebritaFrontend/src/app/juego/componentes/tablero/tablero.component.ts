import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tablero',
  standalone: true,
  imports: [],
  templateUrl: './tablero.component.html',
})
export class TableroComponent {

  @Input() tablero: number[][] = [];

  get tableroPlanar(): number[] {
    return this.tablero.flat();
  }

  obtenerClaseCelda(valor: number): string {
    switch (valor) {
      case 3:  return 'bg-green-700';   // Cabeza de la culebra
      case 1:  return 'bg-green-400';   // Cuerpo de la culebra
      case 2:  return 'bg-red-500';     // Comida (manzana)
      default: return 'bg-white';       // Espacio vacío
    }
  }
}
