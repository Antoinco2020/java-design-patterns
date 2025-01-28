package it.model.integration.example.model;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class LinearRegressionModel extends MLModel {
    private String modelPath;

    public LinearRegressionModel(String modelPath, String modelVersion) {
        this.modelPath = modelPath;
        this.modelVersion = modelVersion;
    }

    @Override
    public void loadModel() {
        System.out.println("Linear Regression Model loaded from: " + modelPath + " with version: " + modelVersion);
    }

    @Override
    public Object predict(Object input) {
        System.out.println("Performing prediction using Linear Regression Model...");
        return "Prediction result for input: " + input;
    }

    @Override
    public void updateModel(String modelPath) {
        this.modelPath = modelPath;
        System.out.println("Linear Regression Model updated to path: " + modelPath);
        loadModel();
    }

    @Override
    public void updateModel(String modelPath, String modelVersion) {
        this.modelPath = modelPath;
        this.modelVersion = modelVersion;
        System.out.println("Linear Regression Model updated to path: " + modelPath + " with version: " + modelVersion);
        loadModel();
    }

    @Override
    public double getCpuUsage() {
        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return bean.getCpuLoad() * 100;
    }

    @Override
    public double getMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();
        long maxMemory = heapMemoryUsage.getMax();
        return (double) usedMemory / maxMemory * 100;
    }

    @Override
    public double getNetworkLatency() {
        throw new UnsupportedOperationException("Network Latency not supported for not distributed models.");
    }
}
