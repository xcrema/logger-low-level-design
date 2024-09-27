package models.sink;

import models.LogRotation;

import java.io.FileWriter;
import java.util.Map;

public class FileSink extends Sink {
    private FileWriter fileWriter;
    private final String filePath;
    private final LogRotation logRotation;

    public FileSink(Map<SinkDetails, String> sinkDetails) throws Exception {
        filePath = sinkDetails.get(SinkDetails.FILEPATH);
        fileWriter = new FileWriter(filePath, true);
        logRotation = new LogRotation(Integer.parseInt(sinkDetails.get(SinkDetails.MAX_FILESIZE)), filePath);
    }

    public void log(String message) {
        try {
            if (logRotation.rotate()) {
                fileWriter = new FileWriter(filePath, true);
            }
            fileWriter.write(message + "\n");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
