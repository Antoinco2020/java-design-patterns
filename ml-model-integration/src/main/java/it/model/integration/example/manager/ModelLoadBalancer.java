package it.model.integration.example.manager;

import it.model.integration.example.model.MLModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ModelLoadBalancer {
    private List<MLModel> models;
    private AtomicInteger currentIndex;

    public ModelLoadBalancer(List<MLModel> models) {
        this.models = models;
        this.currentIndex = new AtomicInteger(0);
    }

    public MLModel getNextModel() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % models.size());
        return models.get(index);
    }
}
