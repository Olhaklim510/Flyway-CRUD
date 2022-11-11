package com.company.Flyway_and_CRUD.client;

import com.company.Flyway_and_CRUD.services.DatabaseInitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ClientServiceTest {
    private Connection connection;
    private ClientService clientService;


    @BeforeEach
    public void beforeEach() throws SQLException {
        final String connectionUrl="jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        clientService = new ClientService(connection);
    }

    @AfterEach
    public void AfterEach() throws SQLException {
        connection.close();
    }
    @Test
    public void testThatClientCreatedCorrectly() throws SQLException {
        Client clientOrigin=new Client();

        String clientNameOrigin=clientOrigin.getName();
        long clientSavedId = clientService.create(clientNameOrigin);

        String clientNameSaved = clientService.getById(clientSavedId);

        Assertions.assertEquals(clientNameOrigin,clientNameSaved);

    }

}