package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class Police extends Infrastructure implements java.io.Serializable{

    public Police(Coordinates coordinates) {
        super(4,2,20, 1500, coordinates, 15, 40);
    }
}
