package PromptManager;

import java.util.Scanner;

import static Utility.ShowOptionMenu.Grouping_UI_Print;

public class PromptManager {
    public int choiceMenuWithValidOption(int validInclusive) throws Exception{
        Scanner read = new Scanner(System.in);
//        int validInclusive = 4;
        int variable = -1;
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
            if (variable<0 || variable>validInclusive ){
                System.out.println("Incorrect choice, try again. Enter option between 1 and 3");
            } else {
                auxiliaryBoolean = true;
            }
            choiceMenu = variable;

        }
//        System.out.println(choiceMenu);
        return choiceMenu;
    }
//    public int choiceMenuWithTwoOptions() throws Exception{
//        Scanner read = new Scanner(System.in);
//        int validInclusive = 3;
//        int variable;
//        boolean auxiliaryBoolean = false;
//        int choiceMenu = 0;
//        while (!auxiliaryBoolean) {
//            try {
//                variable = read.nextInt();
//                read.nextLine();
//            } catch (Exception e) {
//                System.out.println("Incorrect data type, try again. Please enter digit between 1 -3 "+e);
//                read.nextLine();
//                continue;
//            }
//            if (variable<0 || variable>validInclusive ){
//                System.out.println("Incorrect choice, try again. Enter option between 1 and 3");
//            } else {
//                auxiliaryBoolean = true;
//            }
//            choiceMenu = variable;
//
//        }
////        System.out.println(choiceMenu);
//        return choiceMenu;
//    }
    public static boolean checkRemainder(int size, int n) {
        int remainder = size % n;
        if (remainder == 0) {
            return true;
        } else {
            return false;
        }
    }
    public int canDivide(int n) {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        int number = 0;
        int finalNum = 0;
        while (!flag) {
            try {
                Grouping_UI_Print("1.3");
                number = scan.nextInt();
                scan.nextLine();

            } catch (Exception e) {
                System.out.println("Invalid input try again");
                scan.nextLine();
                // cause to consume already caught Exception
            }
            if (checkRemainder(n, number)) {
                flag = true;
            } else {
                System.out.printf("Cannot group %d days into group. Try again: \n", number);
                flag = false;
            }
            finalNum = number;
        }
        return finalNum;
    }

}
