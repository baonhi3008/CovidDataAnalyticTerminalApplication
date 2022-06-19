package Controller;

import Model.DataPointModel;
import Model.GroupModel;
import PromptManager.PromptManager;
import Utility.ShowOptionMenu;
import View.SummaryListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static Utility.ShowOptionMenu.groupingMethodMenu;

public class SummaryListController {
    private Scanner scan = new Scanner(System.in);
    private ArrayList<GroupModel> groupList;
    private ArrayList<DataPointModel> dataPointModels;
    private PromptManager promptManager = new PromptManager();
    private DataListController dataListController = new DataListController();

    public SummaryListController(ArrayList<DataPointModel> dataPointModels) {
        this.dataPointModels = dataPointModels;
        groupList = new ArrayList<>();
    }

    /**
     * Method to interact with user to create summary for the chosen data points -> each of data is grouped in a user chosen method way.
     * in form of group model. Group models combines to make this list to further create data visualization.
     * @return a list of group model contain necessary data in form of group model
     * @throws Exception if there is any error
     */
    public ArrayList<GroupModel> groupNTotalProcess() throws Exception {
        ShowOptionMenu.groupingOptionMenu();
        int option = promptManager.choiceMenuWithValidOption(3,1);
        //Grouping
        switch (option) {

            // NO GROUPING
            case 1 -> {
                for (DataPointModel i : dataPointModels) {
                    GroupModel g1 = new GroupModel();
                    g1.add_content(i);
                    groupList.add(g1);
                }
            }
            // GROUP BY NUMBER OF GROUP
            case 2 -> {
                groupingMethodMenu("1.2");
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
                    groupList.add(g);
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
                    groupList.add(g1);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + option);
        }

        //Summary
        mathProcess();

        return groupList;
    }

    /**
     * Method to process calculations for the data base on the want calculating way (new total or up to)
     * and metric (positive cases, deaths, vaccinated).
     */
    private void mathProcess() throws Exception {

        groupingMethodMenu("2");
        String s = scan.nextLine();
        int option1 = Integer.parseInt(s);
        groupingMethodMenu("3");
        s = scan.nextLine();
        int option2 = Integer.parseInt(s);

        switch (option2) {
            //NEW TOTAL
            case 1 -> newTotal(groupList, option1);
            //UP TO
            case 2 -> upTo(dataPointModels, groupList, option1);
        }

    }

    /**
     * Method to take wanted metric from user and process new total calculation on that metric, from a wished criteria
     * @param Group_List an array list of group model to process calculation
     * @param option inputted from user. This is a chosen metric from user to process the calculation
     */

    private void newTotal(ArrayList<GroupModel> Group_List, int option) throws Exception {

        switch (option) {
            //CASES
            case 1 -> {
                for (GroupModel group : Group_List) {
                    int new_total = 0;
                    for (DataPointModel i : group.getContent()) {
                        new_total += i.getCases();
                    }
                    group.setTotalValue(new_total);
                }
            }
            //DEATH
            case 2 -> {
                for (GroupModel group : Group_List) {
                    int new_total = 0;
                    for (DataPointModel i : group.getContent()) {
                        new_total += i.getDeaths();
                    }
                    group.setTotalValue(new_total);
                }
            }
            //VACCINE
            case 3 -> {
                int vaccine1 = vaccineBeforeRange();
                int vaccine2 = 0;
                for (GroupModel group : groupList) {
                    int new_total = 0;
                    for (DataPointModel dataPointModel : group.getContent()) {
                        vaccine2 = dataPointModel.getVaccinated();
                        if (vaccine2 == 0) {
                            vaccine2 = vaccine1;
                        }
                        new_total += (vaccine2 - vaccine1);
                        vaccine1 = vaccine2;
                    }
                    group.setTotalValue(new_total);
                }

            }
        }

    }

