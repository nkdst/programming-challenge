package de.bcxp.challenge.tools;

import java.io.IOException;
import java.util.Map;

public interface IReader {

    public Map<String, Map<String, String>> readTable() throws IOException;


}
