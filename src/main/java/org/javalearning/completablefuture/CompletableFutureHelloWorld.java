package org.javalearning.completablefuture;

import org.javalearning.service.HelloWorldService;
import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public String helloWorld_3_async_calls_log(){

        CommonUtil.startTimer();

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "Third Completable Future Call";
        });

        String helloWorld = hello
                .thenCombine(world, (h,w) -> { LoggerUtil.log("thenCombine h/w");
                    return h + w;
                })
                .thenCombine(completableFuture, (previous, current) -> { LoggerUtil.log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    LoggerUtil.log("thenApply");
                    return s.toUpperCase();
                })
                .join();

        CommonUtil.timeTaken();

        return helloWorld;
    }

    public String helloWorld_3_async_calls_customThreadPool(){

        CommonUtil.startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(this.helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(this.helloWorldService::world, executorService);
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "Third Completable Future Call";
        }, executorService);

        String helloWorld = hello
                .thenCombine(world, (h,w) -> { LoggerUtil.log("thenCombine h/w");
                    return h + w;
                })
                .thenCombine(completableFuture, (previous, current) -> { LoggerUtil.log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    LoggerUtil.log("thenApply");
                    return s.toUpperCase();
                })
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

    public CompletableFuture<String> helloWorld_thenCompose(){
        CommonUtil.startTimer();
        CompletableFuture<String> helloWorldFuture = CompletableFuture
                .supplyAsync(() -> this.helloWorldService.hello())
                .thenCompose((previous) -> this.helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);

        CommonUtil.timeTaken();

        return helloWorldFuture;
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
