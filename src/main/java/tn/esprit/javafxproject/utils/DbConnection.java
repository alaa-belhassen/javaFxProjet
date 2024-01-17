package tn.esprit.javafxproject.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private String user = "postgres";
    private String password = "postgres";
    private String url = "jdbc:postgresql://localhost:5433/javageeks";
    private static DbConnection instance;
    private static Connection cnx;

    public static Connection getCnx() {
        return cnx;
    }

    private DbConnection()  {
        try {
            cnx = DriverManager.getConnection(url,user,password);
            System.out.println("connected!!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DbConnection getInstance(){
        if(instance==null) instance= new DbConnection();
        return instance;
    };

    public static void createTable(Connection cnx , String table,String fields){
        Statement statement;
        try{
            String query= "create table "+table+"("+fields+");";
            statement=cnx.createStatement();
            statement.executeUpdate(query);
            System.out.println("table created ");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }



    }
