import config.LoggerConfig;
import models.LogLevel;
import models.sink.Sink;
import models.sink.SinkFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class Logger {
    private LoggerConfig loggerConfig;

    private Sink sink;

    private ExecutorService executor;

    private BlockingQueue<String> logQueue;

    public Logger(LoggerConfig loggerConfig) throws Exception {
        this.loggerConfig = loggerConfig;
        this.sink = SinkFactory.getSink(loggerConfig);
        if (loggerConfig.isMultiThreadModel() && loggerConfig.isAsyncWriteMode()) {
            logQueue = new LinkedBlockingQueue<>();
            executor = Executors.newSingleThreadExecutor();
            executor.submit(this::processLogs);
        }
    }

    public static Logger getInstance(LoggerConfig config) throws Exception {
        return new Logger(config);
    }

    public void log(String message, LogLevel level, String namespace) {
        String time = new SimpleDateFormat(loggerConfig.getTimeFormat()).format(new Date());
        String formattedMessage = String.format("%s [%s] %s %s", level, time, message, namespace);

        if (!loggerConfig.isAsyncWriteMode()) {
            sink.log(formattedMessage);
        } else {
            logQueue.offer(formattedMessage);
        }
    }

    private void processLogs() {
        try {
            while (true) {
                String logMessage = logQueue.take();
                sink.log(logMessage);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() throws InterruptedException {
        if (executor != null) {
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
    }

}
