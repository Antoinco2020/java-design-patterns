package it.model.integration.example.model;

import com.sun.management.OperatingSystemMXBean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;

public class DistributedMLModel extends MLModel {
    private String endpoint;

    public DistributedMLModel(String endpoint, String modelVersion) {
        this.endpoint = endpoint;
        this.modelVersion = modelVersion;
    }

    @Override
    public void loadModel() {
        System.out.println("Distributed model endpoint: " + endpoint + " with version: " + modelVersion);
    }

    @Override
    public Object predict(Object input) {
        // Simulates a call to a remote server
        System.out.println("Sending prediction request to: " + endpoint);
        return "Remote prediction result for input: " + input;
    }

    @Override
    public void updateModel(String modelPath) {
        throw new UnsupportedOperationException("Update not supported for distributed models.");
    }

    @Override
    public void updateModel(String modelPath, String modelVersion) {
        throw new UnsupportedOperationException("Update not supported for distributed models.");
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
        int timeout = 5000;
        try {
            InetAddress inet = InetAddress.getByName(endpoint);
            long start = System.currentTimeMillis();
            boolean reachable = inet.isReachable(timeout);
            long end = System.currentTimeMillis();

            if (reachable) {
                return end - start;
            } else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
