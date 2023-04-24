package Model.Map;

import Model.Helper.Coordinates;

abstract public class EduBuilding extends Infrastructure {
    public EduBuilding(int width, int height, int maxCapacity, int costOfBuilding, Coordinates coordinates, int upKeep) {
        super(width, height, maxCapacity,costOfBuilding, coordinates, upKeep);
    }
}
