package org.javalearning.parallestreams;

import org.javalearning.util.LoggerUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelStreamResultOrder {

    public static List<Integer> listOrder(List<Integer> inputList){
        return inputList.parallelStream()
                .map(item -> item * 2)
                .collect(Collectors.toList());

    }

    public static Set<Integer> listOrder_Set(List<Integer> inputList){
        return inputList.parallelStream()
                .map(item -> item * 2)
                .collect(Collectors.toSet());

    }

    public static Set<Integer> setOrder(Set<Integer> inputList){
        return inputList.parallelStream()
                .map(item -> item * 2)
                .collect(Collectors.toSet());
    }

    public static List<Integer> setOrder_List(Set<Integer> inputList){
        return inputList.parallelStream()
                .map(item -> item * 2)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {

        List<Integer> inputList = List.of(1,2,3,4,5,6,7,8,9);
        Set<Integer> inputSet = Set.of(1,2,3,4,5,6,7,8,9);

        LoggerUtil.log("inputList : " + inputList);
        List<Integer> resultList = listOrder(inputList);
        LoggerUtil.log("resultList : " + resultList);

        System.out.println();

        LoggerUtil.log("inputList : " + inputList);
        Set<Integer> resultSet = listOrder_Set(inputList);
        LoggerUtil.log("resultSet : " + resultSet);

        System.out.println();

        LoggerUtil.log("inputSet : " + inputSet);
        List<Integer> resultList1 = setOrder_List(inputSet);
        LoggerUtil.log("resultList : " + resultList1);

        System.out.println();

        LoggerUtil.log("inputSet : " + inputSet);
        Set<Integer> resultSet1 = setOrder(inputSet);
        LoggerUtil.log("resultSet : " + resultSet1);

    }
}
