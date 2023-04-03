package Model;

import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.*;
import com.sun.tools.javac.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class City {
    private String name;
    private ArrayList<Citizen> citizens;

    private Tile[][] map;

    public City(String cityName) {
        this.name = cityName;
        this.citizens = new ArrayList<>();

        // Read the default map
        this.map = new Tile[18][32];
        try {
            Scanner sc = new Scanner(new File("KukaPest/Assets/testMap.txt"));
            for (int i = 0; i < 18; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < 32; j++) {
                    int mapNum = Character.getNumericValue(line.charAt(j));
                    this.map[i][j] = switch (mapNum) {
                        case 1 -> new Grass();
                        case 2 -> new Dirt();
                        case 3 -> new Water();
                        default -> throw new InvalidParameterException();
                    };
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." + e);
        }
    }

    boolean build(Building toBuild, Coordinates coords){
        //printMap
        Constructable toBeBuilt = switch(toBuild){
            case STADIUM -> new Stadium();
            case ROAD -> new Road();
            case POLICE -> new Police();
            case UNIVERSITY -> new University();
            case SCHOOL -> new School();
            case POLE -> new Pole();
            case POWER_PLANT -> new PowerPlant();
            case RESIDENTIAL -> new ResidentialZone();
            case INDUSTRY -> new IndustrialZone();
        };

        if(toBeBuilt instanceof MainZone ){
            if(canBeBuilt(toBeBuilt,coords)){
                for(int i=coords.getX();i<coords.getX()+((MainZone) toBeBuilt).getWidth();i++){
                    for(int j=coords.getY();j<coords.getY()+((MainZone) toBeBuilt).getHeight();j++){
                        this.map[i][j] = new ZonePart((MainZone)toBeBuilt);
                    }
                }
                this.map[coords.getX()][coords.getY()] = toBeBuilt;
            printMap();
            return true;
            }

        }else if(toBeBuilt instanceof Road){
            if(canBeBuilt(toBeBuilt,coords)){
                this.map[coords.getY()][coords.getX()] = new Road();
                return true;
            }

        }else if(toBeBuilt instanceof Pole) {
            if(canBeBuilt(toBeBuilt,coords)) {
                this.map[coords.getY()][coords.getX()] = new Pole();
                return true;
            }
        }


        return false;
    }
    boolean canBeBuilt(Constructable toBuild, Coordinates coords){
        //TODO: implement method
        if(toBuild instanceof Pole || toBuild instanceof Road){
            if(!(map[coords.getX()][coords.getY()] instanceof Environment)) return false;
        }
        else{
            MainZone mz = ((MainZone)toBuild);
            for(int i=coords.getX();i< coords.getX()+mz.getWidth();i++){
                for(int j=coords.getY();j< coords.getY()+ mz.getHeight();j++){
                    if(this.map[i][j] instanceof ZonePart
                            || this.map[i][j] instanceof MainZone
                            || this.map[i][j] instanceof Road
                            || this.map[i][j] instanceof Pole){
                        System.out.println("Foglalt terulet sorry");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //forTesting
    public void printMap() {
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 32; j++) {
                if(this.map[i][j] instanceof MainZone || this.map[i][j] instanceof ZonePart
                    || this.map[i][j] instanceof Road || this.map[i][j] instanceof Pole){
                    System.out.print("1");
                }else{
                    System.out.print("0");
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public Tile[][] getMap() {
        return map;
    }
    public String getName() {
        return name;
    }

}
