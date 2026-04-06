package com.hititcs.locationmanager.controller;
import com.hititcs.locationmanager.model.*;
import com.hititcs.locationmanager.service.LocationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ListController {
    private final LocationService service;
    public ListController(LocationService s) { this.service = s; }

    @GetMapping("/region-list")
    public List<Region> getR(@RequestParam(required=false) String isoCountry) { return service.listRegions(isoCountry); }
    
    @GetMapping("/airport-list")
    public List<Airport> getA(@RequestParam String isoCountry, @RequestParam String scheduledService) { 
        return service.listAirports(isoCountry, scheduledService); 
    }
}