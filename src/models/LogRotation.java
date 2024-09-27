package models;

import java.io.File;
import java.util.UUID;

public class LogRotation {
    private final long maxSize;
    private final String filePath;

    public LogRotation(long maxSize, String filePath) {
        this.maxSize = maxSize;
        this.filePath = filePath;
    }

    public boolean rotate() throws Exception {
        File file = new File(filePath);
        if (file.length() > maxSize) {
            File backup = new File(filePath + UUID.randomUUID().toString());
            file.renameTo(backup);
            File newFile = new File(filePath);
            newFile.createNewFile();
            return true;
        }
        return false;
    }
}
