package org.javalearning.parallestreams;

import org.javalearning.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

//    @ParameterizedTest
//    @ValueSource(booleans = {false, true})
    @Test
    void arrayListSpliteratorExample(){

        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2,false);
        assertEquals(size, resultList.size());
    }

    @RepeatedTest(5)
    void arrayListSpliteratorExampleRepeatedTest(){


        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2,false);
        assertEquals(size, resultList.size());
    }

    @Test
    void arrayListSpliteratorParallelExample(){

        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2,true);
        assertEquals(size, resultList.size());
    }

    @RepeatedTest(5)
    void arrayListSpliteratorParallelExampleRepeatedTest(){

        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2,true);
        assertEquals(size, resultList.size());
    }

    @RepeatedTest(10)
    void multiplyIntStream(){
        int n = 1000000;

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(n,2,false);
        assertEquals(n, resultList.size());

    }

    @RepeatedTest(5)
    void multiplyIntStreamInParallel(){
        int n = 1000000;

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(n,2,true);
        assertEquals(n, resultList.size());

    }
}