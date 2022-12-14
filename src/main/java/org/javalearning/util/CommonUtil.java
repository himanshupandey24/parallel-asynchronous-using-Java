package org.javalearning.util;

import org.apache.commons.lang3.time.StopWatch;

import static java.lang.Thread.sleep;

public class CommonUtil {
    private static StopWatch stopWatch = new StopWatch();

    public static void delay(long delayMilliSeconds){
        try {
            sleep(delayMilliSeconds);
        }
        catch (Exception exception){
            LoggerUtil.log("Exception is : " + exception.getMessage());
        }
    }
}
