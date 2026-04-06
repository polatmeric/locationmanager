package com.hititcs.locationmanager.controller;
import com.hititcs.locationmanager.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/import")
public class ImportController {
    private final ImportService importService;
    private final LocationService locationService;
    public ImportController(ImportService is, LocationService ls) { this.importService = is; this.locationService = ls; }

    @PostMapping("/regions")
    public String impR(@RequestParam String path) throws Exception { 
        importService.importRegions(path); 
        locationService.clearCache(); 
        return "OK"; 
    }
    @PostMapping("/airports")
    public String impA(@RequestParam String path) throws Exception { 
        importService.importAirports(path); 
        locationService.clearCache(); 
        return "OK"; 
    }
}