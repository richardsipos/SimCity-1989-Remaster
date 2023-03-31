package Model;

import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.Tile;
import java.util.ArrayList;

public class City {
    private String name;
    private ArrayList<Citizen> citizens = new ArrayList<>();
    private Tile[][] map;// = new Tile[][];

    public City(String cityName) {
        this.name = cityName;
        this.citizens = new ArrayList<>();
    }

    boolean build(Building toBuild, Coordinates coords){
        //TODO: implement method
        return false;
    }
    boolean canBeBuilt(Building toBuild, Coordinates coords){
        //TODO: implement method
        return false;
    }
}
