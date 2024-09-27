import config.LoggerConfig;
import models.LogLevel;
import models.sink.SinkDetails;
import models.sink.SinkType;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<SinkDetails, String> fileDetails = new HashMap<>();
        fileDetails.put(SinkDetails.FILEPATH, "/Users/achyuttripathi/Downloads/LoggerImplementation/LoggerImplementation/info.log");
        fileDetails.put(SinkDetails.MAX_FILESIZE, "1024");

        LoggerConfig multiAsyncFileConfig = new LoggerConfig("dd:mm:yyyy hh:mm:ss", SinkType.FILE, fileDetails, true, true);
        Logger asyncLogger = Logger.getInstance(multiAsyncFileConfig);

        Runnable runnable1 = () ->
        {
            for (int i = 0; i < 10; i++) {
                asyncLogger.log(i + "log message from thread " + Thread.currentThread().getName(), LogLevel.INFO, "AppNamespace");
            }
        };

        Runnable runnable2 = () ->
        {
            for (int i = 0; i < 10; i++) {
                asyncLogger.log(i + "log message from thread " + Thread.currentThread().getName(), LogLevel.INFO, "AppNamespace");
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncLogger.log("No user found for the phone number", LogLevel.INFO, "AppNamespace");

        LoggerConfig singleSyncConsoleConfig = new LoggerConfig("dd:mm:yyyy hh:mm:ss", SinkType.CONSOLE, null, false, false);
        Logger logger = Logger.getInstance(singleSyncConsoleConfig);
        logger.log("Null pointer exception in the line 10", LogLevel.ERROR, "AppNamespace");

        asyncLogger.shutdown();
    }
}