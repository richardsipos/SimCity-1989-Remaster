package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;
import java.util.HashMap;

public abstract class Workplace extends MainZone implements java.io.Serializable{
    private int level = 1;
    public Workplace(int width, int height, int maxCapacity, int priceOfBuilding, Coordinates coordinates, int upKeep, int electricityNeed) {
        super(width, height, maxCapacity, priceOfBuilding, coordinates, upKeep, electricityNeed);
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
        ret.put("Capacity", "" + super.getMaxCapacity());
        ret.put("Workers", "" + super.getCurrentCapacity());
        ret.put("Level", "" + "" + level);
        ret.put("Upkeep", "" + super.getUpKeep());
        ret.put("Satisfaction", "" + super.getSatisfaction());
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
