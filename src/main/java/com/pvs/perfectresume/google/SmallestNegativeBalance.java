package com.pvs.perfectresume.google;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SmallestNegativeBalance {

    public static void main(String[] args) {
        List<List<String>> debts = new ArrayList<>();
        List<String> values = new ArrayList<>();
        values.add("Alex");
        values.add("Black");
        values.add("2");
        debts.add(values);
        values = new ArrayList<>();
        values.add("Black");
        values.add("Alex");
        values.add("2");
        debts.add(values);
        values = new ArrayList<>();
        values.add("Casey");
        values.add("Alex");
        values.add("5");
        debts.add(values);
        values = new ArrayList<>();
        values.add("Black");
        values.add("Casey");
        values.add("7");
        debts.add(values);
        values = new ArrayList<>();
        values.add("Alex");
        values.add("Black");
        values.add("4");
        debts.add(values);
        values = new ArrayList<>();
        values.add("Alex");
        values.add("Casey");
        values.add("4");
        debts.add(values);
        HashMap<String,Integer> bmap = new HashMap<>();
        HashMap<String, Integer> lmap = new HashMap<>();
        int amt;
        for (List<String> list : debts) {
            amt = Integer.parseInt(list.get(2));
            if (bmap.containsKey(list.get(0))) {
                bmap.put(list.get(0),bmap.get(list.get(0))+ amt);
            } else {
                bmap.put(list.get(0), amt);
            }
            if (lmap.containsKey(list.get(1))) {
                lmap.put(list.get(1),lmap.get(list.get(1))+ amt);
            } else {
                lmap.put(list.get(1), amt);
            }
        }
        HashMap<String,Integer> itemMap=new HashMap<>();
        AtomicBoolean flag= new AtomicBoolean(true);
        List<Integer> list=new ArrayList<>();
        lmap.forEach((key, value) -> {
            if (value - bmap.get(key) < 0) {
                flag.set(false);
                itemMap.put(key, value - bmap.get(key));
                list.add(value - bmap.get(key));
            }

        });
        List<String> nameList=new ArrayList<>();
        if(flag.get()) {
            nameList.add("Nobody has a negative balance");
            System.out.println(nameList);
        }
        Collections.sort(list);
        System.out.println(list);
        itemMap.forEach((key, value)->{
            if(value==list.get(0)){
                nameList.add(key);
            }
        });
        Collections.sort(nameList);
        System.out.println(nameList);
    }
}
