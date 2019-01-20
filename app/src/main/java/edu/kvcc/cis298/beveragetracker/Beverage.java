package edu.kvcc.cis298.beveragetracker;

public class Beverage {

    // Private member variables for the model
    private String mId;
    private String mName;
    private String mPack;
    private double mPrice;
    private boolean mActive;

    // Constructors
    public Beverage() {

    }

    public Beverage(String Id, String Name, String Pack, double Price, boolean Active) {
        mId = Id;
        mName = Name;
        mPack = Pack;
        mPrice = Price;
        mActive = Active;
    }

    // Getters / Setters
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPack() {
        return mPack;
    }

    public void setPack(String pack) {
        mPack = pack;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }
}
