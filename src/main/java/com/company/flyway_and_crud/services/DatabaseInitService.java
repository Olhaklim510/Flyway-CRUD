package com.company.flyway_and_crud.services;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    public void initDb(String connectionUrl) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(connectionUrl, null, null)
                .load();

        flyway.migrate();
    }
}
