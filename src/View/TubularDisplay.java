package View;

import Model.GroupModel;

import java.util.ArrayList;

import static Utility.PrintScreen.printf;

public class TubularDisplay {
    public TubularDisplay() {

    }

    /**
     * Method to create tubular from an array list of group model created from user inputs.
     * @param dataTable a table from a summary processor with wanted values of time range, groups and metrics.
     */
    public static void displayTable(ArrayList<GroupModel> dataTable) {
        String data_print = "|  %-30s|  %-11d|%n";
        printf("+--------------------------------+-------------+%n",null,null);
        printf("|            Range               |    Value    |%n",null,null);
        printf("+--------------------------------+-------------+%n",null,null);
        for (GroupModel i : dataTable) {
            printf(data_print, i.getRange(), i.getTotalValue());
            printf("+--------------------------------+-------------+%n",null,null);
        }
    }

}