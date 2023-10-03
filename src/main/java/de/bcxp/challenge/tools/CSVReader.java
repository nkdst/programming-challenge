package de.bcxp.challenge.tools;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class CSVReader implements IReader{
    
    private String DELIMITER;
    private String path;

    public CSVReader(String path) throws IOException {
        this.path = path;
        detectDeliminter(path);
    }

    @Override
    public Map<String, Map<String, String>> readTable() throws FileNotFoundException, IOException {
        Map<String, Map<String, String>> records = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            String[] headers = line.split(DELIMITER);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);

                if (values.length != headers.length) {
                    System.out.println("record omitted, because of wrong format");
                    System.out.println(line);
                    continue;
                }

                Map<String, String> record = new HashMap<>(headers.length - 1);
                for (int col = 1; col < headers.length; col++) {
                    record.put(headers[col], values[col]);
                }
                records.put(values[0], record);
            }
            
        }
        return records;
    }

    private void detectDeliminter(String path) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            if (line.contains(";")) {
                DELIMITER = ";";
                // System.out.println("Detected delimiter: ;");
            } else if (line.contains(",")) {
                DELIMITER = ",";
                // System.out.println("Detected delimiter: ,");
            } else {
                throw new IllegalStateException("No delimiter detected");
            }
        }
    }

    public void setDELIMITER(String DELIMITER) {
        this.DELIMITER = DELIMITER;
    }

    public void setPath(String path) throws FileNotFoundException, IOException {
        this.path = path;
        detectDeliminter(path);
    }
}
