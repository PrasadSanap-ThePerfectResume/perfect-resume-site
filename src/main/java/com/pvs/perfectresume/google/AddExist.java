package com.pvs.perfectresume.google;

import org.hibernate.mapping.Set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class AddExist {
    public static void main(String[] args){
        String[][] queries ={{"ADD","1"},
                {"ADD","2"},
                {"ADD","2"},
                {"ADD","4"},
                {"GET_NEXT","1"},
                {"GET_NEXT","2"},
                {"GET_NEXT","3"},
                {"GET_NEXT","4"},
                {"REMOVE","2"},
                {"GET_NEXT","1"},
                {"GET_NEXT","2"},
                {"GET_NEXT","3"},
                {"GET_NEXT","4"}};
        String[] ans=new String[queries.length];
        ArrayList<String> numbers= new ArrayList<>();
        int i=0;
        for(String[] arr:queries){
           if(arr[0].equals("ADD")){
              numbers.add(arr[1]);
              ans[i]="";
              i++;
           }else if(arr[0].equals("EXISTS")) {
               if (numbers.contains(arr[1])) {
                   ans[i] = "true";
               } else {
                   ans[i] = "false";
               }
               i++;
           }
           else if(arr[0].equals("REMOVE")){
               if (numbers.contains(arr[1])) {
                   int indexOf=numbers.indexOf(arr[1]);
                   numbers.set(indexOf,"");
                   ans[i] = "true";
                   i++;
               } else {
                   ans[i] = "false";
                   i++;
               }
           } else if(arr[0].equals("GET_NEXT")){
               if (numbers.contains(arr[1])) {
                   int indexOf=numbers.indexOf(arr[1]);
                   ans[i] =  numbers.get(indexOf);
                   i++;
               } else {
                   ans[i] = "";
                   i++;
               }
           }
        }
        //{"", "", "", "", "2", "4", "4", "", "true", "2", "4", "4", ""}
        System.out.println(ans);
    }
}
