/*
 * Copyright (c) 2020.
 * Plugin by Lucas L. - CompileException.
 */

package de.teamsoul.party.mysql;

import de.teamsoul.party.main.PartySystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MySQL {

    ArrayList<Connection> connections = new ArrayList<>();

    private static Connection connection;
    private static Statement command;
    private static ResultSet data;

    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    public MySQL(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public MySQL openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            connections.add(connection);
            return new MySQL(host, port, database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                connections.remove(connection);
                connection.close();
            } catch (SQLException ignored) { }
        }
    }

    public void closeALl() throws SQLException {
        try {
            if (!connections.isEmpty()) {
                for (Connection connection : connections) {
                    connections.remove(connection);
                    connection.close();
                }
            }
        } catch (ConcurrentModificationException ignored) {}
    }

    public boolean createTable(String Name, String Data) {
        try {
            MySQL mySQL = new MySQL(PartySystem.getInstance().hostname, PartySystem.getInstance().port, PartySystem.getInstance().database, PartySystem.getInstance().username, PartySystem.getInstance().password).openConnection();

            mySQL.openConnection();
            PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "+Name+"("+Data+")");
            ps.executeUpdate();
            mySQL.closeConnection();
        } catch (SQLException ignored) {return false;}
        return true;
    }

    public Connection getConnection() { return connection; }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public Boolean isConnected() {
        return (connection != null);
    }
}
