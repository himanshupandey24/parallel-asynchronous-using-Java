package org.javalearning.completablefuture;

import org.javalearning.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {

        //given

        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();

        //then
        completableFuture.thenAccept(s -> assertEquals("HELLO WORLD", s)).join();

    }

    @Test
    void helloWorld_withSize() {
        //given

        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_withSize();
        completableFuture.thenAccept(s -> assertEquals("11 - HELLO WORLD", s)).join();
    }

    @Test
    void helloWorld_multiple_async_calls(){
        String result = completableFutureHelloWorld.helloWorld_multiple_async_calls();
        assertEquals("HELLOWORLD", result);
    }

    @Test
    void helloWorld_3_async_calls(){
        String result = completableFutureHelloWorld.helloWorld_3_async_calls();
        assertEquals("HELLOWORLDTHIRD COMPLETABLE FUTURE CALL", result);
    }

    @Test
    void helloWorld_3_async_calls_log(){
        String result = completableFutureHelloWorld.helloWorld_3_async_calls_log();
        assertEquals("HELLOWORLDTHIRD COMPLETABLE FUTURE CALL", result);
    }

    @Test
    void helloWorld_3_async_calls_log_async(){
        String result = completableFutureHelloWorld.helloWorld_3_async_calls_log_async();
        assertEquals("HELLOWORLDTHIRD COMPLETABLE FUTURE CALL", result);
    }

    @Test
    void helloWorld_3_async_calls_customThreadPool(){
        String result = completableFutureHelloWorld.helloWorld_3_async_calls_customThreadPool();
        assertEquals("HELLOWORLDTHIRD COMPLETABLE FUTURE CALL", result);
    }

    @Test
    void helloWorld_3_async_calls_customThreadPool_async(){
        String result = completableFutureHelloWorld.helloWorld_3_async_calls_customThreadPool_async();
        assertEquals("HELLOWORLDTHIRD COMPLETABLE FUTURE CALL", result);
    }
    @Test
    void helloWorld_4_async_calls(){
        String result = completableFutureHelloWorld.helloWorld_4_async_calls();
        assertEquals("HELLOWORLD THIRD COMPLETABLE FUTURE CALL BYE!", result);
    }

    @Test
    void helloWorld_thenCompose(){
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_thenCompose();

        completableFuture.thenAccept(s -> assertEquals("HELLO WORLD!", s)).join();
    }
}