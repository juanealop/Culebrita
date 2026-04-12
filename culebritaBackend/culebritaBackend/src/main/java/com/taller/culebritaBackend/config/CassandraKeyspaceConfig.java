package com.taller.culebritaBackend.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Crea el keyspace de Cassandra si no existe antes de que
 * Spring Data intente conectarse a él.
 */
@Configuration
public class CassandraKeyspaceConfig {

    @Bean
    public CqlSession cqlSession(
            @Value("${spring.cassandra.contact-points:localhost}") String contactPoint,
            @Value("${spring.cassandra.port:9042}") int puerto,
            @Value("${spring.cassandra.local-datacenter:datacenter1}") String datacenter,
            @Value("${spring.cassandra.keyspace-name:culebrita}") String keyspace) {

        // Conectamos al sistema para crear el keyspace
        try (CqlSession sesionSistema = CqlSession.builder()
                .addContactPoint(new java.net.InetSocketAddress(contactPoint, puerto))
                .withLocalDatacenter(datacenter)
                .build()) {
            sesionSistema.execute(
                "CREATE KEYSPACE IF NOT EXISTS " + keyspace +
                " WITH replication = {'class':'SimpleStrategy','replication_factor':1};"
            );
        }

        // Devolvemos la sesión real del keyspace
        return CqlSession.builder()
                .addContactPoint(new java.net.InetSocketAddress(contactPoint, puerto))
                .withLocalDatacenter(datacenter)
                .withKeyspace(keyspace)
                .build();
    }
}
