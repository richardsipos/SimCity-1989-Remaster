package Model;

import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.Tile;

import java.util.UUID;

public class Game {
    private UUID gameID;
    private City city;
    private int timeSpeed;

    public Game(String cityName){
        gameID = UUID.randomUUID();
        city = new City(cityName);
        timeSpeed = 1;
    }

    public boolean build(Building toBuild, Coordinates coords){
        return city.build(toBuild, coords);
    }

    public void stepGame(){
        city.timePassed(timeSpeed);
    }

    public Tile[][] getMap(){
        return city.getMap();
    }
    public String getCityName(){
        return city.getName();
    }
    public int getPopulation(){
        return city.getPopulation();
    }
    public int getFunds(){
        return city.getFunds();
    }
}
