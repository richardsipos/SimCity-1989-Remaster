package Model.Map;

import Model.Citizen;

import java.awt.*;
import java.util.ArrayList;

public class MainZone {
    private ArrayList<ZonePart> parts;
    private boolean moveln = false;
    private boolean isFree = false;
    private int capacity = 0;
    private int satisfaction = 0;
    private boolean hasPoliceNearby = false;
    private ArrayList<Citizen> citizens;
    private int level;
    private ArrayList<Image> possibleImages;





    private int electricity(){
        return 1;
    }

    public void upgrade(){}
    public int getSatisfaction(){
        return 1;
    }


}
