package de.bcxp.challenge.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVParserTest {

    private static final int EXPECTED_NUMBER_OF_COUNTRIES = 27;    
    private static final int EXPECTED_NUMBER_OF_COUNTRY_COLUMNS = 7;   // minus the id column
    private static final int EXPECTED_NUMBER_OF_DAYS = 30;    
    private static final int EXPECTED_NUMBER_OF_WEATHER_COLUMNS = 13; // minus the id column


    private IReader reader;
    private Map<String, Map<String,String>> countries;
    private Map<String, Map<String,String>> weather;

    @BeforeEach
    void setUp() {
        try {
            reader = new CSVParser("src/main/resources/de/bcxp/challenge/countries.csv");
            countries = reader.readTable();

            reader = new CSVParser("src/main/resources/de/bcxp/challenge/weather.csv");
            weather = reader.readTable();
        } catch (FileNotFoundException e) {
            fail("test file not found");
        } catch (IOException e) {
            fail("error during IO");
        }
    }


    @Test
    void testReadCSV() {
        assertEquals(EXPECTED_NUMBER_OF_COUNTRIES, countries.size(), "wrong number of countries");
        assertEquals(EXPECTED_NUMBER_OF_DAYS, weather.size(), "wrong number of days");

        for (Map<String, String> row : weather.values()) {
            assertEquals(EXPECTED_NUMBER_OF_WEATHER_COLUMNS, row.size(), "wrong number of columns");
        }

        for (Map<String, String> row : countries.values()) {
            assertEquals(EXPECTED_NUMBER_OF_COUNTRY_COLUMNS, row.size(), "wrong number of columns");
        }
    }
}
