package Controller;

import Model.DataPointModel;
import PromptManager.PromptManager;
import Utility.*;
import View.DataPointView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utility.PrintScreen.print;
import static Utility.PrintScreen.println;
import static Utility.ShowOptionMenu.User_Input_PrintScreen;
import static Utility.ShowOptionMenu.printCountryList;

public class DataListController {
    // This class is designed to create Data Point Model List and update the Data Point List View, following the design pattern of MVC

    //List of Attributes of Data List Controller
    private static final String fileNameFinal = "src/covid_data.csv";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public static Scanner scanner = new Scanner(System.in);
    private static final ShowOptionMenu view = new ShowOptionMenu();
    private final DataPointView dataPointView;
    private ArrayList<DataPointModel> dataPointModels;
    private PromptManager promptManager = new PromptManager();

    public DataListController() {
        dataPointModels= new ArrayList<DataPointModel>();
        dataPointView = new DataPointView();
    }


    /**
     *
     * @return List of String[] of raw input from csv file
     * @throws Exception
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
    private static Date calculateEndDate(String s, int day) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(s));
        c.add(Calendar.DATE, day);  // number of days to add
        s = sdf.format(c.getTime());
        return sdf.parse(s);

    }

    /**
     *
     * @param s a string of date taken from user input
     * @param day a number of days to end date taken from user input
     * @return a new start date that is calculated from end date and number of date
     * @throws ParseException if the program cannot recognize the format, it will throws exception
     */
    private static Date calculateStartDate(String s, int day) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(s));
        c.add(Calendar.DATE,-day);  // number of days to add
        s = sdf.format(c.getTime());
        return sdf.parse(s);
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
    private  static Date changeTypeOfDateString(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.parse(dateString);
    }
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
    private static boolean isAContinent(String country){
        String[] strings = {"Asia", "0","Europe","Africa","North America", "South America", " Oceania "};
        boolean flag = true;
        for (String continent: strings) {
            if(country.equals(continent)){
                flag = false;
                break;
            }

        }
        return flag;
    }
    private static List<String> getContinentList(List<String[]> rawList){
        ArrayList<String> continentList = new ArrayList<>();

        for (String[] data: rawList) {
            String country = data[1];
            if (!continentList.contains(country)){
                continentList.add(country);
            }
        }
        return continentList;
    }
    private static Map<Integer,String> loadLocationList(List<String> countryList) throws Exception {

        HashMap<Integer,String> countryMap = new HashMap<Integer,String>();

        for (int i = 0; i < countryList.size() ; i++) {
            countryMap.put(i,countryList.get(i));
        }

        return countryMap;

    }

    private static String getOneLocation(Map<Integer,String> countryMap){
        String country = "";
        System.out.println("Please select a country: ");
        int countryNum = scanner.nextInt();
        scanner.nextLine();
        for (int key: countryMap.keySet()) {
            if (key==countryNum){
                country+=countryMap.get(key);
            }
        }
        return country;
    }

    private static List<String[]> getDataFromLocation(String location) throws Exception {
        List<String[]> countryData = new ArrayList<>();
        for (String[] data: loadDataPoints()) {
            if (data[2].equals(location) & !countryData.contains(data)){
                countryData.add(data);
            }
        }
        return countryData;
    }
    private List<String> getLocationTypeToDisplay(List<String[]> rawList) throws Exception {
        System.out.println("Which type of location you want to display\n 1. Country \n 2. Location \n. Your choice_ ");
        int option = promptManager.choiceMenuWithValidOption(3);
        if (option==1){
            return getCountryList(rawList);
        }
        else {
            return  getContinentList(rawList);
        }
    }
    public ArrayList<DataPointModel> prompNewList() throws Exception {
        String startDateString, endDateString;
        Date startDate, enDate;
        List<String[]> rawList = loadDataPoints();
        List<String> locationList = getLocationTypeToDisplay(rawList);
        Map<Integer,String> countryMap = loadLocationList(locationList);
        printCountryList(countryMap);

        //print method
        String location = getOneLocation(countryMap);
        List<String[]> countryDataList = getDataFromLocation(location);
        User_Input_PrintScreen();
        try {

            int option = promptManager.choiceMenuWithValidOption(3) ;
//            scanner.nextLine();
            switch (option) {
                case 1 -> {
                    print("Enter start date (MM/dd/yyyy): ");
                    startDateString = scanner.nextLine();
                    print("Enter end date (MM//dd/yyyy): ");
                    endDateString = scanner.nextLine();
                    startDate = changeTypeOfDateString(startDateString);
                    enDate = changeTypeOfDateString(endDateString);
                    dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                    return dataPointModels;
                }
                case 2 -> {
                    System.out.println("Enter start date (MM/dd/yyyy): ");
                    startDateString = scanner.nextLine();
                    System.out.println("1. Number of week from start date");
                    System.out.println("2. Number of days from start date");
                    int optionDW = promptManager.choiceMenuWithValidOption(2);
//                    scanner.nextLine();
                    if (optionDW == 1){
                        System.out.println("Enter number of weeks from start date: ");
                        int weeks = scanner.nextInt();
                        scanner.nextLine();
                        int days  = calculateWeektoDate(weeks);
                        startDate = changeTypeOfDateString(startDateString);
                        enDate = calculateEndDate(startDateString,days);
                        dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);

                    }
                    else if(optionDW==2) {
                        System.out.println("Enter number of days from start date: ");
                        int days = scanner.nextInt();
                        scanner.nextLine();
                        startDate = changeTypeOfDateString(startDateString);
                        enDate = calculateEndDate(startDateString,days);
                        dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                    }
//                    dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                    return dataPointModels;
                }
                case 3 -> {
                    System.out.println("Enter the up to (end date - MM/dd/yyyy): ");
                    endDateString = scanner.nextLine();
                    System.out.println("1. Number of weeks to end date:  ");
                    System.out.println("2. Number of days to end date:  ");
                    int optionDW = promptManager.choiceMenuWithValidOption(2);
//                    scanner.nextLine();
                    if (optionDW == 1){
                        System.out.println("Enter number of weeks to end date: ");
                        int weeks = scanner.nextInt();
                        scanner.nextLine();
                        int days  = calculateWeektoDate(weeks);
                        startDate = calculateStartDate(endDateString,days);
                        enDate = changeTypeOfDateString(endDateString);
                        dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
                    }
                    else if (optionDW==2){
                        System.out.println("Enter number of days to end date: ");
                        int days = scanner.nextInt();
                        scanner.nextLine();
                        startDate = calculateStartDate(endDateString,days);
                        enDate = changeTypeOfDateString(endDateString);
                        dataPointModels = createListOfDPM(countryDataList,startDate,enDate,location);
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
    private static boolean checkDate(Date testDate, Date startDate, Date endDate) {

        if (testDate.after(startDate) && testDate.before(endDate)) {
            return true;
        } else if (testDate.equals(startDate)) {
            return true;
        } else return testDate.equals(endDate);
    }

    private static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {   // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

    public void updateView(){
        DataPointModel firstDPM = dataPointModels.get(0);
        DataPointModel lastDPM = dataPointModels.get(dataPointModels.size()-1);
        String startDate = firstDPM.getDate();
        String endDate = lastDPM.getDate();
        String location = firstDPM.getLocation();
        System.out.println(" List of Data Point to be processed");
        System.out.printf(" Location: %s \n Time Range: %s to %s \n",location,startDate,endDate);
        System.out.println("--------------------------------------------------------------------------");
        for (DataPointModel dpm: dataPointModels
             ) {
            dataPointView.printDataPoint(dpm);

        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("Number of data points: %d \n", dataPointModels.size());
    }

}
