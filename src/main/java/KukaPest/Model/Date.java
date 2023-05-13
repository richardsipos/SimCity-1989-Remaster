package KukaPest.Model;

import java.io.Serializable;

public class Date implements java.io.Serializable {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int DaysPassed(int days){
        if(days+this.day>30){
            this.day=((this.day+days)%30);
            if(this.month==12){
                this.month=1;
                this.year=this.year+1;
                return 2;
            }else{
                this.month=this.month+1;
                return 1;
            }
        }
        this.day=this.day+days;
        return 0;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "Date{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
