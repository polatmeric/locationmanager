package com.hititcs.locationmanager.model;
public record Region(Long id, String code, String localCode, String name, 
                     String continent, String isoCountry, String wikipediaLink, String keywords) {}