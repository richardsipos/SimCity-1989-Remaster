package Model.Map;

import Model.Helper.Coordinates;

abstract public class EduBuilding extends ServiceZone{
    public EduBuilding(int width, int height, int maxCapacity, int costOfBuilding, Coordinates coordinates) {
        super(width, height, maxCapacity,costOfBuilding, coordinates);
    }
}
