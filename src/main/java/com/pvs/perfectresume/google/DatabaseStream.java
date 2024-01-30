package com.pvs.perfectresume.google;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseStream {
    public static void main(String[] args) {
        String[][] queries = /*{
                {"SET_OR_INC", "A","B", "5"},
                {"SET_OR_INC", "A", "B", "6"},
                {"GET", "A", "B"},
                {"GET", "A", "C"},
                {"DELETE", "A", "B"},
                {"DELETE", "A", "C"}
        };*/

        {
            {"SET_OR_INC", "A","BC", "1"},
            {"SET_OR_INC", "AB", "C", "2"},
            {"DELETE", "BC", "A"},
            {"GET", "BC", "A"},
            {"GET", "A", "BC"},
            {"GET", "AB", "C"},
            {"DELETE", "A", "BC"},
            {"DELETE", "A", "BC"},
            {"DELETE", "B", "AC"},
            {"DELETE", "A", "BC"},
            {"GET", "A", "BC"},
            {"GET", "AB", "C"}
        };
         //["1","2","false","","1","2","true","false","false","false","","2"]


        /*{
            {"DELETE", "key", "key"},
            {"DELETE", "key", "key2"},
            {"GET", "A", "B"},
            {"GET", "key", "key"},
            {"DELETE", "key", "key"},
            {"SET_OR_INC", "key","key", "12"},
            {"SET_OR_INC", "foo","bar", "906"},
            {"DELETE", "key", "bar"},
            {"DELETE", "key", "key2"},
            {"GET", "key", "key"},
            {"GET", "k", "ekey"},
            {"SET_OR_INC", "key","key", "60"},
            {"GET", "key", "key"},
        };*/
        String[] str=solution(queries);
    }

    private static String[] solution(String[][] queries) {
        long time=System.currentTimeMillis();
        System.out.println(time);
        String[] str=new String[queries.length];
        int i=0;
        HashMap<String,HashMap<String,Integer>> outerMap=new HashMap<>();
        HashMap<String,Integer> innerMap=new HashMap<>();
        for(String[] value:queries){
            String outerKey=value[1];
            String innerKey=value[2];
            switch(value[0]){
                case "SET_OR_INC":
                    //System.out.print(++i+"->"+value[0]+":"+value[1]+":"+value[2]+ ":"+value[3]+"\n");

                    if(innerMap.containsKey(innerKey)){
                        innerMap.put(innerKey,innerMap.get(innerKey)+Integer.parseInt(value[3]));
                    }else{
                        innerMap.put(innerKey,Integer.parseInt(value[3]));
                    }
                    str[i]=String.valueOf(innerMap.get(innerKey));
                    outerMap.put(outerKey, innerMap);
                    i++;
                    break;
                case "GET":
                    //System.out.print(++i+"->"+value[0]+":"+value[1]+":"+value[2]+ "\n");
                    if(outerMap.containsKey(outerKey)){
                        if(innerMap.containsKey(innerKey)){
                            str[i]=String.valueOf(innerMap.get(innerKey));
                        }else{
                            str[i]="";
                        }
                    }else {
                        str[i]="";
                    }
                    i++;
                    break;

                case "DELETE":
                    //System.out.print(++i+"->"+value[0]+":"+value[1]+":"+value[2]+ "\n");
                    if(outerMap.containsKey(outerKey)){
                        if(innerMap.containsKey(innerKey)){
                            innerMap.remove(innerKey);
                            str[i]="true";
                        }else{
                            str[i]="false";
                        }
                    }else {
                        str[i]="false";
                    }
                    i++;
                    break;
            }
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        System.out.println(System.currentTimeMillis());

        return str;
    }
}

