package KukaPest.Model.Map;

import KukaPest.Model.Helper.Coordinates;
import java.util.HashMap;

public class Police extends Infrastructure implements java.io.Serializable
    public Police(Coordinates coordinates) {
        super(4,2,20, 1500, coordinates, 15, 40);
    }

    @Override
    public HashMap<String, String> getStats(){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("Fenntartási költség", "" + super.getUpKeep());
        return ret;
    }
}
