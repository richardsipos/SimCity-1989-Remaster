package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

abstract public class Infrastructure extends MainZone implements java.io.Serializable {
    public Infrastructure(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates, int upKeep, int electricityNeed) {
        super(width, height, maxCapacity, priceOfBuilding, coordinates, upKeep, electricityNeed);
    }
}
