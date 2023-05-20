package KukaPest.Model;

import KukaPest.Model.Helper.EduLevel;
import KukaPest.Model.Map.ResidentialZone;
import KukaPest.Model.Map.Workplace;

import java.util.Random;

public class Citizen implements java.io.Serializable {
    int age;
    boolean isPensioner = false;
    int pension = 0;
    int chanceToDie = 0;
    EduLevel education = EduLevel.BASIC;
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
            total += Math.min(home.getSatisfactionBoost(), 30);
        }else{
            if(home.getSatisfactionBoost()!=0){
                total += Math.min(home.getSatisfactionBoost() / 2, 30);
            }
        }
        if(!isPensioner){
            if (workPlace.isElectricity()){
                total += Math.min(workPlace.getSatisfactionBoost(), 30);
            }else{
                total += Math.min(workPlace.getSatisfactionBoost() / 2, 30);
            }
        }

        total -= city.getDaysInNegative() * 0.3;

        if (total < 0) total = 0;
        if (total > 100) total = 100;
        return total;
    }

    /**
     * This method decides whether the given citizen needs to pay taxes or is a pensioner and returns accordingly:
     * @return negative value if citizen gets pension, positive if pays tax
     */
    public int payTax(){
        return isPensioner ? (pension / TAX_FREQUENCY * -1) : tax();
    }

    /**
     * This method calculates the amount of tax this citizen pays
     * @return int, amount of tax
     */
    private int tax(){
        if(workPlace == null){
            return 2;
        }
        else if (workPlace.isElectricity()) {
            return 7 * education.salaryModifier();
        }else {
            return 5 * education.salaryModifier();
        }
    }

    /**
     * This method ages the citizen if a year passes, and settles age related matters (tax, pension, death)
     */
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

    /**
     * This method rolls the dice whther the citizen dice with increasing odds.
     */
    private void tryToKill() {
        int rolledChance = new Random().nextInt(100);
        if(chanceToDie < rolledChance){
            chanceToDie += 5;
        }
        else{
            die();
        }
    }

    /**
     * This method handles the death of a citizen and an arrival of a new one
     */
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
            moveOut();
        }
    }

    public void moveOut(){
        if(isPensioner) return;
        System.out.println("Move out: " + this);
        boolean removedFromHome;
        boolean removedFromWork;
        removedFromHome = this.home.removeCitizen(this);

        if(removedFromHome){
            removedFromWork = this.workPlace.removeCitizen(this);
            if(removedFromHome && removedFromWork){
                this.city.citizens.remove(this);
            }
        }
        System.out.println("Vegigment a delete");

    }

    public EduLevel getEducation() {
        return education;
    }

    public void setEducation(EduLevel education) {
        this.education = education;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Citizen c){
            return c.age == this.age && c.education == this.education
                    && c.pension == this.pension;
        }
        return false;
    }
}
