package br.com.sgcraft.drops.utils;

import java.sql.*;

import br.com.sgcraft.drops.Main;



public class MySQL
{
    public static Connection connection;
    Statement statement;
	private static Main Plugin;
	private String user;
	private String password;
	private String database;
	private String host;
    
    static {
        Plugin = Main.aqui;
    }
	
    
    public MySQL(final String user, final String password, final String database, final String host) {
        try {
            this.user = user;
            this.password = password;
            this.database = database;
            this.host = host;
            MySQL.connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
            (this.statement = MySQL.connection.createStatement()).execute("CREATE TABLE IF NOT EXISTS SgDROPS_IMPORT (Nick VARCHAR(16), MEGA INT(2), EPIC INT(2), ULTRA INT(2), MVP INT(2), RECEBIDO boolean, PRIMARY KEY (Nick))");
            (this.statement = MySQL.connection.createStatement()).execute("CREATE TABLE IF NOT EXISTS SgDROPS_PENDENTES (Nick VARCHAR(16), MEGA INT(2), EPIC INT(2), ULTRA INT(2), MVP INT(2), PRIMARY KEY (Nick))");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public String getUser() {
        return this.user;
    }
    
    public void setUser(final String user) {
        this.user = user;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getDatabase() {
        return this.database;
    }
    
    public void setDatabase(final String database) {
        this.database = database;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
}
