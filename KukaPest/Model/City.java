package Model;

import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Helper.Graph;
import Model.Map.*;
import com.sun.tools.javac.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//import java.util.Date;

public class City {
    private final String name;
    private final ArrayList<Citizen> citizens;
    private int funds = 10000;
    private int[] lastBalance = {0 , 0};
    private Date date;

    //Map dimensions:
    private final int mapHeight = 31;
    private final int mapWidth = 59;

    private final Tile[][] map;

    public int satisfaction(){
        int total = 0;
        for (Citizen c : this.citizens) {
            total += c.satisfaction();
        }
        return total;
    }

    public int getPopulation() {
        return citizens.size();
    }

    public int getFunds() {
        return funds;
    }
    public int[] getLastBalance(){
        return lastBalance;
    }

    public Graph destroyGraph;

    public int destroynodes = 0;

    public int numberBuilding = 0;

    public Coordinates startRoad;

    public City(String cityName) {
        this.name = cityName;
        this.citizens = new ArrayList<>();
        date = new Date(2000,02,1);

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
                    if(this.map[i][j] instanceof Road){
                        destroynodes = destroynodes + 1;
                        startRoad = new Coordinates(i,j);
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. " + e);
        }
    }

    boolean build(Building toBuild, Coordinates coords){
        Constructable toBeBuilt = switch(toBuild){
            case STADIUM -> new Stadium(coords);
            case ROAD -> new Road();
            case POLICE -> new Police(coords);
            case UNIVERSITY -> new University(coords);
            case SCHOOL -> new School(coords);
            case POLE -> new Pole();
            case POWER_PLANT -> new PowerPlant(coords);
            case RESIDENTIAL -> new ResidentialZone(coords);
            case INDUSTRY -> new IndustrialZone(coords);

        };

        if(toBeBuilt instanceof Road || toBeBuilt instanceof Pole){
            destroynodes = destroynodes + 1;
        }else{
            destroynodes = destroynodes + 1;
            numberBuilding = numberBuilding + 1;
        }

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
            calculateSatisfaction();
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
            /*if(coords.getHeight()>0 && coords.getWidth()>0){
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
            }*/

            //van Út mellete
            return nearbyRoadExists;
        }
    }

