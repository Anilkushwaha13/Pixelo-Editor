package com.CodeCrafters.Pixelo.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypter {

    public static String hashPassword(String password){
        String salt = BCrypt.gensalt(7);
        return BCrypt.hashpw(password,salt);
    }
    public static Boolean checkPassword(String password,String hasPassword){
        return BCrypt.checkpw(password,hasPassword);
    }

}
