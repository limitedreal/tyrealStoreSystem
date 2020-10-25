package com.tianlb.myTest.list;

import org.junit.jupiter.api.Test;

import java.util.*;

public class test {
    @Test
    public void test(){
        List<Integer> ints = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) ints.add(random.nextInt(1000));

        //natural sorting using Collections class
        Collections.sort(ints);
        System.out.println("Natural Sorting: "+ints);

        //My custom sorting, reverse order
        ints.sort((o1,o2) -> {return (o2-o1);});

    }
}
