package it.model.integration.example.manager;

public class VersionComparator {
    public static int compare(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        for (int i = 0; i < Math.min(v1.length, v2.length); i++) {
            int diff = Integer.parseInt(v1[i]) - Integer.parseInt(v2[i]);
            if (diff != 0) {
                return diff;
            }
        }
        return v1.length - v2.length;
    }

    public static boolean isCompatible(String version1, String version2) {
        // Esempio: considera compatibile solo se la "major version" è uguale
        return version1.split("\\.")[0].equals(version2.split("\\.")[0]);
    }
}
