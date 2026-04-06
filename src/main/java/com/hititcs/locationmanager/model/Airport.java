package com.hititcs.locationmanager.model;
public record Airport(Long id, String ident, String type, String name, 
                      Double latitudeDeg, Double longitudeDeg, Integer elevationFt, 
                      String continent, String isoCountry, String isoRegion, 
                      String municipality, String scheduledService, String icaoCode, 
                      String iataCode, String gpsCode, String localCode, 
                      String homeLink, String wikipediaLink, String keywords) {}