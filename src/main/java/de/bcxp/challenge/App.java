package de.bcxp.challenge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import de.bcxp.challenge.tools.CSVParser;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {


        // Your preparation code …

        // Properties
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("pc.properties")) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            throw(new IllegalStateException("file not found"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        CSVParser parser = new CSVParser();
        Map<String, Map<String, String>> weather;
        Map<String, Map<String, String>> countries;
        try {
            weather = parser.readCSV(properties.getProperty("weather.path"));
            countries = parser.readCSV(properties.getProperty("countries.path"));

            double minTmpSpread = 0;
            String dayWithSmallestTempSpread = "Someday";     // Your day analysis function call …
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            for (String key : weather.keySet()) {
                double maxT = format.parse(weather.get(key).get("MxT")).doubleValue();
                double minT = format.parse(weather.get(key).get("MnT")).doubleValue();
                double tmpSpread = maxT - minT;

                // System.out.printf("Day %3s : %6.2f %n", key, tmpSpread);

                if (tmpSpread > minTmpSpread) {
                    minTmpSpread = tmpSpread;
                    dayWithSmallestTempSpread = key;
                }
            }
            
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

            

            // Population countries
            String countryWithHighestPopulationDensity = "Some country"; // Your population density analysis function call …

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




            System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
