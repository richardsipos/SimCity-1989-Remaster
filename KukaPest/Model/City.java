package Model;


import Model.Map.Tile;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class City {
    private ArrayList<Citizen> citizens = new ArrayList<>();
    private Tile[][] map;// = new Tile[][];
    private int funds;
    private int satisfaction;
    private LocalDateTime date;
    private int minCitizen;
    private int loneDuration;
    private int lastBalance;
    private int lastIncome;
    private int lastExpense;

    //without DB ctor
    public void City(int funds, int satisfaction, int mapSize){
        this.funds=funds;
        this.satisfaction=satisfaction;
        this.map = new Tile[mapSize][mapSize];
        date=date.now();
        this.minCitizen=0;
        this.loneDuration=0;
        this.lastBalance=0;
        this.lastIncome=0;
        this.lastExpense=0;


    }

    // with DB ctor
    public City(ArrayList<Citizen> citizens, Tile[][] map, int funds, int satisfaction, LocalDateTime date, int minCitizen, int loneDuration, int lastBalance, int lastIncome, int lastExpense) {
        this.citizens = citizens;
        this.map = map;
        this.funds = funds;
        this.satisfaction = satisfaction;
        this.date = date;
        this.minCitizen = minCitizen;
        this.loneDuration = loneDuration;
        this.lastBalance = lastBalance;
        this.lastIncome = lastIncome;
        this.lastExpense = lastExpense;
    }



    public void timePassed(int days){

    }

    public void checkEducatedNumber(){

    }

    public void setZone(Coordinates coord,String type){

    }

    public void handleMoveIn(){

    }

    public void destroy(Coordinates coord){

    }

    public String getZoneDetails(Coordinates coord){

    }

    public void build(){

    }

    public boolean hasLoans(){

    }

    public int industryAndServiceBalance(){

    }

    public int satisfaction(){

    }

    public void checkCitizen(){

    }

    public boolean isGameOver(){

    }

    public void collectTax(){

    }

    public void payUpKeeps(){

    }

    private ResidentialZone hasFreeResidential(){

    }

    private IndustrialZone hasFreeWorkingSpace(ResidentialZone res){

    }

    private void checkRoadNearby(Coordinates coord){

    }

    private boolean checkConnection(Mainzone first, Mainzone second){

    }

    private boolean canBeDestroyed(Coordinates coord){

    }

    private boolean destroyZone(Coordinates coord){

    }

    private boolean destroyBuilding(Coordinates coord){

    }

    public boolean canBeBuilt(Coordinates coord,String type){

    }

    public void handleLightUp(Coordinates coord){

    }

    public void roadDestruction(){

    }

    public boolean upgrade(Coordinates coord){

    }











}
