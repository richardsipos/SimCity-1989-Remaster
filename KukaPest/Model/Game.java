package Model;

import Model.Helper.Buildings;
import Model.Helper.Coordinates;
import Model.Helper.Zones;
import Model.Map.Tile;

public class Game {
    private int gameID;
    private City city;
    private int timeSpeed;

    public void stepGame(){
        // Called very often (eg. every second) from view.
        // Calls city
    }
    public Tile[][] getMap(){
        // Calls city getMap()
        return null;
    }

    public void setZone(Coordinates coords, Zones type){
        // Calls city setZone();
    }

    public boolean destroy(Coordinates coords){
        // Calls city destroy
        return false;
    }

    public String getZoneDetails(Coordinates coords){
        // returns City getZoneDetails() method
        return null;
    }

    public void build(Coordinates coords, Buildings type){

    }

    public void modifyTax(int level){
        // Calls city setTax() method
    }

    public void saveGame(){
        // Calls Database saveGame() method
    }

    public void loadGame(){
        // Calls Database loadGame() method
    }

    public boolean upgrade(Coordinates coords){
        // Calls city upgrade()
        return false;
    }

}
