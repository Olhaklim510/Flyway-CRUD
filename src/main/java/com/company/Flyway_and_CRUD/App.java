package com.company.Flyway_and_CRUD;

import com.company.Flyway_and_CRUD.services.DatabaseInitService;

public class App {
    public static void main(String[] args) {
        final String connectionUrl="jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
    }
}
