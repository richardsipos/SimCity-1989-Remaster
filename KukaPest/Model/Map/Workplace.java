package Model.Map;

import Model.Helper.Coordinates;

public abstract class Workplace extends MainZone{
    private int level = 1;
    public Workplace(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates, int upKeep) {
        super(width, height, maxCapacity, priceOfBuilding, coordinates, upKeep);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
