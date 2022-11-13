package com.company.Flyway_and_CRUD.client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement setNameSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement getAllSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO client (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        getByIdSt = connection.prepareStatement("SELECT name FROM client WHERE id=?");
        getAllSt = connection.prepareStatement("SELECT id, name FROM client");
        setNameSt = connection.prepareStatement("UPDATE client SET name=? WHERE id=?");
        deleteByIdSt = connection.prepareStatement("DELETE FROM client WHERE id=?");
    }

    public long create(String name) throws Exception {
        if (name == null) {
            throw new Exception("Name can't be null");
        }
        if (name.length()<2 | name.length()>1000){
            throw new Exception("Name must be more than 2 characters and less than 1000 characters");
        }
        createSt.setString(1, name);

        createSt.executeUpdate();

        long id = -1;
        try (ResultSet rs = createSt.getGeneratedKeys()) {
            rs.next();
            id = rs.getLong(1);
        }
        return id;
    }

    public String getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            return rs.getString("name");
        }
    }

    public void setName(long id, String name) throws Exception {
        if (name == null) {
            throw new Exception("Name can't be null");
        }
        if (name.length()<2 | name.length()>1000){
            throw new Exception("Name must be more than 2 characters and less than 1000 characters");
        }
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        setNameSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1,id);
        deleteByIdSt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Client> result = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));

                result.add(client);
            }
            return result;
        }
    }
}
