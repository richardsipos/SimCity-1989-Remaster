package Model.Map;

import Model.Helper.Coordinates;

public class Stadium extends ServiceZone{
    public Stadium(Coordinates coordinates) {
        super(4,4,20,2000, coordinates, 20);
    }
}
