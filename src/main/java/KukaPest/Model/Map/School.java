package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Helper.EduLevel;

public class School extends EduBuilding implements java.io.Serializable {
    public School(Coordinates coordinates) {
        super(4, 2,5,1000,coordinates ,10, 30);
    }

    @Override
    protected EduLevel targetEduLevel() {
        return EduLevel.MID;
    }
}
