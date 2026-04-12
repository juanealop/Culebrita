package com.taller.culebritaBackend.repositorio;

import com.taller.culebritaBackend.modelo.Partida;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartidaRepositorio extends CassandraRepository<Partida, UUID> {
}
