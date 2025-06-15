package com.CodeCrafters.Pixelo.util;

import java.util.Random;

public class Otpgenrator {
    public static int getotp(){
        Random rand = new Random();
        return rand.nextInt(900000);
    }
}
