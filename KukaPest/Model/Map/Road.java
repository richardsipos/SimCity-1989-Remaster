package Model.Map;

import Model.Coordinates;

import java.awt.*;

public class Road extends Constructable{

    public Road(Coordinates coordinates, boolean hasElectricity, int cost, int timeToBuild, int upKeep) {
        super(coordinates, hasElectricity, cost, timeToBuild, upKeep);
    }
}
