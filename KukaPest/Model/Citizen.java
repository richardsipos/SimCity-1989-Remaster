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

}
