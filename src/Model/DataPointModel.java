package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPointModel {
    /**
     * This class is a model that represents an Data Point object.
     * -------------------------------------------------------------
     * There are 5 necessary attributes in the this data point model.
     * location can be country or continent
     * date is the date when the data is recorded
     * cases is a positive case recorded in a date
     * death is number of deaths recorded in a date
     * vaccinated is a number of up to people vaccinated till that date
     */
    private final String location;
    private final Date date;
    private final int cases;
    private final int deaths;
    private final int vaccinated;


    /**
     * Constructor for the Data Point Model to create Data Point model when necessary
     * @param location location of data recorded
     * @param date date when the data recorded
     * @param cases cases number of data recorded
     * @param deaths deaths number  of data recorded
     * @param vaccinated vaccinated number of data recorded
     */
    public DataPointModel(String location, Date date, int cases, int deaths, int vaccinated) {
        this.location = location;
        this.date = date;
        this.cases = cases;
        this.deaths = deaths;
        this.vaccinated = vaccinated;
    }

    /**
     * Method to convert a type of Date date to String date
     * @param date to be converted
     * @return a string of date in string type with specific format
     */
    private String changeToStringDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);
    }

    /**
     * Method to get location
     * @return a string of location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Method to get Date but in String type
     * @return a String of date
     */
    public Date getDateType(){
        return this.date;
    }
    public String getDate() {
        return changeToStringDate(this.date);
    }
    /**
     * Method to get number of vaccinated people up to date
     * @return an integer of vaccinated people
     */
    public int getVaccinated() {
        return vaccinated;
    }

    /**
     * Method to get number of deaths
     * @return an integer of deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Method to get positive cases
     * @return an integer of positive cases
     */
    public int getCases() {
        return cases;
    }

    /**
     * Method to get toString content
     * @return a String of data point model content
     */
    public String toString() {
        return   " " + location + " " + date + " " + cases + " " + deaths + " " + vaccinated ;
    }

    /**
     * Method to print data point content
     */
    public void printDataPoint(){
        System.out.printf("Location: %s,Date: %s, Cases: %d, Deaths: %d, Vaccinated: %d \n",location,changeToStringDate(date),cases,deaths,vaccinated);
    }


}
