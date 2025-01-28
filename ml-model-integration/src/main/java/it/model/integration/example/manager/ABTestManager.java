package it.model.integration.example.manager;

import it.model.integration.example.model.MLModel;

import java.util.Random;

public class ABTestManager {
    private MLModel modelA;
    private MLModel modelB;

    public ABTestManager(MLModel modelA, MLModel modelB) {
        this.modelA = modelA;
        this.modelB = modelB;
    }

    public Object predict(Object input) {
        Random random = new Random();
        if (random.nextBoolean()) {
            System.out.println("Using Model A");
            return modelA.predict(input);
        } else {
            System.out.println("Using Model B");
            return modelB.predict(input);
        }
    }
}
