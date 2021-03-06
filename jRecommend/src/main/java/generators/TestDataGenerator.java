/*
 * Aineopintojen harjoitustyö: Tietorakenteet ja algoritmit
 * Helsingin yliopisto Tietojenkäsittelytieteen laitos
 * Ooppa 2015 - GNU General Public License, version 3.
 */
package generators;

import domain.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates example data for testing out the algorithm.
 *
 * @author Ooppa
 */
public class TestDataGenerator {

    private final Random random;
    private final boolean debug;
    private final long startingTime;

    private HashMap<Long, User> users;
    private HashMap<Long, Item> items;
    private HashMap<Long, Category> categories;
    private HashMap<Long, Quality> qualities;
    private HashMap<Long, Rating> ratings;

    private static final Logger logger = Logger.getLogger(TestDataGenerator.class.getName());

    /**
     * Creates a new TestDataGenerator with no debug and scale of 1000.
     */
    public TestDataGenerator() {
        this(false, 500);
    }

    /**
     * Creates a new TestDataGenerator
     *
     * @param debug print out debug messages while generating the data
     * @param scale scales the amount of test data generated
     */
    public TestDataGenerator(boolean debug, int scale) {

        // Scale should not be too small
        if(scale<300) {
            logger.log(Level.WARNING, "Scale for TestDataGenerator was too "
                    +"small, going to use the default value of 1000.");
            scale = 500;
        }

        // Scale should not be too big
        if(scale>10000) {
            logger.log(Level.WARNING, "Scale for TestDataGenerator was too "
                    +"big, going to use the default value of 1000.");
            scale = 500;
        }

        // Debugging fields
        this.startingTime = System.nanoTime();
        this.debug = debug;

        // Random() class used to randomize some testdata
        this.random = new Random();

        // Starting to populate the fields
        populateFields(scale);

        if(debug) {
            System.out.println(debugTimestamp()+": Creation process is now done."
                    +" Overall it took "+TimeUnit.SECONDS.convert(
                            (System.nanoTime()-startingTime), TimeUnit.NANOSECONDS)
                    +" seconds.");
        }
    }

    /**
     * Returns a list of generated users
     *
     * @return users
     *
     * @see User
     */
    public HashMap<Long, User> getUsers() {
        return users;
    }

    /**
     * Returns a list of generated items
     *
     * @return items
     *
     * @see Item
     */
    public HashMap<Long, Item> getItems() {
        return items;
    }

    /**
     * Returns a list of generated qualities
     *
     * @return qualities
     *
     * @see Quality
     */
    public HashMap<Long, Quality> getQualities() {
        return qualities;
    }

    /**
     * Returns a list of generated categories
     *
     * @return categories
     *
     * @see Category
     */
    public HashMap<Long, Category> getCategories() {
        return categories;
    }

    /**
     * Returns a list of generated ratings
     *
     * @return ratings
     *
     * @see Rating
     */
    public HashMap<Long, Rating> getRatings() {
        return ratings;
    }

    /**
     * Returns the amount of users generated
     *
     * @return amount of users
     */
    public int getAmountOfUsers() {
        return users.size();
    }

    /**
     * Returns the amount of items generated
     *
     * @return amount of items
     */
    public int getAmountOfItems() {
        return items.size();
    }

    /**
     * Returns the amount of qualities generated
     *
     * @return amount of qualities
     */
    public int getAmountOfQualities() {
        return qualities.size();
    }

    /**
     * Returns the amount of categories generated
     *
     * @return amount of categories
     */
    public int getAmountOfCategories() {
        return categories.size();
    }

    /**
     * Returns the amount of ratings generated
     *
     * @return amount of ratings
     */
    public int getAmountOfRatings() {
        return ratings.size();
    }

    /*
     * Populates data fields according to scale
     */
    private void populateFields(int scale) {
        populateUsers(Math.round(scale*250));
        populateItems(Math.round(scale*100));
        populateQualities((int) Math.round((double) scale*2.5));
        populateCategories((int) Math.round((double) scale/10));

        assingQualitiesToItems();
        assingCategoriesToItems();

        createRatings();
    }

    /*
     * Populates the class field "users" with users
     */
    private void populateUsers(int amount) {
        if(debug) {
            System.out.println(debugTimestamp()+": Creating "+amount+" users.");
        }

        users = new HashMap<>(amount);

        for(int i = 0; i<amount; i++) {
            User user = new User(i, "Firstname Lastname "+i);
            users.put(new Long(i), user);
        }

    }

