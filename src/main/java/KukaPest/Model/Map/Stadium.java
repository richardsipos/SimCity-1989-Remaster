package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class Stadium extends Infrastructure {
    public Stadium(Coordinates coordinates) {
        super(4,4,20,2000, coordinates, 20, 100);
    }
}
