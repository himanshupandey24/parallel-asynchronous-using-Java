package org.javalearning.parallestreams;

import java.util.List;

public class ReduceExample {
    public  int reduce_sum_ParallelStream(List<Integer> inputList){

        return inputList
                .parallelStream()
                .reduce(0, Integer::sum);
    }

    public  int reduce_multiply_parallelStream(List<Integer> inputList){
        return inputList
                .parallelStream()
                .reduce(1, (x,y)->x*y);
    }


}
