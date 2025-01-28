package it.model.integration.example.manager;

import java.util.HashMap;
import java.util.Map;

public class InferenceCache {
    private Map<Object, Object> cache;

    public InferenceCache() {
        this.cache = new HashMap<>();
    }

    public Object getCachedResult(Object input) {
        return cache.get(input);
    }

    public void addToCache(Object input, Object result) {
        cache.put(input, result);
    }

    public boolean isCached(Object input) {
        return cache.containsKey(input);
    }
}