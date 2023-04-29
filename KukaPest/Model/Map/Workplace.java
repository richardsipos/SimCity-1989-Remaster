package Model.Map;

import Model.Helper.Coordinates;

public abstract class Workplace extends MainZone{
    public Workplace(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates, int upKeep, int electricityNeed) {
        super(width, height, maxCapacity, priceOfBuilding, coordinates, upKeep, electricityNeed);
    }
}
