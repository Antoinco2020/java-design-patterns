package it.model.integration.example.monitor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ModelMonitor {
    private final ConcurrentHashMap<String, List<Long>> inferenceTimesHistory = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicLong> totalInferenceTime = new ConcurrentHashMap<>();
    private final int historySize = 5;  // Numero di inferenze da considerare per la previsione

    // Registra un tempo di inferenza
    public void recordInferenceTime(String modelName, long timeMs) {
        inferenceTimesHistory.computeIfAbsent(modelName, k -> new LinkedList<>()).add(timeMs);
        if (inferenceTimesHistory.get(modelName).size() > historySize) {
            inferenceTimesHistory.get(modelName).remove(0);  // Mantieni solo gli ultimi 'historySize' tempi
        }
        totalInferenceTime.computeIfAbsent(modelName, k -> new AtomicLong()).addAndGet(timeMs);
    }

    // Calcola il tempo medio di inferenza (media dei tempi storici)
    public long getAverageInferenceTime(String modelName) {
        List<Long> history = inferenceTimesHistory.get(modelName);
        if (history == null || history.isEmpty()) {
            return 0;
        }

        long total = history.stream().mapToLong(Long::longValue).sum();
        return total / history.size();
    }

    // Previsione del tempo futuro (basata sulla media mobile degli ultimi 'historySize' tempi)
    public long predictFutureInferenceTime(String modelName) {
        List<Long> history = inferenceTimesHistory.get(modelName);
        if (history == null || history.isEmpty()) {
            return 0;
        }

        // Media dei tempi storici (media mobile)
        long total = history.stream().mapToLong(Long::longValue).sum();
        return total / history.size();
    }
}
