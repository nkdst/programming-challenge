package de.bcxp.challenge.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVParser {
    
    private static final String COMMA_DELIMITER = "[,;]";

    public Map<String, Map<String, String>> readCSV(String path) {
        Map<String, Map<String, String>> records = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String[] headers = br.readLine().split(COMMA_DELIMITER);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                Map<String, String> record = new HashMap<>(headers.length - 1);

                if (values.length != headers.length) {
                    System.out.println("record omitted, because of wrong format");
                    continue;
                }

                for (int col = 1; col < headers.length; col++) {
                    record.put(headers[col], values[col]);
                }
                records.put(values[0], record);
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }


        return records;
    }



}
