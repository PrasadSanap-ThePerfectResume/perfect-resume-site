package com.pvs.perfectresume.google;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TuringArrayNumberPos {
    public static void main(String[] args) {
        int[] arr1={40,10,20,30};
        int[] ar1={100,100,100};
        int[] arr={37,12,28,9,100,56,80,5,12};
        int[] arr2= arr.clone();
        int[] output=new int[arr.length];
        int repeat=0;
        Arrays.sort(arr);
        for(int i:arr){
            System.out.print(i + ",");
        }
        System.out.println();
        for(int i:arr2){
            System.out.print(i + ",");
        }
        int pos=1;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr2.length;j++){
                if(arr2[i]==arr[j]){
                    output[i]=pos;
                    pos=1;
                    break;
                }
                pos++;
            }
        }
        System.out.println();
        for(int i:output){
            System.out.print(i + ",");
        }
    }
}
