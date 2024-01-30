package com.pvs.perfectresume.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class lowestInRange {
    public static void main(String[] args) {
        int[] numbers1={11,4,23,9,10};
        int[] nRange1={5,12};

        int[] numbers={14,81,74,77,96,36,43,9,67,50,10,55,89,25,29,62,84,92,24,12,23,15,8,85,33,73,31,91,98,69};
        int[] nRange={67,75};

        List<Integer> outputNumber=new ArrayList<>();
        for(int i:numbers){
            if(i<nRange[1] && i>nRange[0]){
                outputNumber.add(i);
            }
        }
        Collections.sort(outputNumber);
        System.out.println(outputNumber.get(0));
    }
}
