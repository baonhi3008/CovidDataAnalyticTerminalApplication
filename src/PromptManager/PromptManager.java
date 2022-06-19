package PromptManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Utility.ShowOptionMenu.groupingMethodMenu;

public class PromptManager {
    /**
     *
     * @param validInclusive inclusive of valid option to choose
     * @return int of option to be passed in other methods
     * @throws Exception if there is any error
     */
    public int choiceMenuWithValidOption(int validInclusive, int validInclusive2) throws Exception{
        Scanner read = new Scanner(System.in);
        int variable;
        boolean auxiliaryBoolean = false;
        int choiceMenu = 0;
        while (!auxiliaryBoolean) {
            try {
                variable = read.nextInt();
                read.nextLine();
            } catch (Exception e) {
                System.out.printf("Incorrect data type, try again. Please enter digit between 1 - %d. Error Message: %s \n",validInclusive,e);
                read.nextLine();
                continue;
            }
            if (variable<0 || variable>validInclusive || variable <validInclusive2){
                System.out.println("Incorrect choice, try again. Enter option between 1 and 3");
            } else {
                auxiliaryBoolean = true;
            }
            choiceMenu = variable;
        }
        return choiceMenu;
    }

    /**
     * Method to check if the remainder is 0 or not.
     * @param size a size of array list of data point model
     * @param n a number of days in each group
     * @return true or false (if the remainder is 0 flag will be true, else flag will be false
     */
    public static boolean checkRemainder(int size, int n) {
        int remainder = size % n;
        return remainder == 0;
    }

    /**
     * Method take input from user - a number of days to be grouped in each group and decide whether
     * that number of days can be divided equally in each group, within the data range
     * @param size a size of array list of data point models
     * @return if a inputted number can be divided with remainder of 0 by the list size then the numbers
     * of day in each group can be divided equally into specific number of group
     */
    public int canDivide(int size) {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        int number = 0;
        int finalNum = 0;
        while (!flag) {
            try {
                groupingMethodMenu("1.3");
                number = scan.nextInt();
                scan.nextLine();

            } catch (Exception e) {
                System.out.println("Invalid input try again");
                scan.nextLine();
            }
            if (checkRemainder(size, number)) {
                flag = true;
            } else {
                System.out.printf("Cannot group %d days into group. Try again: \n", number);
                flag = false;
            }
            finalNum = number;
        }
        return finalNum;
    }
    public boolean isValidFormat(String date){
        String month = date.substring(0,2);
        int monthInt = Integer.parseInt(month);
        return monthInt <= 12;
    }
    private  Date changeTypeOfDateString(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.parse(dateString);
    }
    public  boolean inAValidRange(String startRange, String endRange,String testDate) throws ParseException {
        Date startRangeD = changeTypeOfDateString(startRange);
        Date endRangeD = changeTypeOfDateString(endRange);
        Date testDateD = changeTypeOfDateString(testDate);
        if (testDateD.after(startRangeD) && testDateD.before(endRangeD)){
            return true;
        }
        else return testDateD.equals(startRangeD);

    }
    public Date isInRangeDateInput(String startDate,String endDate) throws ParseException {
        boolean flag = false;
        Date finalDate = null;
        while(!flag){
            String testDate = isCorrectFormat();
            if(inAValidRange(startDate,endDate,testDate)){
                flag = true;
                finalDate = changeTypeOfDateString(testDate);
            }
            else {
                System.out.println("Not in a valid range. Try again. ");
                System.out.print("Enter a date: ");
                flag = false;
            }
        }
        return finalDate;
    }
    public String isCorrectFormat() {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        String date = "";
        String finalDate = "";
        while (!flag) {
            try {
                date= scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input try again");
                scan.nextLine();
            }
            if (isValidFormat(date)) {

                flag = true;
            } else {
                System.out.println("Not a valid date. Try again: \n");
                flag = false;
            }
        }
        finalDate = date;
        return finalDate;
    }


}
