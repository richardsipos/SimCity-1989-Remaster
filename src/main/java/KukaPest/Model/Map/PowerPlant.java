package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;
import java.util.HashMap;

public class PowerPlant extends Infrastructure implements java.io.Serializable {
    private final int electricityProduction = 1000;
    public PowerPlant(Coordinates coordinates) {
        super(4,4,30,3000, coordinates, 30,0);
    }

    public int getElectricityProduction() {
        return this.electricityProduction;
    }

    @Override
    public HashMap<String, String> getStats(){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("Electricity generated", "" + this.electricityProduction);
        ret.put("Upkeep", "" + super.getUpKeep());
        return ret;
    }
}
