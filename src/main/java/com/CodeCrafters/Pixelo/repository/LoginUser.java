package com.CodeCrafters.Pixelo.repository;

import com.CodeCrafters.Pixelo.dataBase.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class LoginUser {

    @Autowired
    ConnectionProvider connectionProvider;

    public Map<String,String> getLogin(String Email){
        PreparedStatement stat = null;
        try(Connection con = connectionProvider.dataSource().getConnection()){
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
