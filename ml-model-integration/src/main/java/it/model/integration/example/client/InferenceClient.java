package it.model.integration.example.client;

import it.model.integration.example.manager.ABTestManager;
import it.model.integration.example.manager.InferenceCache;
import it.model.integration.example.manager.ModelLoadBalancer;
import it.model.integration.example.model.MLModel;
import it.model.integration.example.model.NeuralNetworkModel;
import it.model.integration.example.monitor.ModelMonitor;
import it.model.integration.example.registry.VersionedModelRegistry;

import java.util.Arrays;
import java.util.List;

public class InferenceClient {
    public void start() {
        // Creating the registry for versioned models
        VersionedModelRegistry registry = new VersionedModelRegistry();

        // Creation and registration of models
        NeuralNetworkModel modelA_v1 = new NeuralNetworkModel("path/to/modelA_v1", "1.0");
        NeuralNetworkModel modelA_v2 = new NeuralNetworkModel("path/to/modelA_v2", "2.0");
        NeuralNetworkModel modelB = new NeuralNetworkModel("path/to/modelB", "1.0");

        registry.registerModel("ModelA", modelA_v1.getModelVersion(), modelA_v1);
        registry.registerModel("ModelA", modelA_v2.getModelVersion(), modelA_v2);
        registry.registerModel("ModelB", modelB.getModelVersion(), modelB);

        // Upload the most recent ModelA
        String latestVersion = registry.getLatestCompatibleVersion("ModelA", "2.0");
        NeuralNetworkModel latestModelA = (NeuralNetworkModel) registry.getModel("ModelA", latestVersion);
        latestModelA.loadModel();

        // Monitor and cache initialization
        ModelMonitor monitor = new ModelMonitor();
        InferenceCache cache = new InferenceCache();

        // Create a list of models
        List<MLModel> models = Arrays.asList(latestModelA, modelB);

        // Balanced loading across multiple models
        ModelLoadBalancer loadBalancer = new ModelLoadBalancer(monitor);

        // A/B testing between the two models
        ABTestManager abTestManager = new ABTestManager(latestModelA, modelB);

        // Example of input for inference
        Object input = "Input 1";

        // Check if the result is cached
        if (cache.isCached(input)) {
            System.out.println("Cache Hit: " + cache.getCachedResult(input));
        } else {
            // Start time monitoring
            long startTime = System.currentTimeMillis();

            // Inference with load balancing
            MLModel selectedModel = loadBalancer.selectModel(models);  // Use prediction-based model selection
            Object result = selectedModel.predict(input);

            // Calculate the inference time
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;

            // Detailed log via monitor
            monitor.recordInferenceTime(selectedModel.getModelVersion(), timeTaken);

            // Monitor resource usage (assuming these methods are implemented in your models)
            try {
                System.out.println("CPU Usage: " + selectedModel.getCpuUsage() + "%");
                System.out.println("Memory Usage: " + selectedModel.getMemoryUsage() + " MB");
                System.out.println("Network Latency: " + selectedModel.getNetworkLatency() + " ms");
            }
            catch (UnsupportedOperationException e){
                System.out.println(e.getMessage());
            }


            // Save the result in the cache
            cache.addToCache(input, result);

            System.out.println("Balanced Model Result: " + result);
        }

        // Inference with A/B testing
        Object inputABTest = "Input 2";
        Object abResult = abTestManager.predict(inputABTest);

        // A/B inference log
        monitor.recordInferenceTime("ABTest", 0);  // Non calcoliamo il tempo per il test A/B

        // Shows the average inference time for ModelA and ModelB
        System.out.println("Average inference time for ModelA: " + monitor.getAverageInferenceTime("ModelA") + " ms");
        System.out.println("Average inference time for ModelB: " + monitor.getAverageInferenceTime("ModelB") + " ms");

        // Predicted future inference times (for future prediction-based load balancing)
        System.out.println("Predicted future inference time for ModelA: " + monitor.predictFutureInferenceTime("ModelA") + " ms");
        System.out.println("Predicted future inference time for ModelB: " + monitor.predictFutureInferenceTime("ModelB") + " ms");
    }
}
