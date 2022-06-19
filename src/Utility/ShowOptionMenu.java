package Utility;

import java.util.Map;

import static Utility.PrintScreen.*;

public class ShowOptionMenu {
    public ShowOptionMenu() {
    }

    /**
     * Method print the menu screen to give welcome and instruction for user to know how to use this application
     */
    public static void welcome_Screen(){
        println("-------------------------------------------------------------------------------------------------------");
        println("                   WELCOME TO COVID-19 SIMPLE DATA ANALYTIC APPLICATION                     ");
        println("-------------------------------------------------------------------------------------------------------");
        println("                                 Instruction");
        println("                                 ************");
        String instruction =
                """
                        Step 1 >> A list of available country will be displayed on the screen once you choose option 1 to run program.
                        Step 2 >> Choose a country by enter the key number represents for that country
                        Step 3 >> A menu of available options shows how you can specify a time range, enter a time range.
                        Step 4 >> After specifying time range, you can choose how to group data.
                        Step 5 >> If you cannot group your data of your choice, you can re-enter the number of group again.
                        Step 6 >> You can choose a metric to complete a summary, and the application will show you a brief of the chosen data.
                        Step 7 >> Finally you can choose how to display the data visually either by tubular or chart.""";
        print(instruction);
        println("");
        println("-------------------------------------------------------------------------------------------------------");
    }

    /**
     * Method to print a list of methods that user can specify the time range
     */
    public static void optionSpecifyTimeRangeMenu(){

        println(">> Choose your option: ");
        println("1.  A pair of start date and end date (inclusive) (format: MM/DD/YYYY - eg. 01/27/2021)");
        println("2.  A number of days or weeks from a particular date (format: MM/DD/YYYY - eg. 01/27/2021)");
        println("3.  A number of days or weeks to a particular date (format: MM/DD/YYYY - eg. 01/27/2021)");
        print(">> Type option heading number here: ");
    }

    /**
     * Method to print a list UI of summary chosen process base on user option
     * @param s string of option to switch to specific case chosen by user in summary process
     */
    public static void groupingMethodMenu(String s) {

        switch (s) {
            case "1" -> {
                println("");
                print("[Your choice  is no grouping]");
            }
            case "1.2" -> print("How many number of groups: ");
            case "1.3" -> print("How many number of days per group: ");
            case "2" -> {
                println("Choose your metric option: ");
                println("1. Positive Cases.");
                println("2. Deaths.");
                println("3. Vaccinated.");
                print("Your choice:_ ");
            }
            case "3" -> {
                println("Choose your result option: ");
                println("1. New total. ");
                println("2. Up to. ");
                print("Your choice:_ ");
            }
        }
    }

    /**
     * Method to print list of options that user can choose to group their data
     */
    public static void groupingOptionMenu(){
        println("Choose your Grouping option: ");
        println("1. No grouping. ");
        println("2. By number of groups.");
        println("3. By number of days per group.");
        print("Your choice:_ ");
    }

    /**
     * Method to print country or continent map for user to see all available countries or continents with
     * representing number as key.
     * @param countryMap a map of kay and value with number represents a a country for user choose
     */
    public static void printCountryList(Map<Integer,String> countryMap){
        int counter = 0 ;
        System.out.println("------------------- List of country -------------");
        for (Map.Entry<Integer,String> pair : countryMap.entrySet()) {
            String printCountry = " " + pair.getKey() + " ---  " +  pair.getValue() +" | ";
            counter++;
            if (counter ==1){
                System.out.format("%-2s%-50s", "|",printCountry);

            }
            else if (counter ==2){
                System.out.format("%-1s%-50s", "|",printCountry);

            }
            else if (counter==3){
                System.out.format("%-1s%-50s", "|",printCountry);

            }
            else {
                System.out.format("%-1s%-50s", "|",printCountry);
                System.out.println();
                counter = 0;
            }
        }
        System.out.println();
        System.out.println(" -----------------------------------------------------");
    }

    /**
     * Method to ask user which kind of chart to display
     */
    public static void askTypeOfDisplay(){
        System.out.println("What kind of visualization you want to display ?");
        System.out.println("1. Tubular Table.");
        System.out.println("2. Chart ");
        System.out.println("Your choice [1-2] _ ");
    }

    /**
     * Method to ask user which kind of location they want to choose.
     * @param option base on option in other function to display corresponding UI step.
     */

    public static void askToChooseLocation(int option){
        switch (option){
            case 1 ->{ println("Please select a country: ");
            println("Your choice [0-220] _");}
            case 2 ->{ println("Please select a continent: ");
                println("Your choice [0-8] _");}

        }
    }

}
