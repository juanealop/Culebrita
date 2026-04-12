package com.taller.culebritaBackend.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EstadoTableroDTO {

    private int[][] tablero;
    private List<int[]> culebra;
    private int[] comida;
    private int puntuacion;
    private String estadoJuego;

  
}   
