package de.bcxp.challenge;

import de.bcxp.challenge.logic.MainController;

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

        MainController controller = new MainController();
        controller.analyseData();
        
    }
}
