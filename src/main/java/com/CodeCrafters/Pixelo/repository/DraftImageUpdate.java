package com.CodeCrafters.Pixelo.repository;


import com.CodeCrafters.Pixelo.exception.PixeloException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DraftImageUpdate {

    @Autowired
    private DataSource dataSource;

    public boolean draftImage(String userName,String type,byte[] bytes){
        Connection con = null;
        PreparedStatement stat = null;
        try{
            con = dataSource.getConnection();
            String sql1 = "SELECT  COUNT(*) count  FROM draftImage where email = ?";
            stat = con.prepareStatement(sql1);
            stat.setString(1,userName);
            ResultSet rs = stat.executeQuery();
            int count=0;
            while (rs.next()){
                count = rs.getInt("count");
            }
            if (count < 3)  {

                String sql = "insert into draftImage (email, imagedata, imageType, time) values (?,?,?,?);";
                stat = con.prepareStatement(sql);
                stat.setString(1, userName);
                stat.setBytes(2, bytes);
                stat.setString(3, type);
                stat.setString(4, String.valueOf(new Timestamp(System.currentTimeMillis())));
                int result = stat.executeUpdate();

                if (result != 0) {
                    return true;
                } else return false;
            } else  throw new PixeloException("Limit has been reached for Draft");

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

    public Map<Integer, BufferedImage> getDraftImage(String userName){
        Connection con = null;
        PreparedStatement stat = null;
        Map<Integer,BufferedImage> image= new HashMap<>();
        try {
            con = dataSource.getConnection();
            String sql = "SELECT * FROM draftImage where email=?;";
            stat = con.prepareStatement(sql);
            stat.setString(1,userName);
            ResultSet result = stat.executeQuery();
            while (result.next()){
                byte[] bytes= result.getBytes("imageData");
                int id= result.getInt("id");
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                BufferedImage img = ImageIO.read(in);
                image.put(id,img);
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
        return  image;
    }

    public boolean deleteDraftImage(String email,int id){
        Connection con = null;
        PreparedStatement stat = null;
        try{
            con = dataSource.getConnection();
            String sql = "Delete FROM draftImage where email=? and id = ?;";
            stat = con.prepareStatement(sql);
            stat.setString(1,email);
            stat.setInt(2,id);
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
