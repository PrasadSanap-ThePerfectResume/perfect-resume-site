package com.pvs.perfectresume.google;

import java.util.HashMap;

public class HashMapHashKey {
    public static void main(String[] args){
        String[] queryType={"insert","get","insert","addToValue","addToValue","addToValue","insert","addToKey","get","insert"};
        int[][] query={{2,1},{2},{1,3},{-1},{0},{3},{4,-5},{3},{4},{1,1}};
        int sum = 0;
        int[] value;
        HashMap<Integer,Integer> hashMap=new HashMap<>();
        for(int i=0;i<queryType.length;i++){
            switch(queryType[i]){
                case "insert":
                    value=query[i];
                    hashMap.put(value[0],value[1]);
                    break;

                case "get":
                    value=query[i];
                    sum+=hashMap.get(value[0]);
                    break;

                case "addToValue":
                    value=query[i];
                    int newVal=value[0];
                    hashMap.forEach((key, value1) -> {
                            hashMap.put(key,value1 + newVal);
                    });
                    break;

                case "addToKey":
                    value=query[i];
                    int newKey=value[0];
                    hashMap.forEach((key, value1) -> {
                        hashMap.put(key+newKey,value1);
                    });
                    break;
            }
        }
        System.out.println(hashMap);
    }
}
