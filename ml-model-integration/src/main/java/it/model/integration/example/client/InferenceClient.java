package it.model.integration.example.client;

import it.model.integration.example.manager.ABTestManager;
import it.model.integration.example.manager.InferenceCache;
import it.model.integration.example.manager.ModelLoadBalancer;
import it.model.integration.example.model.NeuralNetworkModel;
import it.model.integration.example.monitor.ModelMonitor;
import it.model.integration.example.registry.VersionedModelRegistry;

import java.util.Arrays;

public class InferenceClient {
    public void start() {
        // Creating the registry for versioned models
        VersionedModelRegistry registry = new VersionedModelRegistry();

        // Creation and registration of models
        NeuralNetworkModel modelA_v1 = new NeuralNetworkModel("path/to/modelA_v1", "v1.0");
        NeuralNetworkModel modelA_v2 = new NeuralNetworkModel("path/to/modelA_v2", "v2.0");
        NeuralNetworkModel modelB = new NeuralNetworkModel("path/to/modelB", "v1.0");

        registry.registerModel("ModelA", modelA_v1.getModelVersion(), modelA_v1);
        registry.registerModel("ModelA", modelA_v1.getModelVersion(), modelA_v2);
        registry.registerModel("ModelB", modelA_v1.getModelVersion(), modelB);

        // Upload the most recent ModelA
        String latestVersion = registry.getLatestVersion("ModelA");
        NeuralNetworkModel latestModelA = (NeuralNetworkModel) registry.getModel("ModelA", latestVersion);
        latestModelA.loadModel();

        // Monitor and cache initialization
        ModelMonitor monitor = new ModelMonitor();
        InferenceCache cache = new InferenceCache();

        // Load balancing among multiple models
        ModelLoadBalancer loadBalancer = new ModelLoadBalancer(Arrays.asList(
                latestModelA,
                modelB
        ));

        // A/B testing between two models
        ABTestManager abTestManager = new ABTestManager(latestModelA, modelB);

        // Example of input for inference
        Object input = "Input 1";

        // Check if the result is cached
        if (cache.isCached(input)) {
            System.out.println("Cache Hit: " + cache.getCachedResult(input));
        } else {
            // Weather monitoring begins
            long startTime = System.currentTimeMillis();

            // Inference with load balancing
            NeuralNetworkModel model = (NeuralNetworkModel) loadBalancer.getNextModel();
            Object result = model.predict(input);

            // Calculate the inference time
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;

            // Detail logging via the monitor
            monitor.logInference(input, result, timeTaken);

            // Save the result in the cache
            cache.addToCache(input, result);

            System.out.println("Balanced Model Result: " + result);
        }

        // Inference with A/B testing
        Object inputABTest = "Input 2";
        Object abResult = abTestManager.predict(inputABTest);

        // A/B inference log
        monitor.logInference(inputABTest, abResult, 0);
    }
}
