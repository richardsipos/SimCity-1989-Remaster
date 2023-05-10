package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class School extends EduBuilding implements java.io.Serializable{
    public School(Coordinates coordinates) {
        super(4, 2,30,1000,coordinates ,10, 30);
    }
}
