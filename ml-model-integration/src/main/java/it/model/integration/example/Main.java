package it.model.integration.example;

import it.model.integration.example.client.InferenceClient;

public class Main {
    public static void main(String[] args) {

        InferenceClient client = new InferenceClient();
        client.start();
    }
}