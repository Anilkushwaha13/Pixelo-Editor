package com.CodeCrafters.Pixelo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class LoginUser {

    @Autowired
    private DataSource dataSource;

    public Map<String,String> getLogin(String Email){
        Connection con = null;
        PreparedStatement stat = null;
        try{
             con =dataSource.getConnection();
            String sql = "Select * From appuser where email=? ";
            stat = con.prepareStatement(sql);
            stat.setString(1,Email);
            ResultSet result = stat.executeQuery();
            if (result.next()){
                Map<String,String> userData = new HashMap<>();
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");
                userData.put("name",name);
                userData.put("email",email);
                userData.put("password",password);
                return  userData;
            }
            else return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                stat.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
