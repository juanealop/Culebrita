import { Component } from '@angular/core';
import { JuegoComponente } from './juego/juegoComponente';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [JuegoComponente],
  templateUrl: './appComponent.html',
  styleUrl: './appComponent.css',
})
export class AppComponent {}

