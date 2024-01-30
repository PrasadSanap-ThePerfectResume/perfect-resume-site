package com.pvs.perfectresume.google;

public class AliceAndBob {
    public static void main(String[] args) {
        int[] numbers={1,4,5,5,6};
        int[] numbers1={1,3,3,1,5};
        String output="";
        boolean pairFound=false;
        for(int i=0;i<numbers.length;i++) {
            if (numbers[i] > 0) {
                for (int j = i + 1; j < numbers.length; j++) {
                    if (numbers[j] > 0) {
                        if (numbers[i] == numbers[j]) {
                            pairFound = true;
                            numbers[i]=-1;
                            numbers[j]=-1;
                            break;
                        }
                    }
                }

            }

            if (pairFound) {
                if (output.equals("")) {
                    output = "Alice";
                } else if (output.equals("Alice")) {
                    output = "Bob";
                } else {
                    output = "Alice";
                }
                pairFound=false;
            }
        }
        System.out.println(output);
    }
}
