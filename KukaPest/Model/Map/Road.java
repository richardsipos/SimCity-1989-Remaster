package Model.Map;

import java.awt.*;
import Model.*;

public class Road extends Constructable{

    public Road(Coordinates coordinates, boolean hasElectricity, Image currentImg, int cost, int timeToBuild) {
        super(coordinates, hasElectricity, currentImg, cost, timeToBuild);
    }
}
