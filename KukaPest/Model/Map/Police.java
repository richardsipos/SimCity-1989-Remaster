package Model.Map;

import Model.Helper.Coordinates;

public class Police extends Infrastructure {

    public Police(Coordinates coordinates) {
        super(2,4,20, 1500, coordinates, 15);
    }
}
