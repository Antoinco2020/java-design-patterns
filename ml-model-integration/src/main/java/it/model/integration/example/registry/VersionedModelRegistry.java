package it.model.integration.example.registry;

import it.model.integration.example.model.MLModel;

import java.util.HashMap;
import java.util.Map;

public class VersionedModelRegistry {
    private Map<String, Map<String, MLModel>> registry;

    public VersionedModelRegistry() {
        this.registry = new HashMap<>();
    }

    public void registerModel(String modelName, String version, MLModel model) {
        registry.computeIfAbsent(modelName, k -> new HashMap<>()).put(version, model);
    }

    public MLModel getModel(String modelName, String version) {
        return registry.getOrDefault(modelName, new HashMap<>()).get(version);
    }

    public String getLatestVersion(String modelName) {
        return registry.getOrDefault(modelName, new HashMap<>())
                .keySet()
                .stream()
                .sorted()
                .reduce((first, second) -> second)
                .orElse(null);
    }
}
