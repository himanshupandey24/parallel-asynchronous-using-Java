package org.javalearning.forkjoin;

import lombok.extern.java.Log;
import org.javalearning.util.CommonUtil;
import org.javalearning.util.DataSet;
import org.javalearning.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    @Override

    /**
     * Recursively split the list and runs each half as a ForkJoinTask
     * Right way of using Fork/Join Task
     */
    protected List<String> compute() {


        //Base Condition
        if(inputList.size() <= 1){
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(transform(name)));
            return resultList;
        }

        int midPoint = inputList.size()/2;

        //left side of the list, asynchronously arranges this task in the deque
        ForkJoinTask<List<String>> leftInputList  = new ForkJoinUsingRecursion(inputList.subList(0,midPoint)).fork();

        inputList = inputList.subList(midPoint, inputList.size());

        List<String> rightResult = compute(); // recursion happens

        List<String> leftResult = leftInputList.join();

        LoggerUtil.log("leftResult : " + leftResult);

        leftResult.addAll(rightResult);

        return leftResult;
    }

    public static void main(String[] args) {

        CommonUtil.stopWatch.start();

        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(DataSet.namesList());

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> resultList = forkJoinPool.invoke(forkJoinUsingRecursion);

        CommonUtil.stopWatch.stop();

        LoggerUtil.log("Final Result : " + resultList);
        LoggerUtil.log("Total Time Taken : " + CommonUtil.stopWatch.getTime());

    }

    private String transform(String name){
        CommonUtil.delay(500);
        return name.length() + " - " + name;
    }
}
