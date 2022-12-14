package org.javalearning.thread;

import org.javalearning.util.CommonUtil;

public class HelloWorldThreadExample {
    private static String result = "";

    private static void hello(){
        CommonUtil.delay(700);
        result = result.concat("hello");
    }

    private static void world(){
        CommonUtil.delay(600);
        result = result.concat("world");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread helloWorld = new Thread(HelloWorldThreadExample::hello);
        Thread worldHello = new Thread(HelloWorldThreadExample::world);

        helloWorld.start();
        worldHello.start();

        helloWorld.join();
        worldHello.join();

        System.out.println("Result is : " + result);
    }
}
