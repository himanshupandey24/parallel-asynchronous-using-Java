package org.javalearning.parallestreams;

import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayListSpliteratorExample {
    public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue, boolean isParallel){

        CommonUtil.startTimer();

        Stream<Integer> integerStream = inputList.stream();

        if(isParallel)
            integerStream.parallel();

        List<Integer> resultList = integerStream
                .map(item -> item * multiplyValue)
                .collect(Collectors.toList());

        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();

        LoggerUtil.log("Completed");
        return resultList;
    }

    public List<Integer> multiplyEachValue(int n, int multiplyValue, boolean isParallel){

        CommonUtil.startTimer();
        IntStream intStream = IntStream.range(0,n);

        if(isParallel)
            intStream.parallel();

        List<Integer> resultList = intStream
                .map(item -> item * multiplyValue)
                .boxed()
                .collect(Collectors.toList());

        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();
        LoggerUtil.log("Completed");
        return resultList;
    }
}