    /**
     * Method to take wanted metric from user and process up to calculation on that metric, from a wished criteria
     * @param Group_List an array list of group model to process calculation
     * @param option inputted from user. This is a chosen metric from user to process the calculation
     * @param dataPointModels is the list of data point model to process calculation
     */
    private void upTo(ArrayList<DataPointModel> dataPointModels, ArrayList<GroupModel> Group_List, int option) throws Exception {

        switch (option) {
            //CASES
            case 1 -> {

                int newTotal = valueFromTheBeginning(option);
                System.out.println("Value from the beginning: " + newTotal);
                int counter = 0;
                for (DataPointModel dataPoint : dataPointModels) {
                    newTotal += dataPoint.getCases();

                    //Check if Current dataPoint date is equals to the date of Group_List's [counter]th Group last dataPoint
                    if (dataPoint.getDate().equals(Group_List.get(counter).getContent().get((Group_List.get(counter).getContent().size() - 1)).getDate())) {
                        Group_List.get(counter).setTotalValue(newTotal);
                        counter++;

                    }
                }
            }
            //DEATHS
            case 2 -> {
                int new_total = valueFromTheBeginning(option);
                int counter = 0;
                for (DataPointModel dataPoint : dataPointModels) {
                    new_total += dataPoint.getDeaths();
                    //Check if Current dataPoint date is equals to the date of Group_List's [counter]th Group last dataPoint
                    if (dataPoint.getDate().equals(Group_List.get(counter).getContent().get((Group_List.get(counter).getContent().size() - 1)).getDate())) {
                        Group_List.get(counter).setTotalValue(new_total);
                        counter++;
                    }
                }

            }
            //VACCINATION
            case 3 -> {
                for (GroupModel group : Group_List) {
                    int total = 0;
                    for (DataPointModel dataPoint : group.getContent()) {
                        if (dataPoint.getVaccinated() > total) {
                            total = dataPoint.getVaccinated();
                        }
                    }
                    group.setTotalValue(total);
                }
            }
        }

    }

    /**
     * Method to update view from a GroupModelView. This is created to follow MVC design pattern.
     */

    public void updateViewList(){
        updateView(groupList);
    }

    public void updateView(ArrayList<GroupModel> groupList){
        System.out.println("============================SUMMARY====================================");
        System.out.format("|  %-11s|  %-30s|  %-11s |%n","Group","Range","Value");
        System.out.println("+-------------------------------------------------------------+");
        int index = 1;
        for (GroupModel groupModel: groupList) {
            String groupNum = "Group " + index;
            System.out.format( "|  %-11s|  %-30s|  %-11d |%n",groupNum,groupModel.getRange(),groupModel.getTotalValue());
            System.out.println("+-------------------------------------------------------------+");
            index++;
        }
        System.out.println("=======================================================================");
    }

    /**
     * Method to print suggestion for numbers of days that can be grouped  into 1 group
     * by number of data point model array list in case user have hesitation.
     * @param length a size of data point model array list
     */

    private static void printSuggestion(int length) {
        List<Integer> suggestionList = new ArrayList<>();
        int n = 1;
        while (n <= length) {
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

    /**
     * Method to get value for up to method. The method will get a list of location records till the date before the first date of the time range.
     * @param option to switch between metrics
     * @return an integer that contains number of total value from the beginning.
     * @throws Exception if there is any error
     */
    private int valueFromTheBeginning(int option) throws Exception {
        String location = dataPointModels.get(0).getLocation();
        Date startDateTimeRange = dataPointModels.get(0).getDateType();
        List<String[]> countryData = dataListController.getDataFromLocation(location);
        int valueFromBeginning = 0;
        int finalValue = 0;
        switch (option) {
            case 1 -> {
                //Cases
                for (String[] line : countryData) {
                    Date compareDate = dataListController.changeTypeOfDateString(line[3]);
                    if (compareDate.before(startDateTimeRange)) {
                        int newCase = Integer.parseInt(line[4]);
                        valueFromBeginning+=newCase;
                    }
                }

                finalValue = valueFromBeginning;

            }
            case 2 -> {
                //Deaths
                for (String[] line : countryData) {
                    Date compareDate = dataListController.changeTypeOfDateString(line[3]);
                    if (compareDate.before(startDateTimeRange)) {
                        int newCase = Integer.parseInt(line[5]);
                        valueFromBeginning+=newCase;
                    }
                }
                finalValue = valueFromBeginning;
            }
    }
        return finalValue;
    }

    private int vaccineBeforeRange() throws Exception {
        String location = dataPointModels.get(0).getLocation();
        Date startDateTimeRange = dataPointModels.get(0).getDateType();
        List<String[]> countryData = dataListController.getDataFromLocation(location);
        int vaccineBeforeRange = 0;
        for (String[] line : countryData) {
            Date compareDate = dataListController.changeTypeOfDateString(line[3]);
            if (compareDate.before(startDateTimeRange)) {
                if (Integer.parseInt(line[6]) > vaccineBeforeRange) {
                    vaccineBeforeRange = Integer.parseInt(line[6]);
                }
            }
        }
        System.out.println(vaccineBeforeRange);
        return vaccineBeforeRange;
    }


}

