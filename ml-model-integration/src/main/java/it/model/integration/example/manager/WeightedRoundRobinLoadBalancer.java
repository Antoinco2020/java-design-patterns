package it.model.integration.example.manager;

import it.model.integration.example.model.MLModel;

import java.util.List;

public class WeightedRoundRobinLoadBalancer {
    private final List<MLModel> models;
    private int currentIndex = -1;

    public WeightedRoundRobinLoadBalancer(List<MLModel> models) {
        this.models = models;
    }

    public MLModel getNextModel() {
        currentIndex = (currentIndex + 1) % models.size();
        return models.get(currentIndex);
    }
}
