package com.company.Flyway_and_CRUD.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO client (name) VALUES (?)");
        getByIdSt=connection.prepareStatement("SELECT name FROM client WHERE id=?");
        selectMaxIdSt=connection.prepareStatement("SELECT max(id) AS maxId FROM client");
    }


    public long create(String name) throws SQLException {
        createSt.setString(1, name);
        createSt.executeUpdate();

        long id=-1;
        try(ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id=rs.getLong("maxId");
        }
        return id;
    }

    public String getById(long id) throws SQLException {
        getByIdSt.setLong(1,id);
        try (ResultSet rs = getByIdSt.executeQuery()){
            if(!rs.next()){
                return null;
            }
            Client result=new Client();
            result.setId(id);
            result.setName(rs.getString("name"));

            return result.getName();
        }

    }
}
