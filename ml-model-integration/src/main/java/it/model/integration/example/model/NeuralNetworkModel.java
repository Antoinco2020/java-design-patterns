package it.model.integration.example.model;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class NeuralNetworkModel extends MLModel {
    private String modelPath;

    public NeuralNetworkModel(String modelPath, String modelVersion) {
        this.modelPath = modelPath;
        this.modelVersion = modelVersion;
    }

    @Override
    public void loadModel() {

        System.out.println("Model loaded from: " + modelPath);
    }

    @Override
    public Object predict(Object input) {
        return "Predicted result for input: " + input;
    }

    @Override
    public void updateModel(String modelPath) {
        this.modelPath = modelPath;
        loadModel();
    }

    @Override
    public void updateModel(String modelPath, String modelVersion) {
        this.modelPath = modelPath;
        this.modelVersion = modelVersion;
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
