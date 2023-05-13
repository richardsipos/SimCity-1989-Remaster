package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class ServiceZone extends Workplace implements java.io.Serializable{
    public ServiceZone(Coordinates coordinates) {
        super(2,2,8,500, coordinates, 0, 20);
    }
}
