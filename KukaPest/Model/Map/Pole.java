package Model.Map;

import java.awt.*;
import Model.*;

public class Pole extends Constructable{

    public Pole(Coordinates coordinates, boolean hasElectricity, Image currentImg, int cost, int timeToBuild) {
        super(coordinates, hasElectricity, currentImg, cost, timeToBuild);
    }
}
