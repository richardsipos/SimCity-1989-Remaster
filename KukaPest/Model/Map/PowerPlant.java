package Model.Map;

import Model.Helper.Coordinates;

public class PowerPlant extends ServiceZone{
    public PowerPlant(Coordinates coordinates) {
        super(4,4,30,3000, coordinates, 30);
    }
}
