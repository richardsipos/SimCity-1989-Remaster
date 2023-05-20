package KukaPest.Model.Map;

import KukaPest.Model.Citizen;
import KukaPest.Model.Helper.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class MainZone extends Constructable implements java.io.Serializable{
    private final int width;
    private final int height;

    private final Coordinates coordinates;
    private int capacity;
    private int satisfactionBoost;
    private ArrayList<Citizen> citizens;
    private int electricityNeed;
    private boolean electricity = false;

    public MainZone(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates, int upKeep,int electricityNeed) {
        super(priceOfBuilding, upKeep);
        this.width = width;
        this.height = height;
        this.capacity = maxCapacity;
        this.satisfactionBoost = 0;
        this.citizens = new ArrayList<>();
        this.coordinates = coordinates;
        this.electricityNeed = electricityNeed;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    public boolean isElectricity() {
        return electricity;
    }

    public int getElectricityNeed() {
        return electricityNeed;
    }

    public HashMap<String, String> getStats(){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("Capacity", "" + capacity);
        ret.put("Satisfaction Boost", "" + satisfactionBoost);
        ret.put("People", "" + citizens.size());
        ret.put("Upkeep", "" + super.getUpKeep());
        return ret;
    }

    public void addCitizen(Citizen c){
        this.citizens.add(c);

    }
    public boolean removeCitizen(Citizen c){
        return this.citizens.remove(c);
    }

    /**
     * Get/set methods
     */
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

    public int getSatisfactionBoost() {
        return this.satisfactionBoost;
    }

    public int getSatisfaction(){
        if (citizens.size() == 0) return 0;
        int sum = 0;
        for (Citizen c : citizens) {
            sum += c.satisfaction();
        }
        return sum / citizens.size();
    }

    public void setSatisfactionBoost(int value) {
         this.satisfactionBoost += value;
    }
    public void resetSatisfactionBoost() {
         this.satisfactionBoost = 0;
    }

    public Coordinates getCoordinates() { return coordinates;}

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
