import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partida } from '../modelos/partida.modelo';

/**
 * Model del patrón MVVM para el historial.
 * Encapsula el acceso HTTP al backend; el ViewModel lo usa pero la Vista nunca lo ve.
 */
@Injectable({
  providedIn: 'root',
})
export class PartidaServicio {

  private readonly API_URL = 'http://localhost:8080/api/partidas';

  constructor(private readonly http: HttpClient) {}

  obtenerHistorial(): Observable<Partida[]> {
    return this.http.get<Partida[]>(this.API_URL);
  }
}
