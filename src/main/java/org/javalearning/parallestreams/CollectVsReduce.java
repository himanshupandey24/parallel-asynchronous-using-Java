package org.javalearning.parallestreams;

import org.javalearning.util.DataSet;
import org.javalearning.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CollectVsReduce {

    private static String collect(){
        List<String> list = DataSet.namesList();
        String result = list
                .parallelStream()
                .collect(Collectors.joining());

        return result;
    }

    private static String reduce(){
        List<String> list = DataSet.namesList();
        String result = list
                .parallelStream()
                .reduce("", (s1, s2) -> s1 + s2 );

        return result;
    }
    public static void main(String[] args) {
        LoggerUtil.log("collect : " + collect());
        LoggerUtil.log("reduce : " + reduce());
    }
}
