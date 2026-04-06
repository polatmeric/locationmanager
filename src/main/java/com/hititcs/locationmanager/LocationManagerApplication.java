package com.hititcs.locationmanager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LocationManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LocationManagerApplication.class, args);
    }
}