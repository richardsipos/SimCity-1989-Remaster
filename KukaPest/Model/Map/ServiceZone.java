package Model.Map;

import Model.Helper.Coordinates;

abstract public class ServiceZone extends MainZone{
    public ServiceZone(int width, int height,int maxCapacity,int priceOfBuilding, Coordinates coordinates) {
        super(width, height, maxCapacity, priceOfBuilding, coordinates);
    }
}
