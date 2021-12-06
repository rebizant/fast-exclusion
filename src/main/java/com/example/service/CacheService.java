package com.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CacheService<T> {

    private Map<String, T> CACHE = new HashMap<>();

    public Optional<T> getFromCache(String key) {
        return Optional.ofNullable(CACHE.get(key));
    }

    public void putInCache(String key, T value) {
        CACHE.put(key, value);
    }

}
