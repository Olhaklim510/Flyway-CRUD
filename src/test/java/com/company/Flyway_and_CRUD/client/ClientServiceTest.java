package com.company.Flyway_and_CRUD.client;

import com.company.Flyway_and_CRUD.services.DatabaseInitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import static org.mockito.Mockito.mock;

class ClientServiceTest {
    private Connection connection;
    private ClientService clientService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        clientService = new ClientService(connection);
    }

    @AfterEach
    public void AfterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testThatClientCreatedCorrectly() throws Exception {
        List<Client> originalClients = new ArrayList<>();
        Client fullValueClient = new Client();
        fullValueClient.setName("Test");
        originalClients.add(fullValueClient);

        String clientNameOriginal = fullValueClient.getName();
        long clientSavedId = clientService.create(fullValueClient.getName());
        String clientNameSaved = clientService.getById(clientSavedId);

        Assertions.assertEquals(clientNameOriginal, clientNameSaved);

        Connection connection = mock(Connection.class);
        ClientService clientService = new ClientService(connection);
        Assertions.assertThrowsExactly(Exception.class, () -> clientService.create(null));
        Assertions.assertThrowsExactly(Exception.class, () -> clientService.create("I"));
    }

    @Test
    public void testThatSetNameHandledCorrectly() throws Exception {
        Client originalClient=new Client();
        originalClient.setName("TestSetName");

        long id = clientService.create(originalClient.getName());
        originalClient.setId(id);

        originalClient.setName("NewTestSetName");
        clientService.setName(id,originalClient.getName());

        Assertions.assertEquals(id,originalClient.getId());
        Assertions.assertEquals("NewTestSetName",clientService.getById(id));

        Connection connection = mock(Connection.class);
        ClientService clientService = new ClientService(connection);
        Assertions.assertThrowsExactly(Exception.class, () -> clientService.setName(id,null));
        Assertions.assertThrowsExactly(Exception.class, () -> clientService.setName(id,"I"));
    }

    @Test
    public void testThatDeleteByIdHandledCorrectly() throws Exception {
        Client originalClient=new Client();
        originalClient.setName("TestDeleteById");

        long id = clientService.create(originalClient.getName());
        originalClient.setId(id);

        clientService.deleteById(id);

        Assertions.assertNull(clientService.getById(id));
    }

    @Test
    public void testThatGetAllListClientHandledCorrectly() throws Exception {
        Client expected=new Client();
        expected.setName("TestGetAllList");

        long id = clientService.create(expected.getName());
        expected.setId(id);

        List <Client> expectedClients= Collections.singletonList(expected);
        List <Client> actualClients=clientService.listAll();
        int indexExpectedClient = 0;
        for (int i = 0; i < actualClients.size();i++) {
            if(actualClients.get(i).getId()==expected.getId()) {
                indexExpectedClient=i;
            }
        }
        Assertions.assertEquals(expectedClients.get(0).toString(),actualClients.get(indexExpectedClient).toString());
    }
}