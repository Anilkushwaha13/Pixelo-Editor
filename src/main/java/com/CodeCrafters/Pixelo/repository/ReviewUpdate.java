package com.CodeCrafters.Pixelo.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewUpdate {

    @Autowired
    private DataSource dataSource;

    public boolean Review(String email, Double rating,String summ,String loca){
        Connection con = null;
        PreparedStatement stat = null;
        try{
            con = dataSource.getConnection();
            String sql = "INSERT INTO `imageReview`" +
                        "VALUES" +
                        "(?,?,?,?,?);";
                stat = con.prepareStatement(sql);
                stat.setString(1, email);
                stat.setDouble(2, rating);
                stat.setString(3, summ);
                stat.setString(4, loca);
                stat.setString(5, String.valueOf(new Timestamp(System.currentTimeMillis())));
                int result = stat.executeUpdate();
                   if (result == 1){
                       return true;
                   } else  return false;
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

    public List<Map<String, String>> getReview(){
        Connection con = null;
        PreparedStatement stat = null;
        List<Map<String,String>> list = new ArrayList<>();
        try {
            con = dataSource.getConnection();
            String sql = "SELECT ir.*, au.name" +
                    "FROM imageReview ir" +
                    "JOIN appuser au ON ir.email = au.email" +
                    "ORDER BY ir.time DESC;";
            stat = con.prepareStatement(sql);
            ResultSet result = stat.executeQuery();
            while (result.next()){
                String name= result.getString("name");
                String summary= result.getString("summary");
                String location= result.getString("location");
                double rate= result.getDouble("rating");
                Map<String,String> review= new HashMap<>();
                review.put("name",name);
                review.put("summary",summary);
                review.put("location",location);
                review.put("rating",Double.toString(rate));
                list.add(review);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
        finally {
            try {
                stat.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return  list;
    }

    public boolean reviewDelete(String email){
        Connection con = null;
        PreparedStatement stat = null;
        try{
            con = dataSource.getConnection();
            String sql = "Delete FROM imageReview where email=?;";
            stat = con.prepareStatement(sql);
            stat.setString(1,email);
            int result = stat.executeUpdate();

            if (result == 0) {
                return false;
            } else return true;
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
