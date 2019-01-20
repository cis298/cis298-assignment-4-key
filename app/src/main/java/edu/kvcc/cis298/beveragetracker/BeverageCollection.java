package edu.kvcc.cis298.beveragetracker;

import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BeverageCollection {

    //Static variable that represents this class
    private static BeverageCollection sBeverageCollection;

    //List to store all of the beverages
    private List<Beverage> mBeverages;

    // Boolean for whether the data has been loaded once
    private boolean dataLoadedOnce;

    //public static method to get the single instance of this class
    static BeverageCollection get() {
        //If the collection is null
        if (sBeverageCollection == null) {
            //make a new one
            sBeverageCollection = new BeverageCollection();
        }
        //regardless of whether it was just made or not, return the instance
        return sBeverageCollection;
    }

    //Private constructor to create a new BeverageCollection
    private BeverageCollection() {
        //Make a new list to hold the beverages
        mBeverages = new ArrayList<>();
        // Initialize dataLoadedOnce to False
        dataLoadedOnce = false;
    }

    //Getters
    List<Beverage> getBeverages() {
        return mBeverages;
    }

    public Beverage getBeverage(String Id) {
        for (Beverage beverage : mBeverages) {
            if (beverage.getId().equals(Id)) {
                return beverage;
            }
        }
        return null;
    }

    // Getter to see if the list is empty
    public boolean isEmpty() {
        return mBeverages.isEmpty();
    }

    // Getter to see if the data has been loaded once.
    public boolean isDataLoadedOnce() {
        return dataLoadedOnce;
    }

    //Method to load the beverage list from a CSV file
    void loadBeverageList(InputStream inputStream) {

        //Define a scanner
        try (Scanner scanner = new Scanner(inputStream)) {

            //While the scanner has another line to read
            while (scanner.hasNextLine()) {

                //Get the next line and split it into parts
                String line = scanner.nextLine();
                String parts[] = line.split(",");

                //Assign each part to a local var
                String id = parts[0];
                String name = parts[1];
                String pack = parts[2];

                //setup some vars for doing parsing
                double price = Double.parseDouble(parts[3]);
                boolean active = ((parts[4].equals("True")));

                //Add the beverage to the list
                mBeverages.add(new Beverage(id, name, pack, price, active));
            }

            // Data read in, so set the dataLoadedOnce flag to true.
            dataLoadedOnce = true;

            //catch any errors that occur and finally close the scanner
        } catch (Exception e) {
            Log.e("Read CSV", e.toString());
        }
    }
}
