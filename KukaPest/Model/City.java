package Model;

import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.Dirt;
import Model.Map.Grass;
import Model.Map.Tile;
import Model.Map.Water;

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
        return false;
    }
    boolean canBeBuilt(Building toBuild, Coordinates coords){
        //TODO: implement method
        return false;
    }

    //Getters
    public Tile[][] getMap() {
        return map;
    }
    public String getName() {
        return name;
    }

}
