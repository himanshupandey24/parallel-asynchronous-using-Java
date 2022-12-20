package org.javalearning.parallestreams;

import org.javalearning.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamPerformanceTest {

    ParallelStreamPerformance parallelStreamPerformance = new ParallelStreamPerformance();

    @Test
    void sum_using_intstream() {
        int sum = parallelStreamPerformance.sum_using_intstream(1000000, false);
        System.out.println("Sum : " + sum);

        assertEquals(1784293664, sum);
    }

    @Test
    void sum_using_intstream_parallel() {
        int sum = parallelStreamPerformance.sum_using_intstream(1000000, true);
        System.out.println("Sum : " + sum);

        assertEquals(1784293664, sum);
    }

    @Test
    void sum_using_list() {
        //given

        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when
        int sum = parallelStreamPerformance.sum_using_list(inputList, false);
        System.out.println("Sum : " + sum);


        assertEquals(1784293664, sum);
    }

    @Test
    void sum_using_list_parallel() {
        //given

        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when
        int sum = parallelStreamPerformance.sum_using_list(inputList, true);
        System.out.println("Sum : " + sum);


        assertEquals(1784293664, sum);
    }

    @Test
    void sum_using_iterate() {

        int sum = parallelStreamPerformance.sum_using_iterate(1000000, false);
        System.out.println("Sum : " + sum);
        assertEquals(1784293664, sum);
    }

    @Test
    void sum_using_iterate_parallel() {
        //given

        //when
        int sum = parallelStreamPerformance.sum_using_iterate(1000000, true);
        System.out.println("sum : "+ sum);

        //then
        assertEquals(1784293664, sum);
    }

}