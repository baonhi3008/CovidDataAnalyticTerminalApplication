package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPointModel {
    private final String  location;
    private final Date date;
    private final int cases;
    private final int deaths;
    private final int vaccinated;

    public int getVaccinated() {
        return vaccinated;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getCases() {
        return cases;
    }

    public DataPointModel(String location, Date date, int cases, int deaths, int vaccinated) {
        this.location = location;
        this.date = date;
        this.cases = cases;
        this.deaths = deaths;
        this.vaccinated = vaccinated;
    }

    private String changeToStringDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return changeToStringDate(this.date);
    }

    public String toString() {
        return   " " + location + " " + date + " " + cases + " " + deaths + " " + vaccinated ;
    }

    public void printDataPoint(){
        System.out.printf("Location: %s,Date: %s, Cases: %d, Deaths: %d, Vacinated: %d \n",location,changeToStringDate(date),cases,deaths,vaccinated);
    }

}
