import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { EstadoTablero } from '../modelos/estado-tablero.modelo';
import { Partida } from '../modelos/partida.modelo';

@Injectable({
  providedIn: 'root',
})
export class JuegoServicio implements OnDestroy {

  private readonly SERVIDOR_URL = 'http://localhost:8080/culebrita-ws';
  private readonly TEMA_ESTADO    = '/tema/estado';
  private readonly TEMA_HISTORIAL = '/tema/historial';
  private readonly APP_ACCION     = '/app/accion';

  private cliente!: Client;
  private conectado = false;
  private estadoSubject   = new BehaviorSubject<EstadoTablero | null>(null);
  private historialSubject = new BehaviorSubject<Partida[]>([]);

  readonly estado$    = this.estadoSubject.asObservable();
  readonly historial$ = this.historialSubject.asObservable();

  conectar(): void {
    if (this.conectado) {
      return;
    }

    try {
      this.cliente = new Client({
        webSocketFactory: () => new SockJS(this.SERVIDOR_URL),
        reconnectDelay: 5000,
        onConnect: () => {
          this.cliente.subscribe(this.TEMA_ESTADO, (mensaje: Message) => {
            const estado: EstadoTablero = JSON.parse(mensaje.body);
            this.estadoSubject.next(estado);
          });
          this.cliente.subscribe(this.TEMA_HISTORIAL, (mensaje: Message) => {
            const partidas: Partida[] = JSON.parse(mensaje.body);
            this.historialSubject.next(partidas);
          });
          this.enviarAccion('OBTENER_ESTADO');
        },
        onWebSocketError: (evento) => {
          console.error('Error de WebSocket con el backend', evento);
        },
        onStompError: (frame) => {
          console.error('Error STOMP con el backend', frame.headers['message']);
        },
      });

      this.conectado = true;
      this.cliente.activate();
    } catch (error) {
      console.error('No se pudo inicializar la conexión con el backend', error);
      this.conectado = false;
    }
  }

  enviarAccion(accion: string): void {
    if (this.cliente?.connected) {
      this.cliente.publish({
        destination: this.APP_ACCION,
        body: JSON.stringify({ accion }),
      });
    }
  }

  solicitarHistorial(): void {
    this.enviarAccion('OBTENER_HISTORIAL');
  }

  ngOnDestroy(): void {
    this.cliente?.deactivate();
  }
}

