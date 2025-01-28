package it.model.integration.example.model;

public abstract class MLModel {
    protected String modelVersion;

    public abstract void loadModel();
    public abstract Object predict(Object input);
    public abstract void updateModel(String modelPath);
    public abstract void updateModel(String modelPath, String modelVersion);
    public abstract double getCpuUsage();
    public abstract double getMemoryUsage();
    public abstract double getNetworkLatency();
    public String getModelVersion() {
        return modelVersion;
    }
}
