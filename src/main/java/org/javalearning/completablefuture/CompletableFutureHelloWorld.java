package org.javalearning.completablefuture;

import org.javalearning.service.HelloWorldService;
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
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept( result -> LoggerUtil.log("result : " + result))
                        .join(); // join call will block the main thread

        LoggerUtil.log("Done ! ");

    }
}
