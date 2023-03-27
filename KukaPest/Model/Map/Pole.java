package Model.Map;

import Model.Coordinates;

import java.awt.*;

public class Pole extends Constructable{

    public Pole(Coordinates coordinates, boolean hasElectricity, int cost, int timeToBuild, int upKeep) {
        super(coordinates, hasElectricity, cost, timeToBuild, upKeep);
    }
}
