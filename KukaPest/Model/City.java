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
    private final String name;
    private final ArrayList<Citizen> citizens;
    private int funds = 10000;

    //Map dimensions:
    private final int mapHeight = 31;
    private final int mapWidth = 59;

    private final Tile[][] map;

    public int getPopulation() {
        return citizens.size();
    }

    public int getFunds() {
        return funds;
    }

    public City(String cityName) {
        this.name = cityName;
        this.citizens = new ArrayList<>();


        // Read the default map
        this.map = new Tile[31][59];
        try {
            Scanner sc = new Scanner(new File("KukaPest/Assets/map.txt"));
            for (int i = 0; i < 31; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < 59; j++) {
                    int mapNum = Character.getNumericValue(line.charAt(j));
                    this.map[i][j] = switch (mapNum) {
                        case 1 -> new Grass();
                        case 2 -> new Dirt();
                        case 3 -> new Water();
                        case 4 -> new Road();
                        default -> throw new InvalidParameterException();
                    };
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. " + e);
        }
    }

    boolean build(Building toBuild, Coordinates coords){
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

        if(canBeBuilt(toBeBuilt,coords)) {
            if((toBeBuilt instanceof MainZone)) {
                for(int i = coords.getHeight(); i < coords.getHeight() + ((MainZone) toBeBuilt).getHeight(); i++){
                    for(int j = coords.getWidth(); j < coords.getWidth()+((MainZone) toBeBuilt).getWidth(); j++){
                        this.map[i][j] = new ZonePart((MainZone)toBeBuilt);
                    }
                }
            }
            this.map[coords.getHeight()][coords.getWidth()] = toBeBuilt;
            this.funds -= (toBeBuilt.getPrice());
            return true;
        }
        return false;
    }
    boolean canBeBuilt(Constructable toBuild, Coordinates coords){
        if(toBuild instanceof Road){
            return (map[coords.getHeight()][coords.getWidth()] instanceof Environment
                    && (coords.getHeight() + 1 < mapHeight && (map[coords.getHeight() + 1][coords.getWidth()] instanceof Road)
                    || (coords.getHeight() - 1 >= 0 && map[coords.getHeight() - 1][coords.getWidth()] instanceof Road)
                    || (coords.getWidth() + 1 < mapWidth && map[coords.getHeight()][coords.getWidth() + 1] instanceof Road)
                    || (coords.getWidth() - 1 >= 0 && map[coords.getHeight()][coords.getWidth() - 1] instanceof Road)));
        } else if (toBuild instanceof Pole) {
            return map[coords.getHeight()][coords.getWidth()] instanceof Environment;
        } else{
            MainZone mz = ((MainZone)toBuild);
            for(int i = coords.getHeight(); i< coords.getHeight() + mz.getHeight(); i++){
                for(int j = coords.getWidth(); j< coords.getWidth()+ mz.getWidth(); j++){
                    if(this.map[i][j] instanceof ZonePart   //Nem építhetsz mert foglalt terület.
                            || this.map[i][j] instanceof MainZone
                            || this.map[i][j] instanceof Road
                            || this.map[i][j] instanceof Pole
                            || this.map[i][j] instanceof Water){
                        System.out.println("Foglalt terulet sorry");
                        return false;
                    }
                }
            }
            // Check for a road nearby
            boolean nearbyRoadExists = false;

            //Check for road at the top of the building
            if(coords.getHeight() > 0){
                for (int k = coords.getWidth(); k < coords.getWidth() + mz.getWidth(); k++) {
                    if (this.map[coords.getHeight() - 1][k] instanceof Road) {
                        nearbyRoadExists = true;
                        break;
                    }
                }
            }

            //ha nem vagyunk a legalso sorban.
            if(coords.getHeight() + mz.getHeight() < mapHeight) {
                for (int k = coords.getWidth(); k < coords.getWidth() + mz.getWidth(); k++) {
                    if (this.map[coords.getHeight() + mz.getHeight()][k] instanceof Road) {
                        nearbyRoadExists = true;
                        //System.out.println("Legalso sorban");
                        break;
                    }
                }
            }

            //ha nem vagyunk baloldali oszlopban.
            if(coords.getWidth() >0) {
                for (int k = coords.getHeight(); k < coords.getHeight() + mz.getHeight(); k++) {
                    if (this.map[k][coords.getWidth() - 1] instanceof Road) {
                        nearbyRoadExists = true;
                        //System.out.println("Bal oszlop");
                        break;
                    }
                }
            }

            //ha nem vagyunk baloldali oszlopban.
            if(coords.getWidth() + mz.getWidth()<mapWidth) {
                for (int k = coords.getHeight(); k < coords.getHeight() + mz.getHeight(); k++) {
                    if (this.map[k][coords.getWidth() + mz.getWidth()] instanceof Road) {
                        nearbyRoadExists = true;
                        //System.out.println("Jobb oszlop");
                        break;
                    }
                }
            }
            //bal felso sarok, jobb felso sarok, bal also sarok, jobb also sarok
            if(coords.getHeight()>0 && coords.getWidth()>0){
                if(this.map[coords.getHeight()-1][coords.getWidth()-1] instanceof  Road){
                    nearbyRoadExists=true;
                }
            }
            if(coords.getHeight()+mz.getHeight()<mapHeight && coords.getWidth()>0){
                if(this.map[coords.getHeight()+mz.getHeight()][coords.getWidth()-1] instanceof Road){
                    nearbyRoadExists=true;
                }
            }
            if(coords.getHeight()>0 && coords.getWidth()+ mz.getWidth()<mapWidth){
                if(this.map[coords.getHeight()-1][coords.getWidth()+ mz.getWidth()] instanceof Road){
                    nearbyRoadExists=true;
                }
            }
            if(coords.getHeight()+mz.getHeight()<mapHeight && coords.getWidth()+mz.getWidth() <mapWidth){
                if(this.map[coords.getHeight()+mz.getHeight()][coords.getWidth()+ mz.getWidth()] instanceof Road){
                    nearbyRoadExists=true;
                }
            }

            //van Út mellete
            return nearbyRoadExists;
        }
    }

//    ezt hivja majd a TimePassed es intezzi majd a Citizenet bekoltozeset.
    void handleMoveIn(){
        ResidentialZone Rzone = hasFreeResidential();
        IndustrialZone Izone = hasFreeIndustrial();

        //ha van free residential zone es van industrial zone is akkor letre kell hozzni egy citizent.
        if(Rzone!=null && Izone!=null){
            Citizen citizen = new Citizen(Rzone,Izone);
            citizens.add(citizen);
            Rzone.addCitizen(citizen);
            Izone.addCitizen(citizen);
        }
        //amugy meg nem tortenik semmi.
    }

    ResidentialZone hasFreeResidential(){
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if(this.map[i][j] instanceof ResidentialZone){
                    if(((MainZone)this.map[i][j]).getCurrentCapacity() < ((MainZone)this.map[i][j]).getMaxCapacity()){
                        return ((ResidentialZone) this.map[i][j]);
                    }
                }
            }
        }
        System.out.println("residential");
        return null;
    }

    IndustrialZone hasFreeIndustrial(){
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if( (this.map[i][j] instanceof IndustrialZone) ) {
                    if(  ((MainZone)this.map[i][j]).getCurrentCapacity() < ((MainZone)this.map[i][j]).getMaxCapacity()  ){
                        return ((IndustrialZone) this.map[i][j]);
                    }
                }
            }
        }
        System.out.println("industrial");
        return null;
    }

    public void timePassed(int days){
        for (int i = 0; i < days; i++) {
            // One day
            handleMoveIn();
        }
    }

    //forTesting
    public void printMap() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if(this.map[i][j] instanceof MainZone || this.map[i][j] instanceof ZonePart
                    || this.map[i][j] instanceof Road || this.map[i][j] instanceof Pole){
                //if(this.map[i][j] instanceof ResidentialZone){
                    System.out.print("1");
                }else{
                    System.out.print("0");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Tile[][] getMap() {
        return map;
    }
    public String getName() {
        return name;
    }

}
