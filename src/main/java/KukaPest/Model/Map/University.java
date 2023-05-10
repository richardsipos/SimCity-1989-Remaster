package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class University extends EduBuilding implements java.io.Serializable{
    public University(Coordinates coordinates) {
        super(4, 4,40,1500, coordinates, 15, 50);
    }
}
