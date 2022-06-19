package Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Utility.PrintScreen.*;

public class ShowOptionMenu {
    public ShowOptionMenu() {
    }
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


    public static void User_Input_PrintScreen(){

        println(">> Choose your option: ");
        println("1.  A pair of start date and end date (inclusive) (format: DD/MM/YYYY)");
        println("2.  A number of days or weeks from a particular date (format: DD/MM/YYYY)");
        println("3.  A number of days or weeks to a particular date (format: DD/MM/YYYY)");
        print(">> Type option heading number here: ");
    }
    public static void Grouping_UI_Print(String s) {

        switch (s) {
            case "1" -> {
//                println("Choose your Grouping option: ");
//                println("1. No grouping. ");
//                println("2. By number of groups.");
//                println("3. By number of days per group.");
//                print("Your choice:_ ");
                print(" [ Your choice  is no grouping.]");
            }
            case "1.2" -> print("How many number of groups: ");
            case "1.3" -> { print("How many number of days per group: ");}
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
    public static void groupingOption(){
        println("Choose your Grouping option: ");
        println("1. No grouping. ");
        println("2. By number of groups.");
        println("3. By number of days per group.");
        print("Your choice:_ ");
    }
    public static void printCountryList(Map<Integer,String> countryMap){
        int counter = 0 ;
        System.out.println("------------------- List of country -------------");
        for (Map.Entry<Integer,String> pair : countryMap.entrySet()) {
            String printCountry = " " + pair.getKey() + " ---  " +  pair.getValue() +" | ";
            counter++;
            if (counter ==0){
                System.out.format("%-2s%-40s", "|",printCountry);
            }
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
                System.out.println("");
                counter = 0;
            }

        }
        System.out.println();
        System.out.println(" -----------------------------------------------------");
    }
    public static void printSuggestion(int length){
        List<Integer> suggestionList = new ArrayList<>();
        int n = 1;
        while (n<length){
            if (length/n ==0){
                suggestionList.add(n);
            }
            n++;
        }
        for (Integer i : suggestionList) {
            System.out.println("Recommend number of days for group: ");
            System.out.printf("[ %d, ]",i);
        }
    }

}
