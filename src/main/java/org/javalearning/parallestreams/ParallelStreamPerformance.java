package org.javalearning.parallestreams;

import org.javalearning.util.CommonUtil;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamPerformance {

    public int sum_using_intstream(int count, boolean isParallel){
        CommonUtil.startTimer();

        IntStream intStream = IntStream.rangeClosed(0, count);

        if(isParallel)
            intStream.parallel();

        int sum = intStream.sum();

        CommonUtil.timeTaken();

        return sum;
    }

    public int sum_using_list(List<Integer> inputList, boolean isParallel){
        CommonUtil.startTimer();

        Stream<Integer> inputStream = inputList.stream();

        if(isParallel)
            inputStream.parallel();

        int sum = inputStream
                .mapToInt(Integer::intValue)
                .sum();

        CommonUtil.timeTaken();
        return sum;
    }

    public int sum_using_iterate(int n, boolean isParallel){
        CommonUtil.startTimer();
        Stream<Integer> integerStream = Stream.iterate(0,i -> i+1);

        if(isParallel)
            integerStream.parallel();

        int sum = integerStream
                .limit(n+1)
                .reduce(0, Integer::sum);

        CommonUtil.timeTaken();

        return sum;
    }


}
