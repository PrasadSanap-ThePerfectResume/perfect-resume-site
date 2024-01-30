package com.pvs.perfectresume.google;

public class CenturyFromYear {
    public static void main(String[] args) {
        int year=1970;
        if (year%100== 0){
            System.out.println(year/100);
        }else{
            System.out.println((year/100)+1);
        }




    }
}
