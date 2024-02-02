//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tn.esprit.javafxproject.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private String user = "postgres";
    private String password = "admin";
    private String url = "jdbc:postgresql://localhost:5432/Ticketing";
    private static DbConnection instance;
    private static Connection cnx;

    public static Connection getCnx() {
        return cnx;
    }

    private DbConnection() {
        try {
            cnx = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("connected!!");
        } catch (SQLException var2) {
            System.err.println(var2.getMessage());
        }

    }

    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }

        return instance;
    }

    public static void createTable(Connection cnx, String table, String fields) {
        try {
            String query = "create table " + table + "(" + fields + ");";
            Statement statement = cnx.createStatement();
            statement.executeUpdate(query);
            System.out.println("table created ");
        } catch (Exception var5) {
            System.err.println(var5.getMessage());
        }

    }
}
