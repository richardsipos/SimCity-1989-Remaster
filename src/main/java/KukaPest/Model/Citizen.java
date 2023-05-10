package KukaPest.Model;

import KukaPest.Model.Map.ResidentialZone;
import KukaPest.Model.Map.Workplace;

import java.io.Serializable;
import java.util.Random;

public class Citizen implements java.io.Serializable {
    int age;
    boolean isPensioner = false;
    int pension = 0;
    int chanceToDie = 0;
    ResidentialZone home;
    Workplace workPlace;
    City city;
    int TAX_FREQUENCY = 365;


    Citizen(int age, ResidentialZone home, Workplace workPlace, City city){
        this.home = home;
        this.workPlace = workPlace;
        this.age = age;
        this.city = city;
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
        total += home.getSatisfactionBoost();
        if (!isPensioner) total += workPlace.getSatisfactionBoost();
        if (total < 0) total = 0;
        if (total > 100) total = 100;
        return total;
    }
    public int payTax(){
        return isPensioner ? (pension / TAX_FREQUENCY * -1) : tax();
    }
    private int tax(){
        if(workPlace == null){
            return 2;
        }
        else if (workPlace.isElectricity()) {
            return 7;
        }else {
            return 5;
        }
    }

    public void yearPassed(){
        this.age++;
        if (age >= 65) {
            if (!isPensioner){
                // The citizen becomes pensioner
                isPensioner = true;
                workPlace.removeCitizen(this);
                this.workPlace = null;
            }
            else{
                tryToKill();
            }
        }
        else if (age >= 45){
            if(pension == 0){
                this.pension = tax() * TAX_FREQUENCY / 2;
            }
            else{
                int thisYearsTax = tax() * TAX_FREQUENCY / 2;
                this.pension = (pension + thisYearsTax) / 2;
            }
        }
    }

    private void tryToKill() {
        int rolledChance = new Random().nextInt(100);
        if(chanceToDie < rolledChance){
            chanceToDie += 5;
        }
        else{
            die();
        }
    }

    private void die() {
        ResidentialZone Rzone = city.hasFreeResidential();
        Workplace Wzone = city.hasFreeWorkplace();
        //ha van free residential zone es van industrial zone is akkor letre kell hozzni egy citizent.
        if(Wzone != null){
            this.age = 18;
            this.home = Rzone == null ? home : Rzone;
            this.workPlace = Wzone;
            this.pension = 0;
            this.isPensioner = false;
            this.chanceToDie = 0;
        }
        else{
            this.city.citizens.remove(this);
        }
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "age=" + age +
                ", isPensioner=" + isPensioner +
                ", pension=" + pension +
                ", chanceToDie=" + chanceToDie +
                ", home=" + home +
                ", workPlace=" + workPlace +
                '}';
    }
}
