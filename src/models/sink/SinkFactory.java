package models.sink;

import config.LoggerConfig;

public class SinkFactory {

    public static Sink getSink(LoggerConfig loggerConfig) throws Exception {
        switch (loggerConfig.getSinkType()) {
            case SinkType.FILE:
                return new FileSink(loggerConfig.getSinkDetails());
            case SinkType.CONSOLE:
                return new ConsoleSink();
            default:
                throw new Exception("Invalid sink type");
        }
    }
}
