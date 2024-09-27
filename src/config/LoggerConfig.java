package config;

import models.sink.SinkDetails;
import models.sink.SinkType;

import java.util.Map;

public class LoggerConfig {

    private final String timeFormat;

    private final SinkType sinkType;

    private final Map<SinkDetails, String> sinkDetails;

    private final boolean multiThreadModel;

    private final boolean asyncWriteMode;

    public LoggerConfig(String timeFormat, SinkType sinkType, Map<SinkDetails, String> sinkDetails, boolean multiThreadModel, boolean asyncWriteMode) {
        this.timeFormat = timeFormat;
        this.sinkType = sinkType;
        this.sinkDetails = sinkDetails;
        this.multiThreadModel = multiThreadModel;
        this.asyncWriteMode = asyncWriteMode;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public SinkType getSinkType() {
        return sinkType;
    }

    public Map<SinkDetails, String> getSinkDetails() {
        return sinkDetails;
    }

    public boolean isMultiThreadModel() {
        return multiThreadModel;
    }

    public boolean isAsyncWriteMode() {
        return asyncWriteMode;
    }
}
