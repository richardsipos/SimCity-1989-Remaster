package KukaPest.Model;

import KukaPest.Model.Helper.EduLevel;
import KukaPest.Model.Map.ResidentialZone;
import KukaPest.Model.Map.Workplace;

import java.util.Random;

import static java.lang.Math.*;

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
    int distanceWorkplaceAndHome;

    Citizen(int age, ResidentialZone home, Workplace workPlace, City city) {
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
        if (this.home.isElectricity()) {
            total += Math.min(this.home.getSatisfactionBoost(), 30);
        } else if (this.home.getSatisfactionBoost() != 0) {
            total += Math.min(this.home.getSatisfactionBoost() / 2, 30);
        }
        if (!this.isPensioner) {
            if (this.workPlace.isElectricity()) {
                total += Math.min(this.workPlace.getSatisfactionBoost(), 30);
            } else if (this.workPlace.getSatisfactionBoost() != 0) {
                total += Math.min(this.workPlace.getSatisfactionBoost() / 2, 30);
            }
        }
        if (distanceWorkplaceAndHome <= 5) {
            total -= 0;
        }
        else if (distanceWorkplaceAndHome <= 8) {
            total -= 1;
        }
        else if (distanceWorkplaceAndHome <= 10) {
            total -= 3;
        }
        else if (distanceWorkplaceAndHome <= 15) {
            total -= 5;
        }
        else {
            total -= 10;
        }

        total -= this.city.getDaysInNegative() * 0.3;

        if (total < 0) total = 0;
        if (total > 100) total = 100;
        return total;
    }

    /**
     * This method decides whether the given citizen needs to pay taxes or is a pensioner and returns accordingly:
     * @return negative value if citizen gets pension, positive if pays tax
     */
    public int payTax() {
        return this.isPensioner ? (this.pension / this.TAX_FREQUENCY * -1) : tax();
    }

    /**
     * This method calculates the amount of tax this citizen pays
     * @return int, amount of tax
     */
    private int tax() {
        if (this.workPlace == null) {
            return 2;
        }
        else if (this.workPlace.isElectricity()) {
            return 7 * this.education.salaryModifier();
        } else {
            return 5 * this.education.salaryModifier();
        }
    }

    /**
     * This method ages the citizen if a year passes, and settles age related matters (tax, pension, death)
     */
    public void yearPassed() {
        this.age++;
        if (this.age >= 65) {
            if (!this.isPensioner) {
                // The citizen becomes pensioner
                this.isPensioner = true;
                this.workPlace.removeCitizen(this);
                this.workPlace = null;
            }
            else {
                tryToKill();
            }
        }
        else if (this.age >= 45) {
            if (this.pension == 0) {
                this.pension = tax() * this.TAX_FREQUENCY / 2;
            }
            else {
                int thisYearsTax = tax() * this.TAX_FREQUENCY / 2;
                this.pension = (this.pension + thisYearsTax) / 2;
            }
        }
    }

    /**
     * This method rolls the dice whether the citizen dice with increasing odds.
     */
    private void tryToKill() {
        int rolledChance = new Random().nextInt(100);
        if (this.chanceToDie < rolledChance) {
            this.chanceToDie += 5;
        }
        else {
            die();
        }
    }

    /**
     * This method handles the death of a citizen and an arrival of a new one
     */
    private void die() {
        ResidentialZone Rzone = this.city.hasFreeResidential();
        Workplace Wzone = this.city.hasFreeWorkplace();
        if (Wzone != null) {
            this.age = 18;
            this.home = (Rzone == null) ? this.home : Rzone;
            this.workPlace = Wzone;
            this.pension = 0;
            this.isPensioner = false;
            this.chanceToDie = 0;
        }
        else {
            moveOut();
        }
    }

    public void moveOut() {
        boolean removedFromHome;
        boolean removedFromWork;
        removedFromHome = this.home.removeCitizen(this);
        if (removedFromHome) {
            removedFromWork = this.workPlace.removeCitizen(this);
            if (removedFromWork) {
                this.city.citizens.remove(this);
            }
        }
    }

    public EduLevel getEducation() {
        return this.education;
    }

    public boolean getPensioner() { return isPensioner; }
    public int getAge() { return age; }

    public void setEducation(EduLevel education) {
        this.education = education;
    }

    public void setDistanceWorkplaceAndHome() {
        int first, second;
        first = (int) pow((home.getCoordinates().getHeight() - workPlace.getCoordinates().getHeight()),2);
        second = (int) pow((home.getCoordinates().getWidth() - workPlace.getCoordinates().getWidth()),2);
        this.distanceWorkplaceAndHome = (int) sqrt(first + second);
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "age=" + this.age +
                ", isPensioner=" + this.isPensioner +
                ", pension=" + this.pension +
                ", chanceToDie=" + this.chanceToDie +
                ", home=" + this.home +
                ", workPlace=" + this.workPlace +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Citizen c) {
            return c.age == this.age && c.education == this.education
                    && c.pension == this.pension;
        }
        return false;
    }
}
