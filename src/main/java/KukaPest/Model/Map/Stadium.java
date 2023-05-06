package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;

import java.util.HashMap;

public class Stadium extends Infrastructure {
    public Stadium(Coordinates coordinates) {
        super(4,4,20,2000, coordinates, 20, 100);
    }

    @Override
    public HashMap<String, String> getStats(){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("Fenntartási költség", "" + super.getUpKeep());
        return ret;
    }
}
