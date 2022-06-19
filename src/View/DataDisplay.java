package View;

import Model.GroupModel;

import java.util.ArrayList;

import static Utility.PrintScreen.printf;

public class DataDisplay {

    public DataDisplay() {

    }

    public static void Display_table(ArrayList<GroupModel> data_table) {
        String data_print = "|  %-28s|  %-8d|%n";
        printf("+------------------------------+----------+%n",null,null);
        printf("|  Range                       |  Value   |%n",null,null);
        printf("+------------------------------+----------+%n",null,null);
        for (GroupModel i : data_table) {
            printf(data_print, i.getRange(), i.getTotal_value());
            printf("+------------------------------+----------+%n",null,null);
        }
//        printf("+------------------------------+----------+%n",null,null);
    }

    public void Display_Chart() {

    }
}