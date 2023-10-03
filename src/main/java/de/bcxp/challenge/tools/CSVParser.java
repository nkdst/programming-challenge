package de.bcxp.challenge.tools;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVParser {
    
    private String DELIMITER;

    public Map<String, Map<String, String>> readCSV(String path) throws FileNotFoundException, IOException {

        Map<String, Map<String, String>> records = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            detectDeliminter(line);
            String[] headers = line.split(DELIMITER);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                Map<String, String> record = new HashMap<>(headers.length - 1);

                if (values.length != headers.length) {
                    System.out.println("record omitted, because of wrong format");
                    System.out.println(line);
                    continue;
                }

                for (int col = 1; col < headers.length; col++) {
                    record.put(headers[col], values[col]);
                }
                records.put(values[0], record);
            }
            
        }
        return records;
    }

    private void detectDeliminter(String line) {
        if (line.contains(";")) {
            DELIMITER = ";";
            System.out.println("Detected delimiter: ;");
        } else if (line.contains(",")) {
            DELIMITER = ",";
            System.out.println("Detected delimiter: ,");
        } else {
            throw new IllegalStateException("No delimiter detected");
        }
    }



}
