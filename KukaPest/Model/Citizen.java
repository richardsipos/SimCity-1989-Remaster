package Model;

import Model.Map.ResidentialZone;
import Model.Map.Workplace;

public class Citizen {

    ResidentialZone home;
    Workplace workPlace;

    Citizen(ResidentialZone home,Workplace workPlace){
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
        if (home.isElectricity()){
            total += home.getSatisfactionBoost();
        }else{
            if(home.getSatisfactionBoost()!=0){
                total += home.getSatisfactionBoost()/2;
            }
        }

        if (workPlace.isElectricity()){
            total += workPlace.getSatisfactionBoost();
        }else{
            if(workPlace.getSatisfactionBoost()!=0){
                total += workPlace.getSatisfactionBoost()/2;
            }
        }
        if (total < 0) total = 0;
        if (total > 100) total = 100;
        return total;
    }
    public int payTax(){
        if(workPlace == null){
            return 2;
        }
        else if (workPlace.isElectricity()) {
            return 7;
        }else {
            return 5;
        }
//        return workPlace == null ? 2 : 5;
    }

}
