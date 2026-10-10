package com.islam.mobile.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Shares state between step definition classes within one scenario.
 * PicoContainer creates a fresh instance for every scenario, so no state leaks between tests.
 */
public class ScenarioContext {
    private final Map<String, Object> data = new HashMap<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (!data.containsKey(key)) {
            throw new IllegalStateException("Nothing stored in scenario context under: " + key);
        }
        return (T) data.get(key);
    }
}
