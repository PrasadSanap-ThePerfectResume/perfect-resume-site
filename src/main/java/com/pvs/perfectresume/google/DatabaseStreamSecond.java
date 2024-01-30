package com.pvs.perfectresume.google;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class DatabaseStreamSecond {

    public static void main(String[] args) {
        String[][] queries={{"SCAN","10","dept4"},
                {"GET","15","dept4","floors"},
                {"SCAN_BY_PREFIX","20","dept4","flo"},
                {"SET","22","dept4","floors","8"},
                {"SET","23","dept4","flower","230"},
                {"GET","25","dept4","floors"},
                {"SCAN_BY_PREFIX","29","dept4","flo"},
                {"SET","31","dept4","floors","10"},
                {"SCAN_BY_PREFIX","35","dept4","flo"},
                {"SCAN_BY_PREFIX","38","dept4","flow"}};
        //{"", "", "", "", "", "8", "floors(8), flower(230)", "", "floors(10), flower(230)", "flower(230)"}
        String[] output=solution(queries);
        System.out.println(output);
    }

    static String[] solution(String[][] queries) {


        String[] output=new String[queries.length];
        int i=0;
        String str;
        HashMap<String,Integer> innerMap = new HashMap<>();
        HashMap<String,HashMap<String,Integer>> outerMap = new HashMap<>();
        for(String[] value:queries){
            String outerKey =value[2];
            String innerKey="";
            if(!"SCAN".equals(value[0])) {
                innerKey = value[3];
            }
            switch(value[0]){
                case "SET":
                    innerMap.put(innerKey,Integer.parseInt(value[4]));
                    outerMap.put(outerKey,innerMap);
                    output[i]="";
                    i++;
                    break;
                case "COMPARE_AND_SET":
                    if(outerMap.containsKey(outerKey)){
                        if(innerMap.containsKey(innerKey)){
                            if(innerMap.get(outerKey)==Integer.parseInt(value[4])){
                                innerMap.put(innerKey,Integer.parseInt(value[5]));
                                outerMap.put(outerKey,innerMap);
                                output[i]="true";
                            }else{
                                output[i]="false";
                            }
                        }else{
                            output[i]="false";
                        }
                    }else{
                        output[i]="false";
                    }
                    i++;
                    break;
                case "COMPARE_AND_DELETE":
                    if(outerMap.containsKey(outerKey)){
                        if(innerMap.containsKey(innerKey)){
                            if(innerMap.get(outerKey)==Integer.parseInt(value[4])){
                                innerMap.remove(innerKey);
                                outerMap.put(outerKey,innerMap);
                                output[i]="true";
                            }else{
                                output[i]="false";
                            }
                        }else{
                            output[i]="false";
                        }
                    }else{
                        output[i]="false";
                    }
                    i++;
                    break;
                case "GET":
                    if(outerMap.containsKey(outerKey)) {
                        if (innerMap.containsKey(innerKey)) {
                            output[i] = String.valueOf(innerMap.get(innerKey));
                        } else{
                            output[i]="";
                        }
                    }else{
                        output[i]="";
                    }
                    i++;
                    break;
                case "SCAN":
                    if(outerMap.containsKey(outerKey)){
                        str=outerMap.get(outerKey).toString();
                        str=str.replaceAll("=","(")
                                .replaceAll(",","),")
                                .replaceAll("}",")")
                                .replaceAll("\\{","");
                        output[i]= str;
                        System.out.println(output[i]);
                    }else{
                        output[i]="";
                    }
                    i++;
                    break;
                case "SCAN_BY_PREFIX":
                    if(outerMap.containsKey(outerKey)){
                        String prefix=value[3];
                        HashMap<String,Integer> sampleMap= outerMap.get(outerKey);
                        List sample=sampleMap.entrySet().stream()
                                .filter(entry->entry.getKey().startsWith(prefix))
                                .collect(Collectors.toList());
                        str=sample.toString();
                        str=str.replaceAll("=","(")
                                .replaceAll(",","),")
                                .replaceAll("]",")")
                                .replaceAll("\\[","");
                        output[i]= str;
                        System.out.println(output[i]);
                    }else{
                        output[i]="";
                    }
                    i++;
                    break;
            }

        }

        return output;

    }
}
