package com.hititcs.locationmanager.service;
import org.springframework.stereotype.Service;
import java.io.File;
import java.time.*;
import java.util.*;

@Service
public class FileWatchService {
    public Map<String, Object> getFileMetadata(String path) {
        File f = new File(path);
        Map<String, Object> m = new HashMap<>();
        if (f.exists()) {
            m.put("lastModified", LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault()).toString());
            m.put("size", f.length());
        } else { m.put("error", "not found"); }
        return m;
    }
}