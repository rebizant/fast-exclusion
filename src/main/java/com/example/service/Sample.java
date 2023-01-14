package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Sample {
    
    public static void main(String[] args) {
        System.out.println("to jest jakis sample text");

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());

        IntStream.range(0, 100)
        .forEach(priorityQueue::add);

        List<Integer> result = new ArrayList<>();
        IntStream.range(0, 3)
        .forEach(value -> result.add(priorityQueue.poll()));


        System.out.println(Arrays.toString(result.toArray()));
    }
}
