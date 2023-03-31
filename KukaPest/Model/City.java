package Model;

import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class City {
    private String name;
    private ArrayList<Citizen> citizens;

    private Tile[][] map;// = new Tile[][];

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
        //TODO: implement method
        //printMap();
        Constructable toBeBuilt;
        toBeBuilt = switch(toBuild){
            case STADIUM -> new Stadium();
            case ROAD -> new Road();
            case POLICE -> new Police();
            case UNIVERSITY -> new University();
            case SCHOOL -> new School();
            case POLE -> new Pole();
            case POWER_PLANT -> new PowerPlant();
            case RESIDENTIAL -> new ResidentialZone();
            case INDUSTRY -> new IndustrialZone();
            default -> throw new InvalidParameterException();
        };

        if(toBeBuilt instanceof MainZone ){
            if(canBeBuilt(toBuild,coords)){
                for(int i=coords.getY();i<coords.getY()+((MainZone) toBeBuilt).getHeight();i++){
                    for(int j=coords.getX();j<coords.getX()+((MainZone) toBeBuilt).getWidth();j++){
                        this.map[i][j] = new ZonePart((MainZone)toBeBuilt);
                    }
                }
                this.map[coords.getY()][coords.getX()] = new MainZone(((MainZone) toBeBuilt).getHeight(),((MainZone) toBeBuilt).getWidth()) {
            };
            printMap();
            return true;
            }

        }else if(toBeBuilt instanceof Road){
            if(canBeBuilt(toBuild,coords)){
                this.map[coords.getY()][coords.getX()] = new Road();
                return true;
            }

        }else if(toBeBuilt instanceof Pole) {
            if(canBeBuilt(toBuild,coords)) {

                this.map[coords.getY()][coords.getX()] = new Pole();
                return true;
            }
        }


        return false;
    }
    boolean canBeBuilt(Building toBuild, Coordinates coords){
        //TODO: implement method

        int widthNeeded;
        widthNeeded = switch(toBuild){
            case STADIUM -> 4;
            case ROAD -> 1;
            case POLICE -> 2;
            case UNIVERSITY -> 4;
            case SCHOOL -> 2;
            case POLE -> 1;
            case POWER_PLANT -> 4;
            case RESIDENTIAL -> 2;
            case INDUSTRY -> 2;
            default -> throw new InvalidParameterException();
        };

        int heigthNeeded;
        heigthNeeded = switch(toBuild){
            case STADIUM -> 4;
            case ROAD -> 1;
            case POLICE -> 4;
            case UNIVERSITY -> 4;
            case SCHOOL -> 4;
            case POLE -> 1;
            case POWER_PLANT -> 4;
            case RESIDENTIAL -> 2;
            case INDUSTRY -> 2;
            default -> throw new InvalidParameterException();
        };

        for(int i=coords.getY();i< coords.getY()+heigthNeeded;i++){
            for(int j=coords.getX();j< coords.getX()+widthNeeded;j++){
                if(this.map[i][j] instanceof ZonePart
                    || this.map[i][j] instanceof MainZone
                    || this.map[i][j] instanceof Road
                    || this.map[i][j] instanceof Pole){
                        System.out.println("Foglalt terulet sorry");
                        return false;
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

    //Getters
    public Tile[][] getMap() {
        return map;
    }
    public String getName() {
        return name;
    }

}
