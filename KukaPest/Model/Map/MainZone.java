package Model.Map;

import Model.Citizen;
import Model.Helper.Coordinates;

import java.util.ArrayList;

abstract public class MainZone extends Constructable {
    private final int width;
    private final int height;

    private final Coordinates coordinates;
    private int capacity;
    private ArrayList<Citizen> citizens;




    public MainZone(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates) {
        super(priceOfBuilding);
        this.width = width;
        this.height = height;
        this.capacity = maxCapacity;
        this.citizens = new ArrayList<>();
        this.coordinates = coordinates;
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

    public Coordinates getCoordinates() { return coordinates;}
}
