package Model;

import Model.Map.Tile;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class City {
    private ArrayList<Citizen> citizens = new ArrayList<>();
    private Tile[][] map;// = new Tile[][];
    //private ArrayList<ArrayList<Tile>> map = new ArrayList<>();
    private int funds;
    private int satisfaction;
    private LocalDateTime date;
    private int minCitizen;
    private int loneDuration;
    private int lastBalance;
    private int lastIncome;
    private int lastExpense;

    public void City(int funds,int satisfaction,int mapSize){
        this.funds=funds;
        this.satisfaction=satisfaction;
        map = new Tile[mapSize][mapSize];

    }

    public void timePassed(int days){

    }

    public void checkEducatedNumber(){

    }







}
