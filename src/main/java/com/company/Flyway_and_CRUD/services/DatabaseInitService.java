package com.company.Flyway_and_CRUD.services;

import com.company.Flyway_and_CRUD.prefs.Prefs;
import org.flywaydb.core.Flyway;

import java.sql.Connection;

public class DatabaseInitService {
    public void initDb(String connectionUrl) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(connectionUrl, null, null)
                .load();

        flyway.migrate();
    }
}
