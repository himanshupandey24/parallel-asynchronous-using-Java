package org.javalearning.service;

import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

public class HelloWorldService {
    public String helloWorld(){
        CommonUtil.delay(1000);
        LoggerUtil.log("Inside HelloWorld");
        return "hello world";
    }
    public String hello(){
        CommonUtil.delay(1000);
        LoggerUtil.log("Inside hello");
        return "hello";
    }

    public String world(){
        CommonUtil.delay(1000);
        LoggerUtil.log("Inside World");
        return "world";
    }

    public CompletableFuture<String> worldFuture(String input){
        return CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(3000);
            return input + " world!";
        });
    }
}
