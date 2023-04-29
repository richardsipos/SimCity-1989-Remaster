package Model.Map;

import Model.Helper.Coordinates;

abstract public class Infrastructure extends MainZone{
    public Infrastructure(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates, int upKeep, int electricityNeed) {
        super(width, height, maxCapacity, priceOfBuilding, coordinates, upKeep, electricityNeed);
    }
}
