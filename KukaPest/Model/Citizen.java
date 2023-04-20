package Model;

import Model.Map.IndustrialZone;
import Model.Map.ResidentialZone;

public class Citizen {

    ResidentialZone home;
    IndustrialZone workPlace;

    Citizen(ResidentialZone home,IndustrialZone workPlace){
        this.home = home;
        this.workPlace = workPlace;
    }

    /**
     * This method calculates satisfaction value for an individual Citizen
     * @return Returns total satisfaction of citizen, which is equal to
     *         base + home boost + workplace boost
     */
    public int satisfaction() {
        int total = 50;
        total += home.getSatisfactionBoost();
        total += workPlace.getSatisfactionBoost();
        if (total < 0) total = 0;
        if (total > 100) total = 100;
        return total;
    }
    public int payTax(){
        return workPlace == null ? 2 : 5;
    }

}
