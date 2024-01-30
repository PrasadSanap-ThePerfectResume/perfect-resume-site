package com.pvs.perfectresume.google;

import java.util.List;

public class ClosePaths {
    public static void main(String[] args){
        int number=649578;
        int sum=0;
        String str=String.valueOf(number);
        for(int i=0;i<str.length();i++){
            switch(str.charAt(i)){
                case '0':
                case '4':
                case '6':
                case '9':
                    sum++;
                    break;
                case '8':
                    sum=sum+2;
                    break;
                default:
                    break;
            }
        }
        System.out.println(sum);
    }
}
