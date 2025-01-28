package it.model.integration.example.manager;

import it.model.integration.example.model.MLModel;
import it.model.integration.example.monitor.ModelMonitor;

import java.util.List;

public class ModelLoadBalancer {
    private final ModelMonitor monitor;
    private final double cpuThreshold = 80.0; // CPU threshold above which a model is ignored
    private final double memoryThreshold = 80.0; // Memory threshold

    public ModelLoadBalancer(ModelMonitor monitor) {
        this.monitor = monitor;
    }

    public MLModel selectModel(List<MLModel> models) {
        // Calculate score and filter models above thresholds
        return models.stream()
                .filter(model -> model.getCpuUsage() < cpuThreshold)
                .filter(model -> model.getMemoryUsage() < memoryThreshold)
                .min((m1, m2) -> {
                    long avgTime1 = monitor.predictFutureInferenceTime(m1.getModelVersion());
                    long avgTime2 = monitor.predictFutureInferenceTime(m2.getModelVersion());

                    return Long.compare(avgTime1, avgTime2);
                })
                .orElse(models.get(0));  // If there are no models, the first one returns
    }
}
