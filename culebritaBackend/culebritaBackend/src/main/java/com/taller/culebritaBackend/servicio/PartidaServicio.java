package com.taller.culebritaBackend.servicio;

import com.taller.culebritaBackend.dto.PartidaDTO;
import com.taller.culebritaBackend.modelo.Partida;
import com.taller.culebritaBackend.repositorio.PartidaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PartidaServicio {

    private static final Logger log = LoggerFactory.getLogger(PartidaServicio.class);

    private final PartidaRepositorio repositorio;

    @Autowired
    public PartidaServicio(PartidaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /*
     Guarda una partida finalizada en Cassandra. Si Cassandra no está disponible, registra el error pero no interrumpe el juego.
     */
    public void guardar(long duracionSegundos, int tamanoMaximo) {
        try {
            Partida partida = new Partida(
                    UUID.randomUUID(),
                    Instant.now(),
                    duracionSegundos,
                    tamanoMaximo
            );
            repositorio.save(partida);
        } catch (Exception e) {
            log.error("No se pudo guardar la partida en Cassandra: {}", e.getMessage());
        }
    }

    /*
      Retorna todas las partidas ordenadas de más reciente a más antigua.
     */
    public List<PartidaDTO> listar() {
        return repositorio.findAll()
                .stream()
                .sorted(Comparator.comparing(Partida::getFecha).reversed())
                .map(p -> new PartidaDTO(p.getId(), p.getFecha(), p.getDuracionSegundos(), p.getTamanoMaximo()))
                .collect(Collectors.toList());
    }
}
