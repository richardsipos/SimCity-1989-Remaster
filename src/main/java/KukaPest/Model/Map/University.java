package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Helper.EduLevel;

public class University extends EduBuilding{
    public University(Coordinates coordinates) {
        super(4, 4,80,1500, coordinates, 15, 50);
    }

    @Override
    protected EduLevel targetEduLevel() {
        return EduLevel.HIGH;
    }
}
