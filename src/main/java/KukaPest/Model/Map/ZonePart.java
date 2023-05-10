package KukaPest.Model.Map;

public class ZonePart extends Tile implements java.io.Serializable{

    public MainZone mainBuilding;

    public ZonePart(MainZone mainBuilding) {
        super();
        this.mainBuilding = mainBuilding;
    }

    public MainZone getMainBuilding() {
        return mainBuilding;
    }
}
