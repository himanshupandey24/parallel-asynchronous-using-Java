package org.javalearning.parallestreams;

import org.javalearning.util.CommonUtil;
import org.javalearning.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransformWithParallelStream(){
        //given
        List<String> inputList = DataSet.namesList();

        //when
        CommonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.stringTransformWithParallelStream(inputList);
        CommonUtil.timeTaken();

        //then
        assertEquals(4,resultList.size());

        resultList.forEach(name -> assertTrue(name.contains("-")));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void stringTransformWithSequentialAndParallel(boolean isParallel){

        List<String> inputList = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.stringTransformWithSequentialAndParallel(inputList,isParallel);
        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();

        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));

    }

    @Test
    void string_toLowerCase() {
        List<String> inputList = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.string_toLowerCase(inputList);
        CommonUtil.timeTaken();

        assertEquals(4, resultList.size());
    }
}