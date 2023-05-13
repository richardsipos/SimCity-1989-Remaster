package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;
import java.util.HashMap;

public class ResidentialZone extends MainZone implements java.io.Serializable{
    private int level = 1;
    public ResidentialZone(Coordinates coordinates) {
        super(2,2,10, 500, coordinates, 0,20);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public HashMap<String, String> getStats(){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("Kapacitás", "" + super.getMaxCapacity());
        ret.put("Lakosok", "" + super.getCurrentCapacity());
        ret.put("Szint", "" + "" + level);
        ret.put("Fenntartási költség", "" + super.getUpKeep());
        return ret;
    }

    @Override
    public int getElectricityNeed() {
        return switch(level){
            case 1 -> 20;
            case 2 -> 60;
            case 3 -> 120;
            default -> 180;
        };
    }
}