    /*
     * Populates the class field "items" with items
     */
    private void populateItems(int amount) {
        if(debug) {
            System.out.println(debugTimestamp()+": Creating "+amount+" items.");
        }

        items = new HashMap<>(amount);

        for(int i = 0; i<amount; i++) {
            // Item number as name
            Item item = new Item(i, "Item number: "+i);

            // Timestamp as description
            item.setDescription("created at "+debugTimestamp());

            items.put(new Long(i), item);
        }
    }

    /*
     * Populates the class field "qualities" with qualities
     */
    private void populateQualities(int amount) {
        if(debug) {
            System.out.println(debugTimestamp()+": Creating "+amount+" qualities.");
        }

        qualities = new HashMap<>(amount);

        for(int i = 0; i<amount; i++) {
            Quality quality = new Quality(i, "Qualitys number: "+i);
            quality.setImportance(randomImportanceValue());
            quality.setDescription(getUUID());
            qualities.put(new Long(i), quality);
        }
    }

    /*
     * Populates the class field "categories" with categories
     */
    private void populateCategories(int amount) {
        if(debug) {
            System.out.println(debugTimestamp()+": Creating "+amount+" categories.");
        }

        categories = new HashMap<>(amount);

        for(int i = 0; i<amount; i++) {
            Category category = new Category(i, "Category number: "+i);
            category.setDescription(getUUID());
            categories.put(new Long(i), category);
        }
    }

    /*
     * Assingns qualities to items
     */
    private void assingQualitiesToItems() {
        if(debug) {
            System.out.println(debugTimestamp()+": Giving items their qualities (5-15 qualities per item).");
        }

        // For each item
        for(Map.Entry<Long, Item> entrySet : items.entrySet()) {
            Item item = entrySet.getValue();

            // Each item has from 5 to 15 qualities, discarding possible duplicates
            for(int i = 0; i<randomInteger(5, 15); i++) {
                Quality randomQuality = randomQuality();

                randomQuality.getItems().add(item);
                item.getQualities().add(randomQuality);

            }

        }

    }

    /*
     * Assings categories to items
     */
    private void assingCategoriesToItems() {
        if(debug) {
            System.out.println(debugTimestamp()+": Giving items (every item has a category) their categories.");
        }

        for(Map.Entry<Long, Item> entrySet : items.entrySet()) {
            Long key = entrySet.getKey();
            Item item = entrySet.getValue();

            int randomCategoryId = randomInteger(1, categories.size()-1);
            Category category = categories.get((long) randomCategoryId);

            item.getCategories().add(category);
            category.addElement(item);
        }

    }

    /*
     * Creates and assigns ratings to items and users
     */
    private void createRatings() {
        ratings = new HashMap<>();

        int ratingId = 0;

        if(debug) {
            System.out.println(debugTimestamp()+": Giving items ratings from users (0-65 per user).");
        }

        for(Map.Entry<Long, User> entrySet : users.entrySet()) {
            Long key = entrySet.getKey();
            User user = entrySet.getValue();

            int amountToRate = this.randomInteger(0, 65);
            for(int i = 0; i<amountToRate; i++) {
                // We pull random integer to state the index of the rated item
                int indexOfItemToRate = this.randomInteger(0, items.size()-1);

                Item item = items.get((long) indexOfItemToRate);
                Rating rating = new Rating(ratingId, user, item, randomStar());

                // If item has already been rated by this user it will not be added
                item.getRatings().add(rating);
                user.getRatings().add(rating);
                ratings.put(rating.getId(), rating);

                ratingId++;
            }

        }

        if(debug) {
            System.out.println(debugTimestamp()+": Finished giving "+ratings.size()+" ratings from users.");
        }

    }

    /*
     * Creates a random integer between min and max
     */
    private int randomInteger(int min, int max) {
        return random.nextInt((max-min)+1)+min;
    }

    /*
     * Provides a random importance value for quality generation
     */
    private Value randomImportanceValue() {
        Value[] values = Value.values();
        return values[randomInteger(0, values.length-1)];
    }

    /*
     * Provides a random quality
     */
    private Quality randomQuality() {
        return qualities.get((long) randomInteger(0, qualities.size()-1));
    }

    /*
     * Provides random star for rating generation
     */
    private Star randomStar() {
        Star[] stars = Star.values();
        return stars[randomInteger(0, stars.length-1)];
    }

    /*
     * Returns a UUID as a String for filler content in examples
     */
    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    /*
     * Gives out the amount of milliseconds elapsed from the start of the
     * generation
     */
    private String debugTimestamp() {
        return TimeUnit.MILLISECONDS.convert((System.nanoTime()-startingTime), TimeUnit.NANOSECONDS)+"ms";
    }

}
