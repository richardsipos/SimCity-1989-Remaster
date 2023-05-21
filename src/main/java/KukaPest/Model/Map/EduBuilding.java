package KukaPest.Model.Map;

import KukaPest.Model.Citizen;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Helper.EduLevel;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class EduBuilding extends Infrastructure implements java.io.Serializable {
    private int lastGraduated = 0;
    public EduBuilding(int width, int height, int maxCapacity, int costOfBuilding, Coordinates coordinates, int upKeep, int electricityNeed) {
        super(width, height, maxCapacity,costOfBuilding, coordinates, upKeep, electricityNeed);
    }

    public void handleGraduation(ArrayList<Citizen> citizens){
        for (Citizen c : citizens) {
            if (c.getEducation() != targetEduLevel())
                c.setEducation(targetEduLevel());
            else
                throw new RuntimeException("Bad citizens given to edu building");
        }
        lastGraduated = citizens.size();

    }

    @Override
    public HashMap<String, String> getStats(){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("Capacity", "" + super.getMaxCapacity());
        ret.put("Graduated", "" + lastGraduated);
        ret.put("Upkeep", "" + super.getUpKeep());
        return ret;
    }

    abstract protected EduLevel targetEduLevel();
}
