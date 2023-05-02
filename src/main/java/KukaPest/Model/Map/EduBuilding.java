package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

abstract public class EduBuilding extends Infrastructure {
    public EduBuilding(int width, int height, int maxCapacity, int costOfBuilding, Coordinates coordinates, int upKeep, int electricityNeed) {
        super(width, height, maxCapacity,costOfBuilding, coordinates, upKeep, electricityNeed);
    }
}
