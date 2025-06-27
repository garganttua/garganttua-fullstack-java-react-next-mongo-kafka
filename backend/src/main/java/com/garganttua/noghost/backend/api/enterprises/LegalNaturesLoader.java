package com.garganttua.noghost.backend.api.enterprises;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LegalNaturesLoader {

    public static Map<Integer, String> legalNatures;

    static {
        legalNatures = loadCsvToMap("legalNatures.csv");
    }

    public static Map<Integer, String> loadCsvToMap(String fileName) {
        Map<Integer, String> resultMap = new HashMap<>();

        try (InputStream is = LegalNaturesLoader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0].trim());
                        String value = parts[1].trim();
                        resultMap.put(key, value);
                    } catch (NumberFormatException e) {
                        System.err.println("Clé non entière ignorée : " + parts[0]);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV : " + fileName, e);
        }

        return resultMap;
    }
}
