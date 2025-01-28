package it.model.integration.example.model;

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
}
