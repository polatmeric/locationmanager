package com.hititcs.locationmanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImportService {
    private final JdbcTemplate jdbcTemplate;
    private final Map<String, LocalDateTime> importTimestamps = new ConcurrentHashMap<>();

    @Value("${import.batch-size:100}")
    private int batchSize;

    public ImportService(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Transactional
    public void importRegions(String filePath) throws IOException {
        String sql = "INSERT INTO regions (id, code, local_code, name, continent, iso_country, wikipedia_link, keywords) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        processFile(filePath, sql, 8);
        importTimestamps.put("regions", LocalDateTime.now());
    }

    @Transactional
    public void importAirports(String filePath) throws IOException {
        String sql = "INSERT INTO airports (id, ident, type, name, latitude_deg, longitude_deg, elevation_ft, continent, iso_country, iso_region, municipality, scheduled_service, icao_code, iata_code, gps_code, local_code, home_link, wikipedia_link, keywords) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        processFile(filePath, sql, 19);
        importTimestamps.put("airports", LocalDateTime.now());
    }

    public Map<String, LocalDateTime> getImportTimestamps() { return Collections.unmodifiableMap(importTimestamps); }

    private void processFile(String path, String sql, int cols) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); 
            List<Object[]> batch = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] raw = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                Object[] data = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    String val = (i < raw.length) ? raw[i].replaceAll("^\"|\"$", "").trim() : null;
                    if (val == null || val.isEmpty() || val.equalsIgnoreCase("null")) {
                        data[i] = null;
                    } else {
                        try {
                            if (i == 0) data[i] = Long.parseLong(val);
                            else if (cols == 19 && (i == 4 || i == 5)) data[i] = Double.parseDouble(val);
                            else if (cols == 19 && i == 6) data[i] = (int) Double.parseDouble(val);
                            else data[i] = val;
                        } catch (Exception e) { data[i] = null; }
                    }
                }
                batch.add(data);
                if (batch.size() >= batchSize) {
                    jdbcTemplate.batchUpdate(sql, batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) jdbcTemplate.batchUpdate(sql, batch);
        }
    }
}