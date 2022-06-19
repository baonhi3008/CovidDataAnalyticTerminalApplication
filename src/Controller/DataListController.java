package Controller;

import Model.DataPointModel;
import PromptManager.PromptManager;
import Utility.*;
import View.DataListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utility.PrintScreen.print;
import static Utility.PrintScreen.println;
import static Utility.ShowOptionMenu.*;

public class DataListController {
    /**
     * This class is designed to create Data Point Model List and update the Data Point List View, following the design pattern of MVC
     *  It controls the data flow into model object and updates the view whenever data changes. It keeps view and model separate.
     */

    // List of attributes for the DataListController
    private static final String fileNameFinal = "src/covid_data.csv";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public static Scanner scanner = new Scanner(System.in);
    private static final ShowOptionMenu view = new ShowOptionMenu();
    private final DataListView dataListView;
    private ArrayList<DataPointModel> dataPointModels;
    private final PromptManager promptManager = new PromptManager();
    private final List<String[]> countryDataList;

    public DataListController() {
        dataPointModels= new ArrayList<DataPointModel>();
        dataListView = new DataListView();
        countryDataList = new ArrayList<String[]>();
    }

    /**
     *
     * @return List of String[] of raw input from csv file
     * @throws Exception if there is any error
     */
    private static List<String[]> loadDataPoints() throws Exception {
        List<String> lines = FileHandler.readFile(fileNameFinal);
        lines.remove(0);
        List<String[]> rawDataPointList = new ArrayList<>();
        for (String line:lines) {
            String[] data = line.split(",");
            for (int i = 0; i<data.length; i++ ) {
                if (data[i].equals("")) {
                    data[i] = "0"; }
            }
            if (data.length < 8){
                String[] newArray = new String[8];
                System.arraycopy(data, 0, newArray, 0, data.length);
                newArray[6] = "0";
                newArray[7] = "0";
                data = newArray;
            }
            rawDataPointList.add(data);
        }
        return rawDataPointList;
    }

    /**
     *
     * @param s a string of date taken from user input
     * @param day a number of days from a start date taken from user input
     * @return a new end date that is calculated from start date and number of date
     * @throws ParseException if the program cannot recognize the format, it will throws exception
     */

