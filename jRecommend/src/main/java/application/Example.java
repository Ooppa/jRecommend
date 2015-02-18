/*
 * Aineopintojen harjoitustyö: Tietorakenteet ja algoritmit
 * Helsingin yliopisto Tietojenkäsittelytieteen laitos
 * Ooppa 2015 - GNU General Public License, version 3.
 */
package application;

import generators.TestDataGenerator;

/**
 * Example of the algorithm in action
 *
 * @author Ooppa
 */
public class Example {

    private final TestDataGenerator data;

    /**
     * Creates a new Example to be used to try out the algorithm
     */
    public Example() {
        // Start by generating the test data.
        data = new TestDataGenerator(true, 1000);
        
        // THIS WILL BE CODED AGAIN WITH PROPER CLASS
        
        // new one is Algorithm algorithm = new Algorithm();

    }

    /**
     * Returns the data generated for the Example
     *
     * @return the data generated by TestDataGenerator
     */
    public TestDataGenerator getData() {
        return data;
    }

}
