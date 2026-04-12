export interface EstadoTablero {
  tablero: number[][];
  culebra: number[][];
  comida: number[];
  puntuacion: number;
  estadoJuego: 'ESPERANDO' | 'JUGANDO' | 'PAUSADO' | 'TERMINADO';
}
