package de.bcxp.challenge.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import de.bcxp.challenge.tools.CSVParser;
import de.bcxp.challenge.tools.IReader;

public class MainController {

    private static Properties properties;

    private static final String MIN_TEMP = "MnT";
    private static final String MAX_TEMP = "MxT";
    
    private NumberFormat format;

    public MainController() {
       format = NumberFormat.getInstance(Locale.getDefault());
       initProperties();
    }
    
    public void analyseData() {
        try {
            String dayWithSmallestTempSpread = getDayWithSmallestTempSpread();
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);
            String countryWithHighestPopulationDensity = getCountryWithHighestPopDensity();
            System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
        } catch (IOException e) {
            System.out.println("Error while reading csv data");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("parsing error while reading csv data");
            e.printStackTrace();
        }
    }

    private void initProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("pc.properties")) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            throw(new IllegalStateException("properties file not found!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDayWithSmallestTempSpread() throws IOException, ParseException {
        
        IReader reader = new CSVParser(properties.getProperty("weather.path"));
        Map<String, Map<String, String>> weather = reader.readTable();

        double minTmpSpread = 0;
        String dayWithSmallestTempSpread = "";

        for (String key : weather.keySet()) {
            double maxT = format.parse(weather.get(key).get(MAX_TEMP)).doubleValue();
            double minT = format.parse(weather.get(key).get(MIN_TEMP)).doubleValue();
            double tmpSpread = maxT - minT;

            // System.out.printf("Day %3s : %6.2f %n", key, tmpSpread);

            if (tmpSpread > minTmpSpread) {
                minTmpSpread = tmpSpread;
                dayWithSmallestTempSpread = key;
            }
        }
        return dayWithSmallestTempSpread;
    }

    private String getCountryWithHighestPopDensity() throws IOException, ParseException {

        IReader reader = new CSVParser(properties.getProperty("countries.path"));
        Map<String, Map<String, String>> countries = reader.readTable();

        String countryWithHighestPopulationDensity = ""; 
        double maxPopDensity = 0;

        for (String key : countries.keySet()) {
            double population = format.parse(countries.get(key).get("Population")).doubleValue();
            double area = format.parse(countries.get(key).get("Area (km²)")).doubleValue();
            double popDensity = population / area;

            // System.out.printf("Country %15s : %6.2f %n", key, popDensity);

            if (popDensity > maxPopDensity) {
                maxPopDensity = popDensity;
                countryWithHighestPopulationDensity = key;
            }
        }
        return countryWithHighestPopulationDensity;
    }

}
