package it.model.integration.example.monitor;

public class ModelMonitor {
    public void logInference(Object input, Object result, long timeTaken) {
        //put your monitoring system
        System.out.println("Input: " + input);
        System.out.println("Result: " + result);
        System.out.println("Inference Time: " + timeTaken + "ms");
    }
}
