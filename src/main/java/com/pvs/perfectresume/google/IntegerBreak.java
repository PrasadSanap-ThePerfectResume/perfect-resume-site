package com.pvs.perfectresume.google;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IntegerBreak {
    public static void main(String[] args) {
        int number=15;
        System.out.println(helper(number-1,number));

    }

    static int helper(int n, int total){
        if(n<=1)
            return 1;
        int notpick=helper(n-1,total);
        int pick=0;
        if(total>=n)
            pick=n*helper(n,total-n);
        return Math.max(pick,notpick);
    }
}
