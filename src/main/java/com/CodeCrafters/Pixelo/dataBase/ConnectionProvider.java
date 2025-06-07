package com.CodeCrafters.Pixelo.dataBase;



import java.sql.Connection;
import java.sql.DriverManager;



public class ConnectionProvider {
    public  Connection getCon(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            final String db= "jdbc:mysql://pixelo-editor-code-crafter.f.aivencloud.com:25243/defaultdb?ssl-mode=REQUIRED"; // Replace with your own value
            final String user="avnadmin";// Replace with your own value
            final  String pass = "AVNS__69AcqJHRH3CEWXFwEv";// Replace with your own value
            Connection con = DriverManager.getConnection(db,user,pass);
            return con;

        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
            return null;
        }
    }
}


