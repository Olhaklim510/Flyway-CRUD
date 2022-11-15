package com.company.flyway_and_crud;

import com.company.flyway_and_crud.services.DatabaseInitService;

public class App {
    public static void main(String[] args) {
        final String connectionUrl= "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
    }
}
