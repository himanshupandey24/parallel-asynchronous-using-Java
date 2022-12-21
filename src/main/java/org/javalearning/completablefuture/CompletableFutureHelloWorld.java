package org.javalearning.completablefuture;

import org.javalearning.service.HelloWorldService;
import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {

    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld())
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize(){
        return CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld())
                .thenApply(String::toUpperCase)
                .thenApply( s -> s.length() + " - " + s);
    }

    public String helloWorld_multiple_async_calls(){

        CommonUtil.startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());

        String helloWorld = hello
                .thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();

        CommonUtil.timeTaken();

        return helloWorld;
    }


    public String helloWorld_3_async_calls(){

        CommonUtil.startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "Third Completable Future Call";
        });

        String helloWorld = hello
                .thenCombine(world, (h,w) -> h + w)
                .thenCombine(completableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        CommonUtil.timeTaken();

        return helloWorld;
    }


    public String helloWorld_4_async_calls(){

        CommonUtil.startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Third Completable Future Call";
        });

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Bye!";
        });


        String helloWorld = hello
                .thenCombine(world, (h,w) -> h + w)
                .thenCombine(completableFuture, (previous, current) -> previous + current)
                .thenCombine(completableFuture1, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        CommonUtil.timeTaken();

        return helloWorld;
    }
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept( result -> LoggerUtil.log("result : " + result))
                        .join(); // join call will block the main thread

        LoggerUtil.log("Done ! ");

    }
}
