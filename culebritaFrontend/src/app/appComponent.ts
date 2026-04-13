import { Component } from '@angular/core';
import { JuegoComponente } from './juego/juegoComponente';

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
  imports: [JuegoComponente],
  templateUrl: './appComponent.html',
  styleUrl: './appComponent.css',
})
export class AppComponent {}

