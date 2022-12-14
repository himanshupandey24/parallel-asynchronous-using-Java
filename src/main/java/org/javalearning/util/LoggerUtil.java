package org.javalearning.util;

public class LoggerUtil {
    public static void log(String message){
        System.out.println("[" + Thread.currentThread().getName() + " thread execution ] - " + message);
    }
}
