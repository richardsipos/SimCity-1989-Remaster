package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class PowerPlant extends Infrastructure {
    private int electricityProduction = 300;
    public PowerPlant(Coordinates coordinates) {
        super(4,4,30,3000, coordinates, 30,0);
    }

    public int getElectricityProduction() {
        return electricityProduction;
    }
}
