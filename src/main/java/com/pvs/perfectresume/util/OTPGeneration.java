package com.pvs.perfectresume.util;

import java.util.Random;

public class OTPGeneration {
    public static int getOTP(){
        Random random = new Random();
        int randomNumber= random.nextInt(999999);
        return randomNumber;
    }
}
