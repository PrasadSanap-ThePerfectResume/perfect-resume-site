package com.pvs.perfectresume.google;

import java.util.HashSet;
import java.util.Set;

public class SentenceRepetition {
    public static void main(String[] args) {
//        String str="Dooddle moodle Pepper unsuccessfully";
        String str="yummmmy yummmy yummmy yummmy yummy";
        str=str.toLowerCase();
        System.out.println(str);
        String[] strArr=str.split(" ");
        int i,size;
        Set<Character> charSet=new HashSet<>();
        for(String word:strArr){
            for(char ch1:word.toCharArray()){
                i=0;
                size=charSet.size();
                for(char ch2:word.toCharArray()){
                    if(ch1==ch2){
                        i++;
                    }
                    if(i>=3){
                        charSet.add(ch1);
                        break;
                    }
                }
                if(charSet.size()>size){
                    break;
                }
            }
        }
        System.out.println("WORDS::"+charSet);

    }
}
