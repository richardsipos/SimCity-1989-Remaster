package Model.Map;

import Model.Coordinates;

import java.awt.*;
import Model.*;

public class Pole extends Constructable{

    public Pole(Coordinates coordinates, boolean hasElectricity, int cost, int timeToBuild, int upKeep) {
        super(coordinates, hasElectricity, cost, timeToBuild, upKeep);
    }
}
