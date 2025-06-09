package com.CodeCrafters.Pixelo.repository;


import com.CodeCrafters.Pixelo.util.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class DatabaseUpdate {

    @Autowired
    private DataSource dataSource;

    public  Boolean getRegister(String Username, String email ,String password) {
        Connection con = null;
        PreparedStatement input = null;
        try {
             con =dataSource.getConnection();
            String sql = "INSERT into appuser values  (?,?,?,?)";
            input = con.prepareStatement(sql);
            input.setString(1, email);
            input.setString(2, Username);
            input.setString(3, PasswordEncrypter.hashPassword(password));
            input.setString(4, String.valueOf(new Timestamp(System.currentTimeMillis())));

            int result = input.executeUpdate();

            if (result != 0) {
                return true;
            } else return false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        finally {
            try {
                input.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public Boolean getUpdate(String email ,String name){
        Connection con = null;
        PreparedStatement input = null;
        try{
            con = dataSource.getConnection();
            String sql = "Update  appuser set name=? Where email=?";
            input = con.prepareStatement(sql);
            input.setString(1,name);
            input.setString(2,email);

            int result = input.executeUpdate();

            if(result != 0){
                return true;
            }
            else return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                input.close();
                con.close();
            } catch (Exception e) {
                System.out.println("error:" +e);
            }
        }
    }

}
