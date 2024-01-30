package com.pvs.perfectresume.google;

public class HealthDeltas {
    public static void main(String[] args){
        int initialHealth=12;
        int[] deltas={-4,-12,6,2};
        int currentHealth=initialHealth;
        for (int i:deltas) {
            if(currentHealth<0){
                currentHealth=0;
            }
            if(currentHealth>100){
                currentHealth=100;
            }
            currentHealth+=i;
        }
        System.out.println(currentHealth);
    }
}
