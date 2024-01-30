package com.pvs.perfectresume.google;

public class PairOfWords {
    public static void main(String[] args){
//        String[] words={"back","backdoor","gammon","backgammon","comeback","come","door"};
        String[] words={"cba","a","a","b","ba","ca"};
        int sum=0;
        String prev,next,newStr;
        int j;
        for(int i=0;i<words.length;i++){
            j=0;
            prev=words[i];
            while(j<words.length) {
                next = words[j];
                if (j != i) {
                    if (next.length() >= prev.length()) {
                        newStr = next.substring(next.length() - prev.length());
                    } else {
                        newStr = prev.substring(prev.length() - next.length());
                    }
                    if (newStr.equals(prev)) {
                        sum++;
                        System.out.println( sum +".MATCHED ::" + prev + "-" + next );
                    }
                }
                j++;
            }
        }

    }
}
