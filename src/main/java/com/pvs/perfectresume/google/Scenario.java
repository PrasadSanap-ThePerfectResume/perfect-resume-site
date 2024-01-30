package com.pvs.perfectresume.google;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scenario {
    public static void main(String[] args) {
        String[][] query = {{"ADD","1"},
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
        String[] ans=solution(query);
        System.out.println(ans);

    }

    private static String[] solution(String[][] queries) {
        String[] finalAns=new String[ queries.length];
        List outputCheck=new ArrayList<>();
        int i=0;
        // for (String[] str:queries) {
        //     System.out.println(str[0]);
        //     if(str[0].equals("ADD")){

        //     }
        // }
        for (String[] str:queries) {
            if(str[0].equals("ADD")){
                finalAns[i]="";
                outputCheck.add(str[1]);
            }else if(str[0].equals("EXISTS")){
                if(outputCheck.contains(str[1])){
                    finalAns[i]="true";
                }else{
                    finalAns[i]="false";
                }
            }else if(str[0].equals("REMOVE")){
                if(outputCheck.contains(str[1])){
                    outputCheck.remove(str[1]);
                    finalAns[i]="true";
                }else{
                    finalAns[i]="false";
                }
            }else{
                int j=Integer.parseInt(str[1]);
                boolean flag=false;
                Collections.sort(outputCheck);
                for(Object str1:outputCheck){
                    int k=Integer.parseInt(str1.toString());
                    if(k>j){
                        finalAns[i]=String.valueOf(k);
                        flag=false;
                        break;
                    }else{
                        flag=true;
                    }
                }
                if(flag){
                    finalAns[i]="";
                }

            }
            i++;
        }
        return finalAns;
    }
}

/*
String[] solution(String[][] queries) {
       String[] finalAns=new String[ queries.length];
        List<Integer> outputCheck=new ArrayList<>();
        List<Integer> outputCheck2=new ArrayList<>();
        int i=0;
        for (String[] str:queries) {
            if(str[0].equals("ADD")){
                finalAns[i]="";
                outputCheck.add(Integer.parseInt(str[1]));
            }else if(str[0].equals("EXISTS")){
                if(outputCheck.contains(Integer.parseInt(str[1]))){
                    finalAns[i]="true";
                }else{
                    finalAns[i]="false";
                }
            }else if(str[0].equals("REMOVE")){
                if(outputCheck.contains(Integer.parseInt(str[1]))){
                    int eleIndex= outputCheck.indexOf(Integer.parseInt(str[1]));
                    outputCheck.remove(eleIndex);
                    finalAns[i]="true";
                }else{
                    finalAns[i]="false";
                }
            }else{
                int j=Integer.parseInt(str[1]);
                boolean flag=false;
                outputCheck2=outputCheck;
                Collections.sort(outputCheck2);
                for(Object str1:outputCheck2){
                    int k=Integer.parseInt(str1.toString());
                    if(k>j){
                        finalAns[i]=String.valueOf(k);
                        flag=false;
                        break;
                    }else{
                        flag=true;
                    }
                }
                if(flag){
                    finalAns[i]="";
                }

            }
            i++;
        }
        return finalAns;
}

*/


/*
* queries:
[["ADD","2"],
 ["ADD","4"],
 ["ADD","9"],
 ["GET_NEXT","0"],
 ["GET_NEXT","1"],
 ["GET_NEXT","2"],
 ["GET_NEXT","3"],
 ["GET_NEXT","4"],
 ["GET_NEXT","9"]]

queries:
[["ADD","0"],
 ["ADD","1"],
 ["ADD","1"],
 ["ADD","11"],
 ["ADD","22"],
 ["ADD","3"],
 ["ADD","5"],
 ["GET_NEXT","0"],
 ["GET_NEXT","1"],
 ["REMOVE","1"],
 ["GET_NEXT","1"],
 ["ADD","0"],
 ["ADD","1"],
 ["ADD","2"],
 ["ADD","1"],
 ["GET_NEXT","1"],
 ["GET_NEXT","2"],
 ["GET_NEXT","3"],
 ["GET_NEXT","5"]]
Output:
["", "", "", "", "", "", "", "0", "1", "true", "1", "", "", "", "", "1", "11", "22", "5"]
Expected Output:
["", "", "", "", "", "", "", "1", "3", "true", "3", "", "", "", "", "2", "3", "5", "11"]


Input:
queries:
[["ADD","2"],
 ["ADD","4"],
 ["ADD","9"],
 ["GET_NEXT","0"],
 ["GET_NEXT","1"],
 ["GET_NEXT","2"],
 ["GET_NEXT","3"],
 ["GET_NEXT","4"],
 ["GET_NEXT","9"]]
Output:
["", "", "", "2", "4", "9", "", "", ""]
Expected Output:
["", "", "", "2", "2", "4", "4", "9", ""]


Input:
queries:
[["ADD","1"],
 ["ADD","2"],
 ["ADD","2"],
 ["ADD","4"],
 ["GET_NEXT","1"],
 ["GET_NEXT","2"],
 ["GET_NEXT","3"],
 ["GET_NEXT","4"],
 ["REMOVE","2"],
 ["GET_NEXT","1"],
 ["GET_NEXT","2"],
 ["GET_NEXT","3"],
 ["GET_NEXT","4"]]
Output:
["", "", "", "", "2", "2", "4", "", "true", "2", "4", "", ""]
Expected Output:
["", "", "", "", "2", "4", "4", "", "true", "2", "4", "4", ""]
* */
