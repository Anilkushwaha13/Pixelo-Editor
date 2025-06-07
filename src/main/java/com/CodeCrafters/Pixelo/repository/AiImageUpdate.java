package com.CodeCrafters.Pixelo.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public class AiImageUpdate {

    @Autowired
    private DataSource dataSource;

    public boolean updateImage(String userName,String type,byte[] bytes) {
        Connection con= null;
        PreparedStatement stat = null;
        try  {
            con = dataSource.getConnection();
            String sql = "insert into imageai values (?,?,?,?);";
            stat = con.prepareStatement(sql);
            stat.setString(1, userName);
            stat.setBytes(2, bytes);
            stat.setString(3, type);
            stat.setString(4, String.valueOf(new Timestamp(System.currentTimeMillis())));
            int result = stat.executeUpdate();

            if (result == 0) {
                return false;
            } else return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stat.close();
                con.close();
            } catch (Exception e) {
                System.out.println("error:" + e);
            }
        }
    }

    public ArrayList<BufferedImage> getAiImage(int req){
        Connection con = null;
        PreparedStatement stat = null;
        int image = 10;
        int offset = req * image;
        ArrayList<BufferedImage> list = new ArrayList<>();
        try {
            con = dataSource.getConnection();
            String sql = "SELECT * FROM appusers.imageai limit ? offset ?;";
            stat = con.prepareStatement(sql);
            stat.setInt(1,image);
            stat.setInt(2,offset);
            ResultSet result = stat.executeQuery();
            while (result.next()){
                byte[] bytes= result.getBytes("imageData");
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                BufferedImage img = ImageIO.read(in);
                list.add(img);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stat.close();
                con.close();

            } catch (Exception e) {
                System.out.println("error:" + e);
            }
        }
        return  list;
    }

}
