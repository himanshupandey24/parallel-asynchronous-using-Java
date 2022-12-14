package org.javalearning.forkjoin;

import org.javalearning.util.CommonUtil;
import org.javalearning.util.DataSet;
import org.javalearning.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class StringTransformExample {

    public static void main(String[] args) {
        CommonUtil.stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();

        LoggerUtil.log("names : " + names);

        names.forEach(name -> resultList.add(addNameAndTransform(name)));
        CommonUtil.stopWatch.stop();
        LoggerUtil.log("names with length : " + resultList);
        LoggerUtil.log("total time taken : " + CommonUtil.stopWatch.getTime());

    }

    private static String addNameAndTransform(String name){
        CommonUtil.delay(500);
        return "Name : " + name + " - Length : " + name.length();
    }
}
