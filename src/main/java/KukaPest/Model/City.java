package KukaPest.Model;

import KukaPest.Model.Helper.EduLevel;
import KukaPest.Model.Map.*;
import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Helper.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.*;

//import java.util.Date;

public class City implements java.io.Serializable {
    private final String name;
    final ArrayList<Citizen> citizens;
    private int funds = 10000;
    private int[] lastBalance = {0 , 0};
    private KukaPest.Model.Date date;
    private final int guaranteedCitizens = 50;
    private int electricityProduction=0;
    private int electricityNeed=0;
    private int maxSchoolDegrees = 50; //in percentage
    private int maxUniversityDegrees = 20; //in percentage
    private int daysInNegative = 0;

    //Map dimensions:
    private final int mapHeight = 27;
    private final int mapWidth = 48;

    private final Tile[][] map;

    /**
     * This method calculates the city's total satisfaction
     * @return the overall satisfaction
     */
    public int satisfaction(){
        int total = 0;
        for (Citizen c : this.citizens) {
            total += c.satisfaction();
        }

        if(this.citizens.size() > 0)
            return total/this.citizens.size();
        else
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

    public Graph destroygraph;
    public Graph moveInGraph;

    public int destroynodes = 0;

    public int numberBuilding = 0;

    public Coordinates startRoad;


    public City(String cityName) {
        this.name = cityName;
        this.citizens = new ArrayList<>();
        date = new KukaPest.Model.Date(2000,02,1);

        // Read the default map
        this.map = new Tile[mapHeight][mapWidth];
        try {
            Scanner sc = new Scanner(new File("src/main/java/KukaPest/Assets/map.txt"));
            for (int i = 0; i < mapHeight; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < mapWidth; j++) {
                    int mapNum = Character.getNumericValue(line.charAt(j));
                    this.map[i][j] = switch (mapNum) {
                        case 1 -> new Grass();
                        case 2 -> new Dirt();
                        case 3 -> new Water();
                        case 4 -> new Road(null);
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

    /**
     * This method handles the building process, calling upon checking methods to decide whether the construction is feasible
     * @param toBuild The building to be constructed
     * @param coords The top left coordinates of given building
     * @return True/false depending on the state of construction
     */
    boolean build(Building toBuild, Coordinates coords){
        Environment enviroment;
        if(this.map[coords.getHeight()][coords.getWidth()] instanceof Grass){
            enviroment = new Grass();
        }
        else if(this.map[coords.getHeight()][coords.getWidth()] instanceof Dirt){
            enviroment = new Dirt();
        }
        else{
            enviroment = new Water();
        }

        Constructable toBeBuilt = switch(toBuild){
            case STADIUM -> new Stadium(coords);
            case ROAD -> new Road(enviroment);
            case POLICE -> new Police(coords);
            case UNIVERSITY -> new University(coords);
            case SCHOOL -> new School(coords);
            case POLE -> new Pole();
            case POWER_PLANT -> new PowerPlant(coords);
            case RESIDENTIAL -> new ResidentialZone(coords);
            case INDUSTRY -> new IndustrialZone(coords);
            case SERVICE -> new ServiceZone(coords);
        };



        if(canBeBuilt(toBeBuilt,coords)) {
            if(toBeBuilt instanceof Road){
                destroynodes = destroynodes + 1;

            }else if(toBeBuilt instanceof MainZone){
                destroynodes = destroynodes + 1;
                numberBuilding = numberBuilding + 1;
            }

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

    /**
     * This method checks if building requirements are met or not (road nearby and empty space)
     * @param toBuild Building to be built
     * @param coords Top left coordinates of given building
     * @return True if a nearby road exists and space is available, else false
     */
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

    /**
     * This method calls upon methods checking reqs of move in, if they are met, handles the creation of new citizen
     */
//    ezt hivja majd a TimePassed es intezzi majd a Citizenet bekoltozeset.
    void handleMoveIn(){
        //random people will come to the city (bebtween 1-4 bot ends included)
        int randomNumber = (new Random().nextInt(4)) + 1;

        for(int i=1;i<=randomNumber;i++){
            ResidentialZone Rzone = hasFreeResidential();
            Workplace Wzone = hasFreeWorkplace();

            //satisfaction miatt nem koltozhet be <30  akkor nem koltozhet be. Eloszor lesz biztosan 50 szemely aki barhogy bekoltozik

            //ha van free residential zone es van industrial zone is akkor letre kell hozzni egy citizent.

            if(Rzone!=null && Wzone!=null && canMoveIn()){
                Citizen citizen = new Citizen(new Random().nextInt(42) + 18, Rzone, Wzone, this);
                citizens.add(citizen);
                Rzone.addCitizen(citizen);
                Wzone.addCitizen(citizen);
            }
        }
    }
    private boolean canMoveIn(){
        if (citizens.size() < guaranteedCitizens){
            return true;
        }
        else{
            return satisfaction() >= 30;
        }
    }

    void updateBalance(){
        int[] balance = {0, 0};
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
        if (funds < 0) daysInNegative++;
        else daysInNegative = Math.max(daysInNegative - 2 , 0);
    }

    /**
     * Helper method for move in, checks for available residential zones
     * @return A free residential zone, if available
     */
    ResidentialZone hasFreeResidential(){
        Coordinates coords;
        moveInGraph = new Graph(destroynodes);
        Coordinates coords2 = new Coordinates(mapHeight,mapWidth);
        buildingsAvailable(coords2,moveInGraph);
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if(this.map[i][j] instanceof ResidentialZone){
                    if(moveInGraph.BFS_movein(0,numberBuilding,new Coordinates(i,j))) {
                        if (((MainZone) this.map[i][j]).getCurrentCapacity() < ((MainZone) this.map[i][j]).getMaxCapacity()) {
                            if (citizens.size() > guaranteedCitizens) {
                                if (((ResidentialZone) this.map[i][j]).isElectricity()) {
                                    return (ResidentialZone) this.map[i][j];
                                }
                            } else {
                                return ((ResidentialZone) this.map[i][j]);
                            }
                        }
                    }
                }
            }
        }
        // System.out.println("residential");
        return null;
    }

    /**
     * Helper method for move in, checks for available workplace
     * @return A free workplace, if available
     */
    Workplace hasFreeWorkplace(){
        Coordinates coords;
        moveInGraph = new Graph(destroynodes);
        Coordinates coords2 = new Coordinates(mapHeight,mapWidth);
        buildingsAvailable(coords2,moveInGraph);
        Workplace industrialWorkplace = null;
        int workersInIndustrialZones = 0;
        Workplace serviceWorkplace = null;
        int workersInServiceZones = 0;
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if( (this.map[i][j] instanceof IndustrialZone) ) {
                    workersInIndustrialZones = workersInIndustrialZones + ((MainZone)this.map[i][j]).getCurrentCapacity();
                    coords = new Coordinates(i,j);
                    if(moveInGraph.BFS_movein(0,numberBuilding,coords)) {
                        if (((MainZone) this.map[i][j]).getCurrentCapacity() < ((MainZone) this.map[i][j]).getMaxCapacity()) {
                            industrialWorkplace = ((Workplace) this.map[i][j]);
                        }
                    }
                }
                if( (this.map[i][j] instanceof ServiceZone) ) {
                    workersInServiceZones = workersInServiceZones + ((MainZone)this.map[i][j]).getCurrentCapacity();
                    coords = new Coordinates(i,j);
                    if(moveInGraph.BFS_movein(0,numberBuilding,coords)) {
                        if (((MainZone) this.map[i][j]).getCurrentCapacity() < ((MainZone) this.map[i][j]).getMaxCapacity()) {
                            serviceWorkplace = ((Workplace) this.map[i][j]);
                        }
                    }
                }
            }
        }

        if(industrialWorkplace == null && serviceWorkplace == null ){
            return null;
        }else if(serviceWorkplace == null){
            return industrialWorkplace;
        }else if(industrialWorkplace == null){
            return serviceWorkplace;
        }else{
            if(workersInIndustrialZones >= workersInServiceZones){
                // System.out.println("Working in Service");
                return serviceWorkplace;
            }else{
                // System.out.println("Working in Industrial");
                return industrialWorkplace;
            }
        }
    }

    public int getElectricityProduction() {
        return electricityProduction;
    }


    public int getElectricityNeed() {
        return electricityNeed;
    }

    public double[] getEducatedCitizens(){
        double[] educatedCitizens = {0, 0, 0};
        for (Citizen c : citizens){
            if(c.education == EduLevel.BASIC) educatedCitizens[0]++;
            if(c.education == EduLevel.MID) educatedCitizens[1]++;
            if(c.education == EduLevel.HIGH) educatedCitizens[2]++;
        }
        if(citizens.size() > 0) {
            educatedCitizens[0] = educatedCitizens[0] / citizens.size() * 100;
            educatedCitizens[1] = educatedCitizens[1] / citizens.size() * 100;
            educatedCitizens[2] = educatedCitizens[2] / citizens.size() * 100;
        }
        return educatedCitizens;
    }

    public void timePassed(int days){
        int dateChange = date.DaysPassed(days);

        electricitySupply();
        electricityStats();

        calculateSatisfaction();
        for (int i = 0; i < days; i++) {
            // One day Passed!
            handleMoveIn();
            handleMoveOut();
            updateBalance();
        }
        if(dateChange > 0){
             //A month has passed!
            handleGraduation(); //ezt majd rakd eggyel lejjeb legyen evrol evre, igy csak khonaprol honapra
            if(dateChange > 1){
                for (int i = 0; i < citizens.size(); i++){
                    citizens.get(i).yearPassed();
                }
            }
        }
        // System.out.println("Elégedettség: " + satisfaction()); //debug
    }

    private void handleMoveOut() {
        if (satisfaction() >= 30) return;
        System.out.println("Lakosok: " + citizens.size());
        int toMoveOut = (int)Math.ceil((double)citizens.size() / 100);
        for (int i = 0; i < toMoveOut; i++) {
            citizens.get(new Random().nextInt(citizens.size())).moveOut();
        }
    }

    private void handleGraduation() {
        // Valahogy meghívja az egyetemek és ikolák handleGraduate metódusát random emberekkel, úgy hogy figyel a max %-ra

        ArrayList<Citizen> inNeedOfSchoolDegree = new ArrayList<>();
        ArrayList<Citizen> inNeedOfUniversitylDegree = new ArrayList<>();
        ArrayList<Citizen> hasAlreadyUniversityDegree = new ArrayList<>();
        for (Citizen citizen : this.citizens)
        {
            if(citizen.education == EduLevel.BASIC){
                inNeedOfSchoolDegree.add(citizen);
            }
            else if(citizen.education == EduLevel.MID){
                inNeedOfUniversitylDegree.add(citizen);
            }else if(citizen.education == EduLevel.HIGH){
                hasAlreadyUniversityDegree.add(citizen);
            }
        }


        if(citizens.size() > 0){
            double oneCitizenPercentage = 100d / citizens.size();
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if(this.map[i][j] instanceof School){

                        double percentageWithSchoolDegree = (inNeedOfUniversitylDegree.size() * 100d) / citizens.size();
                        //check if percentage is alright
                        if(percentageWithSchoolDegree < maxSchoolDegrees){

                            ArrayList<Citizen> graduatedFromSchool = new ArrayList<>();
                            while((graduatedFromSchool.size() <= (((School) this.map[i][j]).getMaxCapacity())) && (Math.ceil(percentageWithSchoolDegree) < maxSchoolDegrees)){

                                graduatedFromSchool.add(inNeedOfSchoolDegree.get(0));
                                inNeedOfSchoolDegree.remove(0);
                                percentageWithSchoolDegree = percentageWithSchoolDegree + oneCitizenPercentage;

                            }
                            // System.out.println("Ennyi szemely fog az iskolabol grarudalni");
                            // System.out.println(graduatedFromSchool.size());
                            ((School)this.map[i][j]).handleGraduation(graduatedFromSchool);
                        }

                    }else if(this.map[i][j] instanceof University){
                        double percentageWithUniversityDegree = (hasAlreadyUniversityDegree.size() * 100d) / citizens.size();

                        if(percentageWithUniversityDegree < maxUniversityDegrees){
                            ArrayList<Citizen> graduatedFromUniversity = new ArrayList<>();
                            while((graduatedFromUniversity.size() <= ((University) this.map[i][j]).getMaxCapacity()) && (Math.ceil(percentageWithUniversityDegree)<maxUniversityDegrees)){
                                if(inNeedOfUniversitylDegree.size() == 0){
                                    break;
                                }else{
                                    graduatedFromUniversity.add(inNeedOfUniversitylDegree.get(0));
                                    inNeedOfUniversitylDegree.remove(0);
                                    percentageWithUniversityDegree = percentageWithUniversityDegree + oneCitizenPercentage;
                                }


                            }
                            // System.out.println("Ennyi szemely fog az egyetemrol gradualni");
                            // System.out.println(graduatedFromUniversity.size());
                            ((University)this.map[i][j]).handleGraduation(graduatedFromUniversity);
                        }
                    }
                }
            }
        }

    }

    /**
     * This method handles the destruction of a building, spawning a grassy area in its place
     * @param coords The top left coords of the building destroyed
     * @return True/false depending on the success of destruction
     */
    public boolean destroy(Coordinates coords) {
        if (this.map[coords.getHeight()][coords.getWidth()] instanceof Road) {
            if(coords.getHeight() == startRoad.getHeight() && coords.getWidth() == startRoad.getWidth()){
                return false;
            }
            destroygraph = new Graph(destroynodes);
            // System.out.println(destroynodes);
            buildingsAvailable(coords,destroygraph);
            //System.out.println(destroyGraph);
            if(destroygraph.BFS(0,numberBuilding)){
                Environment environment = ((Road) this.map[coords.getHeight()][coords.getWidth()]).getFormerEnvironment();
                funds = funds + (((Road) this.map[coords.getHeight()][coords.getWidth()]).getPrice()/100*40);
                this.map[coords.getHeight()][coords.getWidth()] = null;
                this.map[coords.getHeight()][coords.getWidth()] = environment;
                destroynodes = destroynodes - 1;
                return true;
            }

            return false;
        }
        else if (this.map[coords.getHeight()][coords.getWidth()] instanceof Pole) {
            this.map[coords.getHeight()][coords.getWidth()] = null;
            this.map[coords.getHeight()][coords.getWidth()] = new Grass();
            return true;
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
                    funds = funds + (mainZone.getPrice()/100*40);
                    return true;
                }
            }
            if (mainZone instanceof Workplace) {
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
                    funds = funds + (mainZone.getPrice()/100*40);
                    return true;
                }
            }
            if (mainZone instanceof Infrastructure) {
                int width = mainZone.getWidth();
                int heigth = mainZone.getHeight();
                for (int i = mainZone.getCoordinates().getHeight(); i < mainZone.getCoordinates().getHeight() + heigth; i++) {
                    for (int j = mainZone.getCoordinates().getWidth(); j < mainZone.getCoordinates().getWidth() + width; j++) {
                        this.map[i][j] = null;
                        this.map[i][j] = new Grass();
                    }
                }
                funds = funds + (mainZone.getPrice()/100*40);
                destroynodes = destroynodes - 1;
                numberBuilding = numberBuilding - 1;

                return true;
            }


        }
        return true;
    }

    public void buildingsAvailable(Coordinates coords,Graph graph) {
        numberBuilding = 0;
        graph.addNode(0, true, new Coordinates(startRoad.getHeight(), startRoad.getWidth()));
        //System.out.println(startRoad.getHeight() + " " + startRoad.getWidth() + " id: " + destroyGraph.getNodeID(new Coordinates(startRoad.getHeight(),startRoad.getWidth())));
        int id = 1;

        //node hozzáadás
        for (int i = this.mapHeight - 1; i >= 0; i--) {
            for (int j = this.mapWidth - 1; j >= 0; j--) {
                if((i == startRoad.getHeight() && j == startRoad.getWidth()) || (i == coords.getHeight() && j == coords.getWidth())){
                    continue;
                }
                else if (this.map[i][j] instanceof Road) {
                    // System.out.println(i + " " + j + " id: " + id);
                    graph.addNode(id, true, new Coordinates(i, j));
                    id = id + 1;
                }
                else if (this.map[i][j] instanceof MainZone) {
                    numberBuilding++;
                    // System.out.println(i + " " + j + " id: " + id);
                    graph.addNode(id, false, new Coordinates(i, j));
                    id = id + 1;
                }
            }
        }

        for (int i = this.mapHeight - 1; i >= 0; i--) {
            for (int j = this.mapWidth - 1; j >= 0; j--) {
                if (graph.getNodeID(new Coordinates(i, j)) != -1 && (this.map[i][j] instanceof Road)) {
                    int first_id = graph.getNodeID(new Coordinates(i, j));

                    //bals alsó sarok, jobb alsó sarok
                    if (i == 0 && j == this.mapWidth - 1) {
                        if (this.map[i][j - 1] instanceof Road && graph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && graph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                    } else if (i == this.mapHeight - 1 && j == this.mapWidth - 1) {
                        if (this.map[i - 1][j] instanceof Road && graph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j - 1] instanceof Road && graph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                    }

                    //bal felső sarok, jobb felső sarok
                    else if (i == 0 && j == 0) {
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && graph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && graph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i + 1, j)));
                        }
                    } else if (i == this.mapHeight && j == 0) {
                        if (this.map[i - 1][j] instanceof Road && graph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id,graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j + 1] instanceof Road && graph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                    }

                    //alsó sor, felső sor
                    else if (i != 0 && i < this.mapHeight - 1 && j == this.mapWidth - 1) {
                        if (this.map[i - 1][j] instanceof Road && graph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j - 1] instanceof Road && graph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i + 1][j] instanceof Road && graph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id,graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                    } else if (i != 0 && i < this.mapHeight - 1 && j == 0) {
                        if (this.map[i - 1][j] instanceof Road && graph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && graph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && graph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i + 1, j)));
                        }
                    }

                    //jobb oszlop, bal oszlop
                    else if (i == this.mapHeight - 1 && j != 0 && j < this.mapWidth - 1) {
                        if (this.map[i][j - 1] instanceof Road && graph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i - 1][j] instanceof Road && graph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i][j + 1] instanceof Road && graph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                    } else if (i == 0 && j != 0 && j < this.mapWidth - 1) {
                        if (this.map[i][j - 1] instanceof Road && graph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && graph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && graph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j + 1)));
                        }
                    }


                    //nem sarok vagy szél
                    else {
                        if (this.map[i][j - 1] instanceof Road && graph.getNodeID(new Coordinates(i, j - 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j - 1)));
                        }
                        if (this.map[i][j - 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j - 1]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i + 1][j] instanceof Road || this.map[i + 1][j] instanceof MainZone) && graph.getNodeID(new Coordinates(i + 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i + 1, j)));
                        }
                        if (this.map[i + 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i + 1][j]).mainBuilding.getCoordinates()));
                        }
                        if ((this.map[i][j + 1] instanceof Road || this.map[i][j + 1] instanceof MainZone) && graph.getNodeID(new Coordinates(i, j + 1)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i, j + 1)));
                        }
                        if (this.map[i][j + 1] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i][j + 1]).mainBuilding.getCoordinates()));
                        }
                        if (this.map[i - 1][j] instanceof Road && graph.getNodeID(new Coordinates(i - 1, j)) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(new Coordinates(i - 1, j)));
                        }
                        if (this.map[i - 1][j] instanceof ZonePart && graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()) != -1) {
                            graph.addEdge(first_id, graph.getNodeID(((ZonePart) this.map[i - 1][j]).mainBuilding.getCoordinates()));
                        }
                    }


                }
            }
        }
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
        ArrayList<MainZone> boosted = new ArrayList<>();

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
                if(this.map[i][j] instanceof ResidentialZone || this.map[i][j] instanceof IndustrialZone || this.map[i][j] instanceof ServiceZone){
                    ((MainZone) this.map[i][j]).setSatisfactionBoost(value);
                    boosted.add((MainZone) this.map[i][j]);
                } else if (this.map[i][j] instanceof ZonePart){ //zonepart check, NOT usable
                    if (((ZonePart) this.map[i][j]).mainBuilding instanceof ResidentialZone
                            || ((ZonePart) this.map[i][j]).mainBuilding instanceof IndustrialZone || ((ZonePart) this.map[i][j]).mainBuilding instanceof ServiceZone) {
                        if (!boosted.contains(((ZonePart) this.map[i][j]).mainBuilding)) {
                            ((ZonePart) this.map[i][j]).mainBuilding.setSatisfactionBoost(value);
                            boosted.add(((ZonePart) this.map[i][j]).mainBuilding);
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
                if (this.map[i][j] instanceof Stadium && ((Stadium) this.map[i][j]).isElectricity()) modifySatisfactionBoost(new Coordinates(i,j), 9, 15, (Constructable) this.map[i][j]);
                else if (this.map[i][j] instanceof Police && ((Police) this.map[i][j]).isElectricity()) modifySatisfactionBoost(new Coordinates(i,j), 6, 10, (Constructable) this.map[i][j]);
            }
        }

    }

    /**
     * This method calculates the electricity supply throughout the city
     */
    public void electricitySupply(){
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if(this.map[i][j] instanceof PowerPlant){
                    ((MainZone) this.map[i][j]).setElectricity(true);
                }
                else if(this.map[i][j] instanceof MainZone){
                    ((MainZone) this.map[i][j]).setElectricity(false);
                }
            }
        }

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if(this.map[i][j] instanceof PowerPlant){

                    //preparations for the algorithm
                    Queue<Coordinates> visitedCoords = new LinkedList<>();
                    Queue<Coordinates> currentCoords = new LinkedList<>();
//                    System.out.println(visitedCoords.size());

                    currentCoords.add(new Coordinates(i,j));
                    currentCoords.add(new Coordinates(i,j+1));
                    currentCoords.add(new Coordinates(i,j+2));
                    currentCoords.add(new Coordinates(i,j+3));
                    currentCoords.add(new Coordinates(i+1,j+3));
                    currentCoords.add(new Coordinates(i+2,j+3));
                    currentCoords.add(new Coordinates(i+3,j+3));
                    currentCoords.add(new Coordinates(i+3,j+2));
                    currentCoords.add(new Coordinates(i+3,j+1));
                    currentCoords.add(new Coordinates(i+3,j));
                    currentCoords.add(new Coordinates(i+2,j));
                    currentCoords.add(new Coordinates(i+1,j));




                    visitedCoords.add(new Coordinates(i+1,j+1));
                    visitedCoords.add(new Coordinates(i+1,j+2));
                    visitedCoords.add(new Coordinates(i+2,j+1));
                    visitedCoords.add(new Coordinates(i+2,j+2));



                    //start of the algorithm
                    int electricityToGive = ((PowerPlant) this.map[i][j]).getElectricityProduction();

                    while (electricityToGive>0 && !currentCoords.isEmpty()){

                        //remove from queue and adding to already searched Tiles.
                        Coordinates currentTileCoords = currentCoords.remove();
                        visitedCoords.add(currentTileCoords);

                        int x = currentTileCoords.getHeight();
                        int y = currentTileCoords.getWidth();
                        // System.out.println(x+" "+y);

                        //it volt a gond, figyeld meg (most jo kell legyen)
                        if(visitedCoords.size() >= mapHeight*mapWidth+100){
//                            break;
                        }

                        if(!visitedCoords.contains(new Coordinates(x-1,y)) && !currentCoords.contains(new Coordinates(x-1,y)) && x>=1 && y>=0 && y<mapWidth && x<mapHeight && (this.map[x-1][y] instanceof MainZone || this.map[x-1][y] instanceof Pole || this.map[x-1][y] instanceof ZonePart)){
                            currentCoords.add(new Coordinates(x-1,y));
                        }if(!visitedCoords.contains(new Coordinates(x,y+1)) && !currentCoords.contains(new Coordinates(x,y+1)) && y<mapWidth-1 && y>=0 && x>=0 && x<mapHeight && (this.map[x][y+1] instanceof MainZone || this.map[x][y+1] instanceof Pole || this.map[x][y+1] instanceof ZonePart)){
                            currentCoords.add(new Coordinates(x,y+1));
                        }if(!visitedCoords.contains(new Coordinates(x+1,y)) && !currentCoords.contains(new Coordinates(x+1,y)) && x<mapHeight-1 &&  y<mapWidth && y>=0 && x>=0 &&  (this.map[x+1][y] instanceof MainZone || this.map[x+1][y] instanceof Pole || this.map[x+1][y] instanceof ZonePart)){
                            currentCoords.add(new Coordinates(x+1,y));
                        }if(!visitedCoords.contains(new Coordinates(x,y-1)) && !currentCoords.contains(new Coordinates(x,y-1)) && y>=1 && y<mapWidth && x>=0 && x<mapHeight && (this.map[x][y-1] instanceof MainZone || this.map[x][y-1] instanceof Pole || this.map[x][y-1] instanceof ZonePart)){
                            currentCoords.add(new Coordinates(x,y-1));
                        }

                        if(this.map[x][y] instanceof MainZone){
                            if(!(((MainZone)this.map[x][y]).isElectricity())){
                                if(electricityToGive>=((MainZone)this.map[x][y]).getElectricityNeed()){
                                    electricityToGive=electricityToGive -((MainZone)this.map[x][y]).getElectricityNeed();
                                    ((MainZone)this.map[x][y]).setElectricity(true);
                                }//itt ne csinalj semmit. A sorbol majd ugyis kijon.
                            }
                        }else if(this.map[x][y] instanceof ZonePart) {
                            MainZone mainZone = ((ZonePart)this.map[x][y]).getMainBuilding();
                            if(!(mainZone.isElectricity())){
                                if(electricityToGive>=mainZone.getElectricityNeed()){
                                    electricityToGive=electricityToGive -mainZone.getElectricityNeed();
                                    mainZone.setElectricity(true);
                                }//itt ne csinalj semmit. A sorbol majd ugyis kijon.
                            }
                        }





                    }


                    // System.out.println(visitedCoords.size());
                    // System.out.println("Ennyi aram maradt: "+electricityToGive);

                }
            }
        }
    }

    /**
     * This method calculates the data for the electricity produced and used
     */
    public void electricityStats() {
        this.electricityNeed = 0;
        this.electricityProduction = 0;
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if (this.map[i][j] instanceof PowerPlant) {
                    this.electricityProduction = this.electricityProduction + ((PowerPlant) this.map[i][j]).getElectricityProduction();
                }
                else if (this.map[i][j] instanceof MainZone) {
                    this.electricityNeed = this.electricityNeed + ((MainZone) this.map[i][j]).getElectricityNeed();
                }
            }
        }
    }

    /**
     * This method handles the upgrade of the three upgradeable zones (residential, industrial, service)
     * @param coords Coordinates of given building
     */
    public void upgrade(Coordinates coords){
        if (this.map[coords.getHeight()][coords.getWidth()] instanceof Road) {

        }
        else if (this.map[coords.getHeight()][coords.getWidth()] instanceof Pole) {

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
                if(((ResidentialZone) mainZone).getLevel() == 1){
                    ((ResidentialZone) mainZone).setLevel(2);
                    // System.out.println(((ResidentialZone) mainZone).getLevel());
                    ((ResidentialZone) mainZone).setCapacity(25);
                    funds = funds - 3000;
                }
                else if(((ResidentialZone) mainZone).getLevel() == 2){
                    ((ResidentialZone) mainZone).setLevel(3);
                    ((ResidentialZone) mainZone).setCapacity(50);
                    funds = funds - 8000;
                }
            }
            if (mainZone instanceof Workplace) {
                if(mainZone instanceof IndustrialZone || mainZone instanceof ServiceZone){
                    if(((Workplace) mainZone).getLevel() == 1){
                        ((Workplace) mainZone).setLevel(2);
                        // System.out.println(((Workplace) mainZone).getLevel());
                        ((Workplace) mainZone).setCapacity(30);
                        funds = funds - 5000;
                    }
                    else if(((Workplace) mainZone).getLevel() == 2){
                        ((Workplace) mainZone).setLevel(3);
                        ((Workplace) mainZone).setCapacity(55);
                        funds = funds - 10000;
                    }

                }
            }
            if (mainZone instanceof Infrastructure) {

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
    public Date getDate(){
        return date;
    }

    public int getCitizenslength(){return citizens.size();}

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public int getDaysInNegative() {
        return daysInNegative;
    }
}
