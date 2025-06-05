package com.CodeCrafters.Pixelo.dataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateUserTable {
    public static void main(String[] args) {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        try(Connection con = connectionProvider.dataSource().getConnection();
            Statement firstState = con.createStatement() ) {

            ResultSet set = firstState.executeQuery("Select * FROM appuser;");
            while(set.next()){
                System.out.println("Email: "+set.getString("email"));
                System.out.println("Name: "+set.getString("name"));
                System.out.println("Encoded Password: "+set.getString("password"));
            }

//            int createResult = firstState.executeUpdate("CREATE TABLE imageai (" +
//                    "    email VARCHAR(200)," +
//                    "    imagedata LONGBLOB ," +
//                    "    imageType VARCHAR(20) not null," +
//                    "    CONSTRAINT fk_email FOREIGN KEY (email) REFERENCES appuser(email)" +
//                    ");");
//            int createResult = firstState.executeUpdate("CREATE TABLE draftImage (" +
//                    "    email VARCHAR(200)," +
//                    "    imagedata LONGBLOB ," +
//                    "    imageType VARCHAR(20) not null," +
//                    "    CONSTRAINT fk1_email FOREIGN KEY (email) REFERENCES appuser(email)" +
//                    ");");
//            int createResult = firstState.executeUpdate("CREATE TABLE appuser ("
//                    + "email VARCHAR(200) PRIMARY KEY,  "
//                    + "name VARCHAR(200), "
//                    + "password VARCHAR(200) )");
//
//            if (createResult == 0) {  // executeUpdate() returns 0 for DDL statements
//                JOptionPane.showMessageDialog(null, "Table created successfully");
//            }

//              Drop table (if needed)
//            int dropResult = firstState.executeUpdate("DROP TABLE imageai;");
//            if (dropResult == 0) {
//                JOptionPane.showMessageDialog(null, "Table dropped successfully");
//            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
