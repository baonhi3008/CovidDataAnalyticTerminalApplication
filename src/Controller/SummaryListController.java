package Controller;

import Model.DataPointModel;
import Model.GroupModel;
import PromptManager.PromptManager;
import View.GroupView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Utility.ShowOptionMenu.Grouping_UI_Print;

public class SummaryListController {
    Scanner scan = new Scanner(System.in);
    ArrayList<GroupModel> Group_List;
    ArrayList<DataPointModel> dataPointModels;
    GroupView groupView;
    PromptManager promptManager = new PromptManager();

    public SummaryListController(ArrayList<DataPointModel> dataPointModels) {
        this.dataPointModels = dataPointModels;
        Group_List = new ArrayList<>();
        groupView = new GroupView();
    }

    public ArrayList<GroupModel> group_n_total_process() throws Exception {
        int option = promptManager.choiceMenuWithValidOption(4);

        //Grouping
        switch (option) {

            // NO GROUPING
            case 1 -> {

                for (DataPointModel i : dataPointModels) {
                    GroupModel g1 = new GroupModel();
                    g1.add_content(i);
                    Group_List.add(g1);
                }

            }
            // GROUP BY NUMBER OF GROUP
            case 2 -> {
                Grouping_UI_Print("1.2");
                String s = scan.nextLine();
                double no_group = Integer.parseInt(s);
                int list_size = dataPointModels.size();
                int counter = 0;
                while (no_group != 0) {
                    GroupModel g = new GroupModel();
                    int no_data_inside = (int) Math.round(list_size / no_group);
                    int counter2 = 0;
                    while (counter2 < no_data_inside) {
                        g.add_content(dataPointModels.get(counter));
                        counter2++;
                        counter++;
                    }
                    list_size -= no_data_inside;
                    no_group -= 1;
                    Group_List.add(g);
                }
            }
            // GROUP BY NUMBER OF DAYS PER GROUP
            case 3 -> {
                int length = dataPointModels.size();
                printSuggestion(length);
                int no_day_per_group = promptManager.canDivide(length);
                int counter = 0;
                int counter2 = 0;

                while (counter2 != dataPointModels.size()) {

                    GroupModel g1 = new GroupModel();

                    for (int i = 0; i < no_day_per_group; i++) {
                        g1.add_content(dataPointModels.get(i + counter));
                        counter2++;
                    }

                    counter += no_day_per_group;
                    Group_List.add(g1);
                }

            }
            default -> throw new IllegalStateException("Unexpected value: " + option);
        }

        //Summary
        Math_process();

        return Group_List;
    }

    public void Math_process() {

        Grouping_UI_Print("2");
        String s = scan.nextLine();
        int option1 = Integer.parseInt(s);
        Grouping_UI_Print("3");
        s = scan.nextLine();
        int option2 = Integer.parseInt(s);

        switch (option2) {
            //NEW TOTAL
            case 1 -> {
                New_total(Group_List, option1);
            }
            //UP TO
            case 2 -> {
                Up_to(dataPointModels, Group_List, option1);
            }
        }

    }

    public void New_total(ArrayList<GroupModel> Group_List, int option) {

        switch (option) {
            //CASES
            case 1 -> {
                for (GroupModel group : Group_List) {
                    int new_total = 0;
                    for (DataPointModel i : group.getContent()) {
                        new_total += i.getCases();
                    }
                    group.setTotal_value(new_total);
                }
            }
            //DEATH
            case 2 -> {

                for (GroupModel group : Group_List) {
                    int new_total = 0;
                    for (DataPointModel i : group.getContent()) {
                        new_total += i.getDeaths();
                    }
                    group.setTotal_value(new_total);
                }

            }
            //VACCINE
            case 3 -> {
                int vaccine1 = dataPointModels.get(0).getVaccinated();
                int vaccine2 = 0;
                int counter = 0;
                for (DataPointModel dataPoint : dataPointModels) {

                    //A safe check to make sure the vaccinated list from original data doesn't constantly update
                    if (dataPoint.getVaccinated() > vaccine2) {
                        vaccine2 = dataPoint.getVaccinated();
                    }

                    //Check if Current dataPoint date is equals to the date of Group_List's [counter]th Group last dataPoint
                    if (dataPoint.getDate().equals(Group_List.get(counter).getContent().get((Group_List.get(counter).getContent().size() - 1)).getDate())) {
                        Group_List.get(counter).setTotal_value(vaccine2 - vaccine1);
                        vaccine1 = vaccine2;
                        counter++;
                    }
                }

            }

        }

    }

    public static void Up_to(ArrayList<DataPointModel> dataPointModels, ArrayList<GroupModel> Group_List, int option) {

        switch (option) {
            //CASES
            case 1 -> {

                int new_total = 0;
                int counter = 0;
                for (DataPointModel dataPoint : dataPointModels) {
                    new_total += dataPoint.getCases();
                    //Check if Current dataPoint date is equals to the date of Group_List's [counter]th Group last dataPoint
                    if (dataPoint.getDate().equals(Group_List.get(counter).getContent().get((Group_List.get(counter).getContent().size() - 1)).getDate())) {
                        Group_List.get(counter).setTotal_value(new_total);
                        counter++;
                    }
                }
            }
            //DEATHS
            case 2 -> {

                int new_total = 0;
                int counter = 0;
                for (DataPointModel dataPoint : dataPointModels) {
                    new_total += dataPoint.getDeaths();
                    //Check if Current dataPoint date is equals to the date of Group_List's [counter]th Group last dataPoint
                    if (dataPoint.getDate().equals(Group_List.get(counter).getContent().get((Group_List.get(counter).getContent().size() - 1)).getDate())) {
                        Group_List.get(counter).setTotal_value(new_total);
                        counter++;
                    }
                }

            }
            //VACCINATION
            //VACCINATION
            case 3 -> {

                for (GroupModel group : Group_List) {
                    int total = 0;
                    for (DataPointModel dataPoint : group.getContent()) {
                        if (dataPoint.getVaccinated() > total) {
                            total = dataPoint.getVaccinated();
                        }
                    }
                    group.setTotal_value(total);
                }
            }
        }

    }

    public void updateView() {
        System.out.println("SUMMARY");
        System.out.println("-----------------------------------------------------------------------------");
        for (GroupModel group : Group_List) {
            group.displayGroup();
        }
    }

    public static void printSuggestion(int length) {
        List<Integer> suggestionList = new ArrayList<>();
        int n = 1;
        while (n < length) {
            if (length % n == 0) {
                suggestionList.add(n);
            }
            n++;
        }
        System.out.println("Recommend number of days for group: ");
        for (Integer i : suggestionList) {
            System.out.printf("[%d]", i);
        }
        System.out.println();
    }


//    public static boolean checkRemainder(int size, int n) {
//        int remainder = size % n;
//        if (remainder == 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    public int canDivide(int n) {
//        boolean flag = false;
//        int number = 0;
//        int finalNum = 0;
//        while (!flag) {
//            try {
//                Grouping_UI_Print("1.3");
//                number = scan.nextInt();
//                scan.nextLine();
//
//            } catch (Exception e) {
//                System.out.println("Invalid input try again");
//                scan.nextLine();
//                // cause to consume already caught Exception
//            }
//            if (checkRemainder(n, number)) {
//                flag = true;
//            } else {
//                System.out.printf("Cannot group %d days into group. Try again: \n", number);
//                flag = false;
//            }
//            finalNum = number;
//        }
//        return finalNum;
//    }



}

