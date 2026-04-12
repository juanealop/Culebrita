package com.taller.culebritaBackend.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
  Crea el keyspace de Cassandra si no existe antes de que
  Spring Data intente conectarse a él.
 */
@Configuration
public class CassandraKeyspaceConfig {

    @Bean
    public CqlSession cqlSession(
            @Value("${spring.cassandra.contact-points:localhost}") String contactPoint,
            @Value("${spring.cassandra.port:9042}") int puerto,
            @Value("${spring.cassandra.local-datacenter:datacenter1}") String datacenter,
            @Value("${spring.cassandra.keyspace-name:culebrita}") String keyspace,
            @Value("${spring.cassandra.replication-factor:2}") int replicationFactor) {

        String[] nodos = contactPoint.split(",");

        // Conectamos al sistema para crear el keyspace
        var builder = CqlSession.builder()
                .withLocalDatacenter(datacenter);

        for (String nodo : nodos) {
            builder.addContactPoint(new java.net.InetSocketAddress(nodo.trim(), puerto));
        }

        try (CqlSession sesionSistema = builder.build()) {
            sesionSistema.execute(
                "CREATE KEYSPACE IF NOT EXISTS " + keyspace +
                " WITH replication = {'class':'NetworkTopologyStrategy','" + datacenter + "':" + replicationFactor + "};"
            );
        }

        // Devolvemos la sesión real del keyspace
        var sesionBuilder = CqlSession.builder()
                .withLocalDatacenter(datacenter)
                .withKeyspace(keyspace);

        for (String nodo : nodos) {
            sesionBuilder.addContactPoint(new java.net.InetSocketAddress(nodo.trim(), puerto));
        }

        return sesionBuilder.build();
    }
}
