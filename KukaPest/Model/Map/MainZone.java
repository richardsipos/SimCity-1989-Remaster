package Model.Map;

import Model.Citizen;

import java.util.ArrayList;

abstract public class MainZone extends Constructable {
    private final int width;
    private final int height;
    private int capacity;
    private int satisfactionBoost;
    private ArrayList<Citizen> citizens;


    public MainZone(int width, int height,int maxCapacity,int priceOfBuilding, int satisfactionBoost) {
        super(priceOfBuilding);
        this.width = width;
        this.height = height;
        this.capacity = maxCapacity;
        this.satisfactionBoost = 0;
        this.citizens = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxCapacity() {
        return capacity;
    }

    public int getCurrentCapacity() {
        return citizens.size();
    }
    public void addCitizen(Citizen c){
        this.citizens.add(c);
    }

    public int getSatisfactionBoost() {
        return this.satisfactionBoost;
    }

    public void setSatisfactionBoost(int value) {
         this.satisfactionBoost += value;
    }
    public void resetSatisfactionBoost() {
         this.satisfactionBoost = 0;
    }
}
