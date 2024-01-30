package com.pvs.perfectresume.google;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaxFileBucket {
    public static void main(String[] args) {
        String[] commands={
                "goto bucketA",
                "create fileA",
                "create fileB",
                "create fileA",
                "goto bucketB",
                "goto bucketC",
                "create fileA",
                "create fileB",
                "create fileC",
        };
        String[] strArr;
        String key="";
        Set<String> fileSet = null;
        HashMap<String,Set<String>> fileMap=new HashMap<>();
        for(String str:commands) {
            strArr = str.split(" ");
            if (strArr[0].equals("goto")) {
                key = strArr[1];
                fileSet = new HashSet<>();
            }

            if (strArr[0].equals("create")) {
                fileSet.add(strArr[1]);
                fileMap.put(key, fileSet);
            }
        }

        int maxSize = 0;
        String largeKey="";
        for(Map.Entry<String,Set<String>> entry : fileMap.entrySet()){
            if(entry.getValue().size()>maxSize){
                maxSize=entry.getValue().size();
                largeKey= entry.getKey();
            }
        }
        System.out.println(largeKey);

    }
}
