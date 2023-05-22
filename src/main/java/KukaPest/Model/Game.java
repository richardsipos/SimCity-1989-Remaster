package KukaPest.Model;

import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Map.ResidentialZone;
import KukaPest.Model.Map.Tile;

import java.util.ArrayList;
import java.util.UUID;

public class Game implements java.io.Serializable {
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

    public boolean destroy(Coordinates coords){
        return city.destroy(coords);
    }
    public void upgrade(Coordinates coords){
        city.upgrade(coords);
    }
    public void stepGame(){
        city.timePassed(timeSpeed);
    }
    public Tile[][] getMap(){
        return city.getMap();
    }
    public String getCityName(){
        return city.getName();
    }//
    public int getPopulation(){
        return city.getPopulation();
    }
    public int getFunds(){
        return city.getFunds();
    }
    public int[] getLastBalance(){
        return city.getLastBalance();
    }
    public int getCitizenslength(){return city.getCitizensLength();}
    public ArrayList<Citizen> getCitizens() {return city.getCitizens();}
    public City getCity() {
        return city;
    }
    public void setTimeSpeed(int timeSpeed) {
        this.timeSpeed = timeSpeed;
    }
    public int getElectricityProduction() {
        return city.getElectricityProduction();
    }

    public double[] getEducatedCitizens(){
        return city.getEducatedCitizens();
    }
    public int getElectricityNeed() {
        return city.getElectricityNeed();
    }
    public ResidentialZone getPoweredResZone() { return city.getPoweredResZone();}

    public int getTimeSpeed() {return timeSpeed;}

    public boolean gameOver(){
        if(city.getDaysInNegative() > 365){
            return true;
        }
        return false;
    }
}
