package org.javalearning.parallestreams;

import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedListSpliteratorExample {
    public List<Integer> multiplyEachValue(LinkedList<Integer> inputList, int multiplyValue, boolean isParallel){

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
}
