package com.hititcs.locationmanager.controller;
import com.hititcs.locationmanager.service.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    private final ImportService is;
    private final FileWatchService fws;
    public HealthController(ImportService is, FileWatchService fws) { this.is = is; this.fws = fws; }

    @GetMapping
    public Map<String, Object> get() {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "UP");
        res.put("import_timestamps", is.getImportTimestamps());
        return res;
    }
}