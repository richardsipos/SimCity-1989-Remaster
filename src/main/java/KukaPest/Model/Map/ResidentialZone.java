package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

public class ResidentialZone extends MainZone{
    private int level = 1;
    public ResidentialZone(Coordinates coordinates) {
        super(2,2,10, 500, coordinates, 0,5);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