//    ezt hivja majd a TimePassed es intezzi majd a Citizenet bekoltozeset.
    void handleMoveIn(){
        //random people will come to the city (bebtween 1-4 bot ends included)
        Random rand = new Random();
        int randomNumber = (rand.nextInt(4))+1;

        for(int i=1;i<=randomNumber;i++){
            ResidentialZone Rzone = hasFreeResidential();
            IndustrialZone Izone = hasFreeIndustrial();
            //ha van free residential zone es van industrial zone is akkor letre kell hozzni egy citizent.
            if(Rzone!=null && Izone!=null){
                Citizen citizen = new Citizen(Rzone,Izone);
                citizens.add(citizen);
                Rzone.addCitizen(citizen);
                Izone.addCitizen(citizen);
            }
        }
        //amugy meg nem tortenik semmi.



    }
    void updateBalance(){
        int[] balance = {0 , 0};
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if(map[i][j] instanceof Constructable) balance[0] -= ((Constructable) map[i][j]).getUpKeep();
            }
        }

        for (Citizen c : citizens) {
            balance[1] += c.payTax();
        }

        this.funds += balance[1] + balance[0];
        lastBalance = balance;
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
        int dateChange = date.DaysPassed(days);
        for (int i = 0; i < days; i++) {
            // One day Passed!
            handleMoveIn();
            updateBalance();
        }
        if(dateChange > 0){
             //A month has passed!
            if(dateChange>1){
                //A year has passed!
            }
        }
        System.out.println("Elégedettség: " + satisfaction()); //debug
    }

    /**
     * This method calculates the radii of service buildings, in which satisfaction of resi/industrial zones are boosted
     * and then executes the satisfaction boost.
     * @param coords Coordinates of the given service building
     * @param radius Radius of given service building
     * @param value Value of satisfaction boost
     * @param b Given service building
     */
    public void modifySatisfactionBoost(Coordinates coords, int radius, int value, Constructable b){
        MainZone mz = (MainZone)b;
        int left, right, top, bottom;

        if (coords.getWidth() - radius < 0) { // check if radius is too big for the left side
            left = 0;
        } else left = coords.getWidth() - radius;
        if ((coords.getWidth()+mz.getWidth()+radius) >= mapWidth) { // check if radius is too big for the right side
            right = mapWidth-1;
        } else right = coords.getWidth()+mz.getWidth() + radius;
        if (coords.getHeight() - radius < 0) { // check if radius is too big for top
            top = 0;
        } else top = coords.getHeight() - radius;
        if ((coords.getHeight()+mz.getHeight()+radius) >= mapHeight) { // check if radius is too big for bottom
            bottom = mapHeight-1;
        } else bottom = (coords.getHeight()+mz.getHeight()) + radius;
        for (int i = top; i < bottom; ++i) {
            for (int j = left; j < right; ++j) {
                if(this.map[i][j] instanceof ResidentialZone || this.map[i][j] instanceof IndustrialZone){
                    ((MainZone) this.map[i][j]).setSatisfactionBoost(value);
                } /*else if (this.map[i][j] instanceof ZonePart){ //zonepart check, NOT usable
                    if (((ZonePart) this.map[i][j]).mainBuilding instanceof ResidentialZone
                            || ((ZonePart) this.map[i][j]).mainBuilding instanceof IndustrialZone){
                        ((ZonePart) this.map[i][j]).mainBuilding.setSatisfactionBoost(value);
                    }
                }*/
            }
        }
    }

    public boolean destroy(Coordinates coords) {
        if (this.map[coords.getHeight()][coords.getWidth()] instanceof Road) {
            if(coords.getHeight() == startRoad.getHeight() && coords.getWidth() == startRoad.getWidth()){
                return false;
            }
            destroyGraph = new Graph(destroynodes);
            //System.out.println(destroynodes);
            buildingsAvailable(coords);
            //System.out.println(destroyGraph);
            if(destroyGraph.BFS(0,numberBuilding)){
                this.map[coords.getHeight()][coords.getWidth()] = null;
                this.map[coords.getHeight()][coords.getWidth()] = new Grass();
                destroynodes = destroynodes - 1;
                return true;
            }

            return false;
        }
        else if (this.map[coords.getHeight()][coords.getWidth()] instanceof Pole) {
            //későbbi ellenőrzés
            return false;
        }
        else if (this.map[coords.getHeight()][coords.getWidth()] instanceof MainZone || this.map[coords.getHeight()][coords.getWidth()] instanceof ZonePart) {
            MainZone mainZone;
            if(this.map[coords.getHeight()][coords.getWidth()] instanceof MainZone){
                mainZone = (MainZone) this.map[coords.getHeight()][coords.getWidth()];
            }
            else{
                mainZone = ((ZonePart) this.map[coords.getHeight()][coords.getWidth()]).mainBuilding;
            }

            if (mainZone instanceof ResidentialZone) {
                if (mainZone.getCurrentCapacity() != 0) {
                    return false;
                } else {
                    int width = mainZone.getWidth();
                    int heigth = mainZone.getHeight();
                    for (int i = mainZone.getCoordinates().getHeight(); i < mainZone.getCoordinates().getHeight() + heigth; i++) {
                        for (int j = mainZone.getCoordinates().getWidth(); j < mainZone.getCoordinates().getWidth() + width; j++) {
                            this.map[i][j] = null;
                            this.map[i][j] = new Grass();
                        }
                    }
                    destroynodes = destroynodes - 1;
                    numberBuilding = numberBuilding - 1;
                    return true;
                }
            }
            if (mainZone instanceof IndustrialZone) {
                if (mainZone.getCurrentCapacity() != 0) {
                    return false;
                } else {
                    int width = mainZone.getWidth();
                    int heigth = mainZone.getHeight();
                    for (int i = mainZone.getCoordinates().getHeight(); i < mainZone.getCoordinates().getHeight() + heigth; i++) {
                        for (int j = mainZone.getCoordinates().getWidth(); j < mainZone.getCoordinates().getWidth() + width; j++) {
                            this.map[i][j] = null;
                            this.map[i][j] = new Grass();
                        }
                    }
                    destroynodes = destroynodes - 1;
                    numberBuilding = numberBuilding - 1;
                    return true;
                }
            }
            if (mainZone instanceof ServiceZone) {
                int width = mainZone.getWidth();
                int heigth = mainZone.getHeight();
                for (int i = mainZone.getCoordinates().getHeight(); i < mainZone.getCoordinates().getHeight() + heigth; i++) {
                    for (int j = mainZone.getCoordinates().getWidth(); j < mainZone.getCoordinates().getWidth() + width; j++) {
                        this.map[i][j] = null;
                        this.map[i][j] = new Grass();
                    }
                }
                destroynodes = destroynodes - 1;
                numberBuilding = numberBuilding - 1;
                return true;
            }


        }
        return true;
    }

    public void buildingsAvailable(Coordinates coords) {
        numberBuilding = 0;
        destroyGraph.addNode(0, true, new Coordinates(startRoad.getHeight(), startRoad.getWidth()));
        //System.out.println(startRoad.getHeight() + " " + startRoad.getWidth() + " id: " + destroyGraph.getNodeID(new Coordinates(startRoad.getHeight(),startRoad.getWidth())));
        int id = 1;

        //node hozzáadás
        for (int i = this.mapHeight - 1; i >= 0; i--) {
            for (int j = this.mapWidth - 1; j >= 0; j--) {
                if((i == startRoad.getHeight() && j == startRoad.getWidth()) || (i == coords.getHeight() && j == coords.getWidth())){
                    continue;
                }
                else if (this.map[i][j] instanceof Road) {
                    System.out.println(i + " " + j + " id: " + id);
                    destroyGraph.addNode(id, true, new Coordinates(i, j));
                    id = id + 1;
                }
                else if (this.map[i][j] instanceof MainZone) {
                    numberBuilding++;
                    System.out.println(i + " " + j + " id: " + id);
                    destroyGraph.addNode(id, false, new Coordinates(i, j));
                    id = id + 1;
                }
            }
        }

        for (int i = this.mapHeight - 1; i >= 0; i--) {
            for (int j = this.mapWidth - 1; j >= 0; j--) {
                if (destroyGraph.getNodeID(new Coordinates(i, j)) != -1 && (this.map[i][j] instanceof Road)) {
                    int first_id = destroyGraph.getNodeID(new Coordinates(i, j));

                    //bals alsó sarok, jobb alsó sarok
                    if (i == 0 && j == this.mapWidth - 1) {
                        if (this.map[i][j - 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                    } else if (i == this.mapHeight - 1 && j == this.mapWidth - 1) {
                        if (this.map[i - 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j - 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                    }

                    //bal felső sarok, jobb felső sarok
                    else if (i == 0 && j == 0) {
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i + 1, j)));
                        }
                    } else if (i == this.mapHeight && j == 0) {
                        if (this.map[i - 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j + 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                    }

                    //alsó sor, felső sor
                    else if (i != 0 && i < this.mapHeight - 1 && j == this.mapWidth - 1) {
                        if (this.map[i - 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j - 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i + 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                    } else if (i != 0 && i < this.mapHeight - 1 && j == 0) {
                        if (this.map[i - 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i + 1, j)));
                        }
                    }

                    //jobb oszlop, bal oszlop
                    else if (i == this.mapHeight - 1 && j != 0 && j < this.mapWidth - 1) {
                        if (this.map[i][j - 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i - 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j + 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                    } else if (i == 0 && j != 0 && j < this.mapWidth - 1) {
                        if (this.map[i][j - 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j + 1)));
                        }
                    }


                    //nem sarok vagy szél
                    else {
                        if (this.map[i][j - 1] instanceof Road && destroyGraph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && destroyGraph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i - 1][j] instanceof Road && destroyGraph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            destroyGraph.addEdge(first_id, destroyGraph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                    }


                }
            }
        }
    }



    /**
     * This method resets all satisfaction boost values every time something is built and recalculates the new values.
     */
    public void calculateSatisfaction(){
        for (Tile[] x : this.map) {
            for (Tile z : x) {
                if (z instanceof MainZone) ((MainZone) z).resetSatisfactionBoost();
            }
        }

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if (this.map[i][j] instanceof Stadium) modifySatisfactionBoost(new Coordinates(i,j), 9, 15, (Constructable) this.map[i][j]);
                else if (this.map[i][j] instanceof Police) modifySatisfactionBoost(new Coordinates(i,j), 6, 10, (Constructable) this.map[i][j]);
            }
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
    public String getDate(){
        return date.toString();
    }

}