    private static Date calculateEndDate(Date s, int day) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(s);
        c.add(Calendar.DATE, day);  // number of days to add
        s = c.getTime();
        return s;
    }

    /**
     *
     * @param s a string of date taken from user input
     * @param day a number of days to end date taken from user input
     * @return a new start date that is calculated from end date and number of date
     * @throws ParseException if the program cannot recognize the format, it will throws exception
     */
    private static Date calculateStartDate(Date s, int day) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(s);
        c.add(Calendar.DATE,-day);  // number of days to add
        s = c.getTime();
        return s;
    }


    /**
     * With a standard united form 1 week = 7 days the method receives and return following values
     * @param numWeek number of week in integer type to calculate days to pass in calculateEndDate and calculateStartDate
     * @return number of days calculated from corresponding number of weeks
     */
    private static int calculateWeektoDate(int numWeek){
        return numWeek *7;
    }

    /**
     *
     * @param dateString This method used to change the format of type String of a Date input by user to Date type to calculate date
     * @return A date from a String with Date format
     * @throws ParseException if there is format error
     */
    public Date changeTypeOfDateString(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.parse(dateString);
    }

    /**
     * Method to create list of country
     * @param rawList this is the list of raw data taken from the loadData method and File Handler class
     * @return a List of String that contains all available countries.
     */
    private static List<String> getCountryList(List<String[]> rawList){
        ArrayList<String> countryList = new ArrayList<>();
        for (String[] data: rawList) {
            String country = data[2];
            if (!countryList.contains(country) && isAContinent(country)){
                countryList.add(country);
            }
        }
        return  countryList;
    }

    /**
     * Method to check whether string is a country or a continent
     * @param country string of country to be compare
     * @return a boolean value if the string is continent or country
     */
    private static boolean isAContinent(String country){
        String[] strings = {"Asia", "0","Europe","Africa","North America", "South America", " Oceania","World"};
        boolean flag = true;
        for (String continent: strings) {
            if(country.equals(continent)){
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     Method to create list of continents
     * @param rawList this is the list of raw data taken from the loadData method and File Handler class
     * @return a List of String that contains all available continents.
     */
    private static List<String> getContinentList(List<String[]> rawList){
        ArrayList<String> continentList = new ArrayList<>();
        for (String[] data: rawList) {
            String continent = data[1];
            String country = data[2];

            if (!continentList.contains(continent)){
                continentList.add(continent);
            }
            else if(!isAContinent(country)){
                continentList.add(country);
            }
        }
        ArrayList<String> finalList = new ArrayList<>();
        for (String continent: continentList) {
            if (!finalList.contains(continent)){
                finalList.add(continent);
            }
        }

        return finalList;
    }

    /**
     * Method to create a map from a list of country or continent
     * @param countryList list of country to be mapped
     * @return a map of country / continents as value and int as key for user to choose from
     */
    private static Map<Integer,String> loadLocationList(List<String> countryList) {
        HashMap<Integer,String> countryMap = new HashMap<>();
        for (int i = 0; i < countryList.size() ; i++) {
            countryMap.put(i,countryList.get(i));
        }
        return countryMap;
    }

    /**
     * Method to get one country from list based on user choose
     * @param countryMap input as a map with values
     * @return a String of location taken from corresponding key input by user
     */
    private  String getOneLocation(Map<Integer,String> countryMap) throws Exception {
        String country = "";
        System.out.println("Please select a location: ");
        System.out.print("Your choice_");
        int countryNum = promptManager.choiceMenuWithValidOption(224,0);
        for (int key: countryMap.keySet()) {
            if (key==countryNum){
                country+=countryMap.get(key);
            }
        }
        return country;
    }

    /**
     * Method to create a list of data point model from a country inputted by user
     * @param location String of location to make list from
     * @return a list of data point model of that country
     * @throws Exception if there is any error
     */
    public List<String[]> getDataFromLocation(String location) throws Exception {
        for (String[] data: loadDataPoints()) {
            if (data[2].equals(location) & !countryDataList.contains(data)){
                countryDataList.add(data);
            }
        }
        return countryDataList;
    }

    /**
     * Method to create a list of string to display from user choice
     * @param rawList this is the list of raw data taken from the loadData method and File Handler class
     * @return a list of String contains all information to be displayed
     * @throws Exception if there is any error
     */
    private List<String> getLocationTypeToDisplay(List<String[]> rawList) throws Exception {
        List<String> listOfLocation = new ArrayList<>();
        System.out.print("Which type of location you want to display\n 1. Country \n 2. Continent \n Your choice_ ");
        int option = promptManager.choiceMenuWithValidOption(2,1);
        switch (option) {
            case 1 -> listOfLocation = getCountryList(rawList);
            case 2 -> listOfLocation =  getContinentList(rawList);
        }
        return  listOfLocation;
    }

    /**
     * Method to create a list of data extracted from a list of raw data
     * @return array list of data point model that satisfy many criteria from a user input
     * @throws Exception if there is any error
     */

    public ArrayList<DataPointModel> prompNewList() throws Exception {
        String startDateString, endDateString;
        Date startDate, enDate;
        List<String[]> rawList = loadDataPoints();
        List<String> locationList = getLocationTypeToDisplay(rawList);
        Map<Integer,String> countryMap = loadLocationList(locationList);
        printCountryList(countryMap);
        String location = getOneLocation(countryMap);
        List<String[]> countryDataList = getDataFromLocation(location);
        String firstDateRange = countryDataList.get(0)[3];
        String lastDateRange = countryDataList.get(countryDataList.size()-1)[3];
        System.out.printf("Valid start date %s and last date %s  in %s: \n",firstDateRange,lastDateRange,location);
        optionSpecifyTimeRangeMenu();
        try {

            int option = promptManager.choiceMenuWithValidOption(3,1) ;
            switch (option) {
                case 1 -> {

                    print("Enter start date (MM/dd/yyyy): ");
                    startDate = promptManager.isInRangeDateInput(firstDateRange,lastDateRange);
                    print("Enter end date (MM//dd/yyyy): ");
                    enDate = promptManager.isInRangeDateInput(firstDateRange,lastDateRange);
                    dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                    return dataPointModels;
                }
                case 2 -> {
                    System.out.println("Enter start date (MM/dd/yyyy): ");
                    startDate = promptManager.isInRangeDateInput(firstDateRange,lastDateRange);

                    System.out.println("1. Number of week from start date");
                    System.out.println("2. Number of days from start date");
                    int optionDW = promptManager.choiceMenuWithValidOption(2,1);

                    switch (optionDW){
                        case 1 ->{
                            System.out.println("Enter number of weeks from start date: ");
                            int weeks = scanner.nextInt();
                            scanner.nextLine();
                            int days  = calculateWeektoDate(weeks);
                            enDate = calculateEndDate(startDate,days);
                            dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                        }
                        case 2 -> {
                            System.out.println("Enter number of days from start date: ");
                            int days = scanner.nextInt();
                            scanner.nextLine();
                            enDate = calculateEndDate(startDate,days);
                            dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                        }
                    }
                        return dataPointModels;
                }
                case 3 -> {
                    System.out.println("Enter the up to (end date - MM/dd/yyyy): ");
                    enDate = promptManager.isInRangeDateInput(firstDateRange,lastDateRange);
                    System.out.println("1. Number of weeks to end date:  ");
                    System.out.println("2. Number of days to end date:  ");
                    int optionDW = promptManager.choiceMenuWithValidOption(2,1);

                    switch (optionDW) {
                        case 1 ->{
                            System.out.println("Enter number of weeks to end date: ");
                            int weeks = scanner.nextInt();
                            scanner.nextLine();
                            int days  = calculateWeektoDate(weeks);
                            startDate = calculateStartDate(enDate,days);
//                            enDate = changeTypeOfDateString(endDateString);
                            dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                        }
                        case 2 ->{
                            System.out.println("Enter number of days to end date: ");
                            int days = scanner.nextInt();
                            scanner.nextLine();
                            startDate = calculateStartDate(enDate,days);
                            dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                        }
                    }
                    return dataPointModels;
                }
                default -> {
                    print("Invalid option.");
                    return null;
                }
            }
        } catch (Exception e) {
            println("Error?!");
            print(e);
            return null;
        }
    }

    /**
     * Method to create an array list of data point model, not interacting with user input. This method will be pass to prompNewList,
     * the previous method is create with param in secret
     * @param list of String[] contain of wanted country
     * @param startDate a start date of time range
     * @param endDate a end date of time range
     * @param location a location that user want to show
     * @return a list of data point model contains wanted criteria
     * @throws Exception if there is any error
     */
    private ArrayList<DataPointModel> createListOfDPM(List<String[]> list,Date startDate, Date endDate,String location) throws Exception {
        ArrayList<DataPointModel> dataPointModels = new ArrayList<>();
        for (String[] data : list) {
            Date test = changeTypeOfDateString(data[3]);
            String locationCheck = data[2];
            if (checkDate(test,startDate,endDate) && locationCheck.equals(location) ){
                DataPointModel dataPoint = new DataPointModel(data[2],changeTypeOfDateString(data[3]), Integer.parseInt(data[4]),
                        Integer.parseInt(data[5]),Integer.parseInt(data[6]));
                dataPointModels.add(dataPoint);
            }
        }
        return removeDuplicates(dataPointModels);
    }

    /**
     * Method to check if a date is in the wanted range
     * @param testDate a date to be compare
     * @param startDate a start date of time range
     * @param endDate a end date of time range
     * @return whether a test date is in the wanted range or not
     */
    private static boolean checkDate(Date testDate, Date startDate, Date endDate) {
        if (testDate.after(startDate) && testDate.before(endDate)) {
            return true;
        } else if (testDate.equals(startDate)) {
            return true;
        } else return testDate.equals(endDate);
    }

    /**
     * Methid ti sort array list of object
     * @param list list of object to be sorted
     * @param <T> object inside a list
     * @return a sorted array list of old list
     */
    private static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }


    /**
     * Method to update view from a DataPointView. This is created to follow MVC design pattern.
     */
    public void updateView(){
       dataListView.printDataPointList(dataPointModels);
    }



}
