package com.hititcs.locationmanager.service;

import com.hititcs.locationmanager.model.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {
    private final JdbcTemplate jdbcTemplate;

    public LocationService(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Cacheable(value = "regions", key = "#isoCountry ?: 'ALL'")
    public List<Region> listRegions(String isoCountry) {
        if (isoCountry == null || isoCountry.isBlank()) {
            return jdbcTemplate.query("SELECT * FROM regions",new DataClassRowMapper<>(Region.class));
            //return jdbcTemplate.query("SELECT * FROM regions", new BeanPropertyRowMapper<>(Region.class));
        }
        return jdbcTemplate.query("SELECT * FROM regions WHERE iso_country = ?",new DataClassRowMapper<>(Region.class));
        //return jdbcTemplate.query("SELECT * FROM regions WHERE iso_country = ?", new BeanPropertyRowMapper<>(Region.class), isoCountry.toUpperCase());
    }

    @Cacheable(value = "airports", key = "#isoCountry + '-' + #scheduledService")
    public List<Airport> listAirports(String isoCountry, String scheduledService) {
        return jdbcTemplate.query("SELECT * FROM airports WHERE iso_country = ? AND scheduled_service = ?", 
                new DataClassRowMapper<>(Airport.class), isoCountry.toUpperCase(), scheduledService.toLowerCase());
    }

    @CacheEvict(value = {"regions", "airports"}, allEntries = true)
    public void clearCache() {}
}