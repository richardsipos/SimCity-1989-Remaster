package Model.Map;

import Model.Coordinates;

public class Constructable extends Tile{

    private int cost;
    private int timeToBuild;
    private int upKeep = 0;


    public Constructable(Coordinates coordinates, boolean hasElectricity, int cost, int timeToBuild, int upKeep) {
        super(coordinates, hasElectricity);
        this.cost = cost;
        this.timeToBuild = timeToBuild;
        this.upKeep = upKeep;
    }
}
