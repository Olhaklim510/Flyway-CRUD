package com.company.Flyway_and_CRUD.services;

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
