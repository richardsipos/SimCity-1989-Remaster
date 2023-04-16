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
    private int funds=10000;

    //map meretei:
    int mapHeight=31;
    int mapWidth=59;

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
            System.out.println("An error occurred." + e);
        }
    }

    boolean build(Building toBuild, Coordinates coords){
        //printMap
        //printMap();
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
//            printMap();
            return true;
            }

        }else if(toBeBuilt instanceof Road){
            if(canBeBuilt(toBeBuilt,coords)){
                this.map[coords.getX()][coords.getY()] = new Road();
                return true;
            }

        }else if(toBeBuilt instanceof Pole) {
            if(canBeBuilt(toBeBuilt,coords)) {
                this.map[coords.getX()][coords.getY()]= new Pole();
                return true;
            }
        }


        return false;
    }
    boolean canBeBuilt(Constructable toBuild, Coordinates coords){
        if(toBuild instanceof Road){
            return map[coords.getX()][coords.getY()] instanceof Environment
                    && (coords.getX() + 1 < mapHeight && (map[coords.getX() + 1][coords.getY()] instanceof Road)
                    || (coords.getX() - 1 > 0 && map[coords.getX() - 1][coords.getY()] instanceof Road)
                    || (coords.getY() + 1 < mapHeight && map[coords.getX()][coords.getY() + 1] instanceof Road)
                    || (coords.getY() - 1 > 0 && map[coords.getX()][coords.getY() - 1] instanceof Road));
        } else if (toBuild instanceof Pole) {
            if(!(map[coords.getX()][coords.getY()] instanceof Environment)) return false;
        } else{
            MainZone mz = ((MainZone)toBuild);
            for(int i=coords.getX();i< coords.getX()+mz.getWidth();i++){
                for(int j=coords.getY();j< coords.getY()+ mz.getHeight();j++){
                    if(this.map[i][j] instanceof ZonePart   //Nem epíthetsz mert foglalt terület.
                            || this.map[i][j] instanceof MainZone
                            || this.map[i][j] instanceof Road
                            || this.map[i][j] instanceof Pole){
                        System.out.println("Foglalt terulet sorry");
                        return false;
                    }else if(this.map[i][j] instanceof Water){  // Csak utat építhetsz vízre // !(toBuild instanceof Road) &&
                        System.out.println("Vizre csak utat lehet építeni!");
                        return false;
                    }
                }
            }
        }

        //Csak út mellé szabad rakni épületeket.

        if(toBuild instanceof MainZone mz){
            boolean nearbyRoadExists = false;


            //ha nem vagyunk a legfelső sorban.
            for(int k=coords.getY();k<coords.getY()+ mz.getHeight();k++){
                if (this.map[coords.getX() - 1][k] instanceof Road) {
                    nearbyRoadExists = true;
                    break;
                }
            }

            //ha nem vagyunk a legalso sorban.
            for(int k=coords.getY();k<coords.getY()+ mz.getHeight();k++){
                if (this.map[coords.getX() + mz.getWidth()][k] instanceof Road) {
                    nearbyRoadExists = true;
                    break;
                }
            }

            //ha nem vagyunk baloldali oszlopban.
            for(int k=coords.getX();k<coords.getX()+ mz.getWidth();k++){
                if (this.map[k][coords.getY() - 1] instanceof Road) {
                    nearbyRoadExists = true;
                    break;
                }
            }

            //ha nem vagyunk baloldali oszlopban.
            for(int k=coords.getX();k<coords.getX()+ mz.getWidth();k++){
                if (this.map[k][coords.getY() + mz.getHeight()] instanceof Road) {
                    nearbyRoadExists = true;
                    break;
                }
            }

            //bal felso sarok, jobb also sarok, jobb felso sarok,
            if(this.map[coords.getX()-1][coords.getY()-1] instanceof  Road ||
                    this.map[coords.getX()+mz.getWidth()][coords.getY()+ mz.getHeight()] instanceof Road ||
                    this.map[coords.getX()-1][coords.getY()+ mz.getHeight()] instanceof Road ||
                    this.map[coords.getX()+mz.getWidth()][coords.getY()-1] instanceof Road ){
                nearbyRoadExists=true;
            }

            //van Út mellete
            if(nearbyRoadExists){
                //nem checkelem le ha a this.funds nagyobb mint az epulet koltsege, mivel negativba is mehetunk!
                this.funds=this.funds-(toBuild.getPrice());
                return true;
            }

        }else {
            //nem checkelem le ha a this.funds nagyobb mint az epulet koltsege, mivel negativba is mehetunk!
            this.funds = this.funds - (toBuild.getPrice());
            return true;
        }
        return false;

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
                    if(  ((MainZone)this.map[i][j]).getCurrentCapacity() < ((MainZone)this.map[i][j]).getMaxCapacity()  ){
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
